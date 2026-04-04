<div align="center">

<img src="app icon.png" alt="ADP Chathouse" width="120" />

# ADP Chathouse

**A real-time group chat application with a retro terminal aesthetic.**
No phone number. No social media. Just instant connection.

[![Download APK](https://img.shields.io/badge/Download-APK%20v1.0.0-00ff41?style=for-the-badge&logo=android&logoColor=white&labelColor=000000)](https://github.com/atharvpawar16/adpchathouse-app/releases/tag/v1.0.0)

![Build](https://github.com/atharvpawar16/adpchathouse-app/actions/workflows/android-build.yml/badge.svg)
![Platform](https://img.shields.io/badge/Platform-Android%207.0+-00ff41?style=flat-square&labelColor=000000)
![Version](https://img.shields.io/badge/Version-1.0.0-blue?style=flat-square&labelColor=000000)
![Language](https://img.shields.io/badge/Java-Android-orange?style=flat-square&labelColor=000000)
![Firebase](https://img.shields.io/badge/Firebase-Realtime%20DB-FFCA28?style=flat-square&logo=firebase&labelColor=000000)
![License](https://img.shields.io/badge/License-Proprietary-red?style=flat-square&labelColor=000000)

</div>

---

## Screenshots

<div align="center">
<img src="screenshots/1_login.jpeg" width="22%" />
<img src="screenshots/2_login_light.jpeg" width="22%" />
<img src="screenshots/3_chat.jpeg" width="22%" />
<img src="screenshots/6_chat_4.jpeg" width="22%" />
</div>

<div align="center">
<sub>Login · Light Mode · Chat · Live Room</sub>
</div>

---

## What is ADP Chathouse?

ADP Chathouse is a real-time group chat application for Android. It wraps a Firebase-powered web app inside a native Android WebView, delivering a smooth, app-like experience with full offline resilience and a push-ready architecture.

The interface is inspired by retro terminal aesthetics — monospace fonts, green-on-black, scan lines — complemented by a clean light mode for everyday use.

---

## Features

| | Feature | Details |
|---|---------|---------|
| 🔐 | Persistent Login | Email and password authentication via Firebase Auth. The same email always maps to the same room. |
| 🎭 | Anonymous Mode | Join with a username and room code — no account required. |
| 🔒 | Private Rooms | Optional password protection for anonymous rooms. |
| ⚡ | Real-time Sync | Messages sync instantly via Firebase Realtime Database. |
| 🖼️ | Image Sharing | Send images up to 5 MB directly in chat. |
| 😊 | Emoji & GIF Support | Built-in emoji picker with integrated GIF search. |
| 💬 | Reply & React | Quote messages and respond with emoji reactions. |
| ✍️ | Typing Indicators | Live typing status visible to all users in the room. |
| 👑 | Admin Controls | Room creators can delete messages and moderate participants. |
| 🌙 | Dark / Light Theme | Terminal dark mode with a clean light mode toggle. |
| 📶 | Network Pulse | Live connection indicator with automatic reconnection. |
| 🔔 | Unread Badge | Displays unread message count when scrolled away from the latest messages. |
| 🔗 | Room Sharing | Share room links directly from within the app. |

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Android | Java · Native WebView · AndroidX |
| Frontend | HTML5 · CSS3 · Vanilla JavaScript |
| Authentication | Firebase Authentication |
| Database | Firebase Realtime Database |
| Hosting | Firebase Hosting |
| Minimum SDK | Android 7.0 (API 24) |
| Target SDK | Android 14 (API 34) |

---

## Project Structure

```
adpchathouse-app/
├── app/
│   └── src/main/
│       ├── java/com/adpchathouse/app/
│       │   ├── MainActivity.java       # WebView host + file chooser
│       │   ├── SplashActivity.java     # Animated splash screen
│       │   └── AndroidBridge.java      # JS ↔ Android interface
│       ├── assets/
│       │   ├── index.html              # Full web app (UI + Firebase logic)
│       │   ├── manifest.json           # PWA manifest
│       │   └── service-worker.js       # Offline caching
│       ├── res/                        # Icons, drawables, layouts, themes
│       └── AndroidManifest.xml
├── functions/
│   └── index.js                        # Cloud Functions placeholder
├── build.gradle
├── settings.gradle
└── README.md
```

---

## Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+
- A Firebase project with Realtime Database and Authentication enabled

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/atharvpawar16/adpchathouse-app.git
   ```

2. Open the project in Android Studio.

3. Add your `google-services.json` to the `app/` directory. This file is not included — you must create your own Firebase project.

4. Update the Firebase configuration inside `app/src/main/assets/index.html` with your own project credentials.

5. Build and run the app on a physical device or emulator.

---

## Download

Get the latest release APK directly from the [Releases](https://github.com/atharvpawar16/adpchathouse-app/releases) page.

> Requires Android 7.0 or higher. Enable "Install from unknown sources" in your device settings before installing.

---

## Security Notes

- Firebase API keys exposed in the frontend are public by design. Security is enforced through Firebase Security Rules on the backend.
- The signing keystore is excluded from this repository.
- `google-services.json` is excluded — you must supply your own.

---

## Roadmap

- [ ] Push notifications via FCM
- [ ] Message search
- [ ] Voice messages
- [ ] User profiles and avatars
- [ ] End-to-end encryption

---

## Developer

**Atharv Pawar**  
Android & Web Developer

[![GitHub](https://img.shields.io/badge/GitHub-atharvpawar16-181717?style=flat-square&logo=github)](https://github.com/atharvpawar16)
[![Portfolio](https://img.shields.io/badge/Portfolio-atharvpawar16.netlify.app-00ff41?style=flat-square&labelColor=000000)](https://atharvpawar16.netlify.app/)

---

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for the full version history.

---

## Contributing

This is a personal project and pull requests are not actively accepted. However, you are welcome to:
- Open an issue if you discover a bug
- Fork the repository and build your own version
- Star the repository if you find it useful

---

## License

© 2026 Atharv Pawar. All rights reserved.  
This project is personal and proprietary. Unauthorized use, copying, or distribution is strictly prohibited.
