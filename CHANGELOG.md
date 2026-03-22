# Changelog

All notable changes to ADP Chathouse will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.0] - 2026-03-22

### Added
- Permanent login mode via Firebase Authentication (email + password)
- Anonymous mode — join any room with a username and room code
- Optional room passwords for private anonymous rooms
- Real-time messaging via Firebase Realtime Database
- Image sharing (up to 5MB — JPEG, PNG, GIF, WebP)
- Built-in emoji picker
- GIF search and send
- Message reply / quote support
- Emoji reactions on messages
- Typing indicators for all users in the room
- Online user list panel
- Admin controls — room creator can delete any message
- Dark terminal theme (green-on-black, monospace, scan lines)
- Light mode toggle
- Network pulse indicator with auto-reconnect
- Unread message badge
- Notification sound toggle
- Room sharing via link
- Image viewer with zoom and pan
- Character counter with warning states
- Rate limiting (3 messages per 3 seconds)
- Message history (last 50 messages on join)
- Room auto-expiry after 3 hours of inactivity
- Splash screen with terminal boot animation
- AndroidBridge JS ↔ Java interface
- Service worker for offline caching
- PWA manifest

### Android
- Native WebView wrapper (Java + AndroidX)
- File chooser for image uploads
- Back navigation handled natively
- Supports Android 7.0+ (API 24) up to Android 14 (API 34)
- Adaptive icons for all screen densities

---

## [Unreleased]

### Planned
- Push notifications via FCM
- Message search
- Voice messages
- User profiles and avatars
- End-to-end encryption
