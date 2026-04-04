# Changelog

All notable changes to ADP Chathouse will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Planned
- Push notifications via Firebase Cloud Messaging (FCM)
- In-app message search
- Voice message recording and playback
- User profiles with custom avatars
- End-to-end encryption for private rooms

---

## [1.0.0] - 2026-03-22

Initial public release of ADP Chathouse.

### Added

#### Authentication & Rooms
- Persistent login via Firebase Authentication (email + password)
- Anonymous mode — join any room with a username and room code, no account required
- Optional password protection for anonymous rooms
- Room auto-expiry after 3 hours of inactivity
- Message history — last 50 messages loaded on room join
- Room sharing via shareable link

#### Messaging
- Real-time messaging powered by Firebase Realtime Database
- Image sharing support (up to 5 MB — JPEG, PNG, GIF, WebP)
- Built-in emoji picker
- GIF search and send
- Message reply / quote support
- Emoji reactions on messages
- Typing indicators visible to all users in the room
- Rate limiting — maximum 3 messages per 3 seconds to prevent spam
- Character counter with warning states

#### UI & Theming
- Dark terminal theme (green-on-black, monospace font, scan lines)
- Light mode toggle for everyday use
- Image viewer with pinch-to-zoom and pan support
- Online user list panel
- Unread message badge when scrolled away from latest messages
- Notification sound toggle

#### Moderation
- Admin controls — room creator can delete any message in the room

#### Connectivity
- Network pulse indicator with automatic reconnection on disconnect

#### Android (Native)
- Native WebView wrapper built with Java and AndroidX
- File chooser integration for image uploads
- Back navigation handled natively by the Android layer
- Splash screen with terminal-style boot animation
- Adaptive launcher icons for all screen densities
- Supports Android 7.0+ (API 24) through Android 14 (API 34)
- AndroidBridge interface for JavaScript ↔ Java communication
- Service worker for offline asset caching
- PWA manifest included

---

## [0.9.0-beta] - 2026-02-10

Internal beta release for testing core functionality.

### Added
- Basic Firebase Authentication (email + password)
- Real-time messaging with Firebase Realtime Database
- Anonymous room join with username and room code
- Dark terminal theme (initial version)
- Native Android WebView wrapper

### Known Issues
- No rate limiting on messages
- Image sharing not yet supported
- Light mode not available

---

*For older history, see git log.*
