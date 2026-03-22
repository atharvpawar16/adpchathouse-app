# Security Policy

## Supported Versions

| Version | Supported |
|---------|-----------|
| 1.0.x   | ✅ Yes    |

## Reporting a Vulnerability

If you discover a security vulnerability in ADP Chathouse, please **do not** open a public GitHub issue.

Instead, report it privately:

- **Email:** adp060606@gmail.com
- **Subject:** `[SECURITY] ADP Chathouse - <brief description>`

### What to include
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Any suggested fix (optional)

### Response Timeline
- Acknowledgement within **48 hours**
- Status update within **7 days**
- Fix released as soon as possible depending on severity

## Security Notes

- Firebase API keys in the frontend are intentionally public — security is enforced via **Firebase Security Rules**
- The signing keystore (`*.jks`) is excluded from this repository
- `google-services.json` is excluded — users must provide their own Firebase project credentials
- Passwords are hashed client-side with SHA-256 before storage

## Scope

The following are **in scope** for security reports:
- Authentication bypass
- Unauthorized access to chat rooms
- Data injection or XSS in the chat UI
- Firebase Security Rules misconfigurations

The following are **out of scope**:
- Issues in third-party libraries (Firebase, etc.)
- Denial of service attacks
- Social engineering

---

© 2026 Atharv Pawar. All rights reserved.
