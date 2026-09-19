# MindBridge Uganda — React Native + Expo + MVVM

A polished mobile-first prototype based on the MindBridge concept paper.

## Architecture

```text
MindBridge/
├── App.js
├── app.json
├── package.json
├── assets/
│   ├── app-icon.png
│   ├── mindbridge-logo.png
│   ├── photos/                 # photo sources + attribution
│   └── ...
└── src/
    ├── data/                   # media/configuration data
    ├── models/                 # domain models and static datasets
    ├── services/               # persistence + repository layer
    ├── viewmodels/             # MVVM state and business logic
    ├── views/
    │   ├── components/         # reusable UI building blocks
    │   └── screens/            # presentation-only screens
    ├── navigation/             # navigation graph
    └── theme/                  # design system
```

## MVVM rule used here

- **Model:** data structures and static domain data.
- **ViewModel:** screen state, validation, commands and repository calls.
- **View:** React Native screens/components. Views render state and call ViewModel actions.
- **Service/Repository:** persistence and data access. AsyncStorage is used for the local prototype.

## Included flows

- Splash / onboarding / anonymous registration
- Home dashboard
- Verified counsellor directory and profiles
- Booking: text, voice and video session modes
- Persistent local session list
- Session room UI for voice/video bookings with timer and controls (provider integration point)
- Secure-style local chat prototype with persisted messages
- Psychoeducation search and categories
- Anonymous peer-support posting
- Community / faith / health-facility referral flow
- Emergency support with Uganda emergency number 112
- English / Luganda / Runyankore preference
- Profile, anonymous ID and notification preference
- Branded icon, logo and fallback artwork
- Real-world photography through approved Unsplash sources with local fallbacks

## Run

```bash
npm install
npx expo start
```

Then open with Expo Go or an Android emulator.

## Production work still required

This is a project-ready functional prototype, not a clinical production system. Real deployment requires a secure backend, verified professional accounts, encrypted server-side data, real-time messaging, proper voice/video infrastructure, notifications, audit logging, clinical escalation integration, approved crisis contacts, ethical approval and data-protection compliance.
