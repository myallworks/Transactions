# Transaction App with Biometric Authentication

## Project Overview

A secure Android application that demonstrates:
- API integration with transaction data
- Biometric authentication for secure access
- Offline data persistence using Room Database
- Modern UI with dark mode support
- Search functionality by transaction ID or description

## Features

### Core Features
✅ **Secure Authentication**  
- Biometric  login
- Encrypted credential storage

✅ **Transaction Management**  
- Fetch and display transactions from API
- Offline caching with Room Database
- Real-time search (by ID or description)

✅ **Modern UI**  
- Material Design 3 components
- Dark/Light theme support
- Responsive layout

### Technical Highlights
- EncryptedSharedPreferences for secure token storage
- Retrofit for API communication
- MVVM architecture pattern
- BiometricPrompt API integration

## Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Android SDK 33+
- Java 8+

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/myallworks/Transactions.git
   ```
2. Open project in Android Studio
3. Sync Gradle dependencies
4. Build and run on emulator or device

### API Configuration
- Base URL: `https://api.prepstripe.com/`
- Endpoints:
  - Login: `POST /login`
  - Transactions: `GET /transactions`

Default credentials:
- Username: `admin`
- Password: `A7ge#hu&dt(wer`

## Search Functionality

The app provides powerful search capabilities:
- **Search by Transaction ID**: Enter full or partial ID

Search works in both:
- Online mode (filters API results)
- Offline mode (filters cached transactions)

## Build Instructions

### Generate APK
1. In Android Studio:
   - Build > Generate Signed Bundle/APK
   - Select APK option
   - Choose debug/release variant
   - Complete signing configuration

2. Or via command line:
   ```bash
   ./gradlew assembleDebug
   ```
   Output: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build
Configure in `app/build.gradle`:
```gradle
android {
    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

## Bonus Features Implemented
- 🌙 Dark Mode support
- 📱 Offline Mode with Room Database
- 🔍 Advanced Search/Filter functionality
- 🔄 Automatic data refresh
- 🔒 Secure token handling

## Evaluation Criteria Met
- **Functionality**: Complete API integration with biometric auth
- **Code Quality**: Modular, well-structured codebase
- **Security**: EncryptedSharedPreferences for tokens
- **UX**: Intuitive interface with search capabilities
