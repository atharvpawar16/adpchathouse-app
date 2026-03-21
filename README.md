# ADP Chathouse

![Platform](https://img.shields.io/badge/Platform-Web%20%7C%20Android-00ff41?style=flat-square&labelColor=000000)
![Status](https://img.shields.io/badge/Status-Active-00ff41?style=flat-square&labelColor=000000)
![Language](https://img.shields.io/badge/Android-Java-orange?style=flat-square&labelColor=000000)
![License](https://img.shields.io/badge/License-Proprietary-red?style=flat-square&labelColor=000000)
![Version](https://img.shields.io/badge/Version-1.0.0-blue?style=flat-square&labelColor=000000)
![PRs](https://img.shields.io/badge/PRs-Not%20Accepting-lightgrey?style=flat-square&labelColor=000000)
![Made With](https://img.shields.io/badge/Made%20With-Java%20%7C%20JS-00ff41?style=flat-square&labelColor=000000)
![Realtime](https://img.shields.io/badge/Realtime-Yes-00ff41?style=flat-square&labelColor=000000)
![Auth](https://img.shields.io/badge/Auth-Email%20%7C%20Anonymous-00ff41?style=flat-square&labelColor=000000)
![Theme](https://img.shields.io/badge/Theme-Dark%20%7C%20Light-00ff41?style=flat-square&labelColor=000000)

> A real-time chat platform built for instant human connection — no phone number, no social media, no noise.

---

## Overview

ADP Chathouse is a cross-platform chat application available on both web and Android. It focuses on simplicity and speed — users can jump into a conversation within seconds using either a verified account or a completely anonymous session.

The app is built with a terminal-inspired UI, real-time sync, and a lightweight Android wrapper that keeps the experience smooth and native-feeling on mobile.

---

## Features

| Feature | Description |
|---------|-------------|
| Secure Login | Email-based authentication with verification |
| Anonymous Mode | Join any room instantly with a username and room code |
| Real-time Messaging | Messages sync instantly across all connected users |
| Image Sharing | Send images directly inside the chat |
| Emoji & GIF Support | Built-in emoji picker and GIF search |
| Private Rooms | Lock any room with a password |
| Admin Controls | Room admin system with moderation tools |
| Typing Indicators | Live typing status for all users |
| Dark / Light Theme | Terminal-style dark mode with a clean light mode |
| Android App | Native Android wrapper with background keep-alive |

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | HTML5, CSS3, Vanilla JavaScript |
| Backend | Cloud Realtime Database |
| Authentication | Cloud Auth Service |
| Android | Java — Native WebView |
| Hosting | Cloud Hosting |

---

## Project Structure

```
adpchathouse-app/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/adpchathouse/app/
│           │   ├── MainActivity.java        # WebView host activity
│           │   ├── SplashActivity.java      # Animated splash screen
│           │   └── KeepAliveService.java    # Background service
│           ├── assets/
│           │   └── index.html               # Web app entry point
│           ├── res/                         # Android resources
│           └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── README.md
```

---

## Platforms

- Web — runs in any modern browser
- Android — native APK with full mobile support

---

## Current Status

Active development — core chat system complete, additional features in progress.

---

## Developer

**Atharv Pawar**
Android & Web Developer | Full Stack Analyst

---

## License

This project is personal and proprietary.
All rights reserved © 2026 Atharv Pawar. Unauthorized use, copying, or distribution is not permitted.
