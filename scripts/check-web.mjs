import { readFileSync } from 'node:fs';
import { spawnSync } from 'node:child_process';

// Parse every inline script as a module without downloading or executing Firebase.
for (const file of ['app/src/main/assets/index.html', 'docs/index.html']) {
  const html = readFileSync(file, 'utf8');
  const scripts = [...html.matchAll(/<script\b[^>]*>([\s\S]*?)<\/script>/gi)];
  for (const [index, match] of scripts.entries()) {
    const result = spawnSync(process.execPath, ['--input-type=module', '--check'], {
      input: match[1], encoding: 'utf8'
    });
    if (result.status !== 0) {
      console.error(`${file}: inline script ${index + 1}\n${result.stderr}`);
      process.exit(1);
    }
  }
  console.log(`${file}: ${scripts.length} inline scripts checked`);
}
for (const file of ['app/src/main/assets/manifest.json', 'functions/package.json']) {
  JSON.parse(readFileSync(file, 'utf8'));
  console.log(`${file}: valid JSON`);
}
