// Cache only this app's shell. Other apps can share the same hosting origin.
const CACHE_PREFIX = 'adp-chathouse-';
const CACHE_NAME = `${CACHE_PREFIX}v3`;
const SHELL_ASSETS = ['./index.html', './manifest.json', './polish.css'];

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(cache => cache.addAll(SHELL_ASSETS))
      .then(() => self.skipWaiting())
  );
});

self.addEventListener('activate', event => {
  event.waitUntil(
    caches.keys().then(keys => Promise.all(
      keys.filter(key => key.startsWith(CACHE_PREFIX) && key !== CACHE_NAME)
        .map(key => caches.delete(key))
    )).then(() => self.clients.claim())
  );
});

self.addEventListener('fetch', event => {
  // POST requests and third-party requests must keep their normal network path.
  if (event.request.method !== 'GET') return;
  const url = new URL(event.request.url);
  if (url.origin !== self.location.origin) return;
  event.respondWith(
    caches.open(CACHE_NAME)
      .then(cache => cache.match(event.request))
      .then(cached => cached || fetch(event.request))
  );
});
