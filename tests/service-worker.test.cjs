const { test } = require('node:test');
const assert = require('node:assert/strict');
const { readFileSync } = require('node:fs');
const vm = require('node:vm');

function worker({ keys = [], cached, failInstall = false } = {}) {
  const handlers = {};
  const calls = { deleted: [], network: [], opened: [], assets: [] };
  const self = {
    location: { origin: 'https://example.com' },
    addEventListener: (name, handler) => { handlers[name] = handler; },
    skipWaiting: async () => { calls.skipped = true; },
    clients: { claim: async () => { calls.claimed = true; } }
  };
  const caches = {
    keys: async () => keys,
    delete: async key => { calls.deleted.push(key); },
    open: async key => {
      calls.opened.push(key);
      return {
        addAll: async assets => {
          calls.assets = [...assets];
          if (failInstall) throw new Error('offline');
        },
        match: async () => cached
      };
    }
  };
  vm.runInNewContext(readFileSync('app/src/main/assets/service-worker.js', 'utf8'), {
    self, caches, URL, fetch: async request => { calls.network.push(request); return 'network'; }
  });
  const lifecycle = name => {
    let pending;
    handlers[name]({ waitUntil: promise => { pending = promise; } });
    assert.ok(pending, `${name} must keep the worker alive until complete`);
    return pending;
  };
  const request = (url, method = 'GET') => {
    let response;
    handlers.fetch({ request: { url, method }, respondWith: promise => { response = promise; } });
    return response;
  };
  return { calls, lifecycle, request };
}

test('installation caches the complete shell before activating', async () => {
  const w = worker();
  await w.lifecycle('install');
  assert.deepEqual(w.calls.assets, ['./index.html', './manifest.json', './polish.css']);
  assert.equal(w.calls.skipped, true);
});

test('failed install does not replace a working worker', async () => {
  const w = worker({ failInstall: true });
  await assert.rejects(w.lifecycle('install'), /offline/);
  assert.equal(w.calls.skipped, undefined);
});

test('activation deletes old app caches but preserves other apps', async () => {
  const w = worker({ keys: ['adp-chathouse-v1', 'adp-chathouse-v2', 'adp-chathouse-v3', 'other-app-v1'] });
  await w.lifecycle('activate');
  assert.deepEqual(w.calls.deleted, ['adp-chathouse-v1', 'adp-chathouse-v2']);
  assert.equal(w.calls.claimed, true);
});

test('same-origin GET uses only the current app cache', async () => {
  const w = worker({ cached: 'shell' });
  assert.equal(await w.request('https://example.com/index.html'), 'shell');
  assert.deepEqual(w.calls.opened, ['adp-chathouse-v3']);
  assert.equal(w.calls.network.length, 0);
});

test('cache misses fall back to the original request', async () => {
  const w = worker();
  assert.equal(await w.request('https://example.com/other'), 'network');
  assert.equal(w.calls.network[0].url, 'https://example.com/other');
});

test('cross-origin and non-GET traffic is not intercepted', () => {
  const w = worker();
  assert.equal(w.request('https://firebase.example.com/data'), undefined);
  assert.equal(w.request('https://example.com/data', 'POST'), undefined);
  assert.equal(w.request('https://example.com/data', 'DELETE'), undefined);
  assert.equal(w.calls.opened.length, 0);
});
