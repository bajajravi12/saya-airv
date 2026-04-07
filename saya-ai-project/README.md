# SAYA AI Voice Assistant - Android Application

A complete Android application for an AI Voice Assistant named SAYA, built with Kotlin and modern Android development practices.

## 🎯 Features

### Core Voice Features
- **Wake Word Detection**: Continuously listens for "SAYA" (case-insensitive)
- **Voice Input Capture**: Converts user speech to text using SpeechRecognizer
- **Text-to-Speech Output**: Speaks responses using TextToSpeech API
- **Auto-Loop System**: Automatically returns to listening after each command

### Voice Commands
- `"open whatsapp"` - Launches WhatsApp if installed
- `"open youtube"` - Launches YouTube app
- `"open chrome"` - Opens Chrome browser
- `"time"` - Announces current time
- `"hello"` - Greeting response
- Any other command receives: "Sorry I didn't understand"

### UI Design
- Dark theme with Material Design 3
- Centered microphone button
- Real-time speech display
- Assistant response feedback

### Permissions
- `RECORD_AUDIO` - For microphone access
- `INTERNET` - For app data access
- `QUERY_ALL_PACKAGES` - To check installed apps

## 📋 Project Configuration

| Component | Version |
|-----------|---------|
| Gradle | 8.2 |
| Android Gradle Plugin | 8.1.4 |
| Kotlin | 1.9.10 |
| Compile SDK | 34 |
| Target SDK | 34 |
| Min SDK | 21 |
| JDK | 1.8 |

## 📁 Project Structure

```
saya-ai-project/
├── README.md
├── BUILD_VERIFICATION.md
├── .gitignore
├── build.gradle (root level)
├── gradle.properties
├── settings.gradle
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
└── app/
    ├── build.gradle
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── kotlin/com/saya/ai/
        │   └── MainActivity.kt
        └── res/
            ├── drawable/
            │   └── mic_background.xml
            ├── layout/
            │   └── activity_main.xml
            └── values/
                ├── strings.xml
                └── themes.xml
```

## 🚀 Getting Started

### Prerequisites
- Android Studio (Flamingo or later)
- JDK 11 or newer
- Android SDK 34 installed

### Opening in Android Studio

1. **Clone/Download the project**
   ```bash
   cd /workspaces/saya-ai-/saya-ai-project
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "File" → "Open"
   - Navigate to the project folder
   - Click "OK"

3. **Sync Gradle**
   - Android Studio will automatically prompt to sync Gradle
   - Click "Sync Now" or wait for automatic sync
   - First sync may take 2-5 minutes

4. **Build the Project**
   - Click "Build" → "Make Project" or press `Ctrl+F9`
   - Verify no build errors appear
   - Check the "Build" console at the bottom

### Building and Running

#### Build Debug APK
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

#### Build Release APK
```bash
./gradlew assembleRelease
```
Note: Requires keystore file for signing

#### Run on Emulator/Device
- Connect Android device with USB debugging enabled, OR
- Start an Android emulator
- In Android Studio: "Run" → "Run 'app'" or press `Shift+F10`

#### View Gradle Tasks
```bash
./gradlew tasks
```

#### Clean Build
```bash
./gradlew clean
```

## 🔧 Configuration Details

### Gradle Optimizations
- ✅ Gradle DEXING optimization enabled
- ✅ Parallel task execution (4 workers)
- ✅ Build caching enabled
- ✅ Configuration caching enabled (Gradle 8.0+)
- ✅ G1 garbage collector for JVM
- ✅ Increased JVM heap to 4096MB

### Build Types
- **Debug**: Development builds with debugging enabled, no minification
- **Release**: Production builds with ProGuard minification and resource shrinking

### Dependencies
- AndroidX Core KTX
- AppCompat
- Material Design 3
- ConstraintLayout
- JUnit (Testing)
- Espresso (UI Testing)

## 📝 MainActivity Overview

The main activity handles:
- Speech recognition initialization
- Text-to-speech engine setup
- Wake word listening loop
- Command processing
- App launching intents
- Permission requests
- Error handling and recovery

### Key Methods
- `initializeSpeech()` - Initialize SpeechRecognizer
- `startWakeWordListening()` - Begin listening for "SAYA"
- `processSpeech(text)` - Handle wake word detection
- `startVoiceInput()` - Start command input after wake word
- `executeCommand(command)` - Process and execute voice commands
- `openApp(packageName)` - Launch apps via Intent
- `speak(text)` - Output via TextToSpeech

## 🎨 Themes and Styling

- **Theme**: Material Design 3 Dark Night theme
- **Primary Color**: Black
- **Secondary Color**: Dark Gray
- **Text Color**: White
- **Background**: Black

## 🐛 Troubleshooting

### Gradle Sync Fails
- Check internet connection
- Click "File" → "Invalidate Caches" → "Invalidate and Restart"
- Delete `.gradle` folder and sync again

### Build Fails with "No application modules found"
- Verify `settings.gradle` contains `include ':app'`
- Verify `app/` folder exists with `build.gradle`

### Microphone Permission Issues
- Grant RECORD_AUDIO permission when app prompts
- Check Android device settings: Settings → Apps → SAYA → Permissions

### Voice Recognition Not Working
- Ensure internet connection (Google Speech Recognition requires it)
- Check microphone functionality on device
- Verify RECORD_AUDIO and INTERNET permissions are granted

### TTS Not Working
- Check if TTS engine is installed (usually pre-installed)
- Go to Settings → Language & input → Text-to-speech output
- Ensure default TTS engine is selected

## 📊 Build Output

After successful build:
```
app/build/outputs/apk/debug/app-debug.apk (3-5 MB)
```

## 📄 Development Notes

### Adding New Commands
Edit `executeCommand()` in [MainActivity.kt](app/src/main/kotlin/com/saya/ai/MainActivity.kt):
```kotlin
when {
    command.contains("your command") -> {
        speak("Your response")
        // Your action here
    }
}
```

### Changing Wake Word
Edit in `processSpeech()` method:
```kotlin
if (text.contains("your_word")) {
    speak("Response")
    startVoiceInput()
}
```

### Customizing UI
Edit layout file: [activity_main.xml](app/src/main/res/layout/activity_main.xml)

Edit styles: [themes.xml](app/src/main/res/values/themes.xml)

### Updating Dependencies
Edit `dependencies` block in [app/build.gradle](app/build.gradle)

## 📦 Version History

- **v1.0** (Apr 7, 2026) - Initial release
  - Wake word detection
  - Voice input and output
  - Command execution
  - Dark theme UI

## ⚖️ License

This project is provided as-is for educational and development purposes.

## 🤝 Support

For build issues or questions:
1. Check [BUILD_VERIFICATION.md](BUILD_VERIFICATION.md) for configuration details
2. Review the gradle sync console output
3. Verify all Android SDKs are properly installed in Android Studio

## 🎓 Learning Resources

- [Android Development Docs](https://developer.android.com)
- [Kotlin Language](https://kotlinlang.org)
- [AndroidX Libraries](https://developer.android.com/jetpack)
- [Gradle Documentation](https://gradle.org/docs)

---

**SAYA AI Assistant v1.0**
Built with Kotlin and modern Android development practices.
Ready for production builds and deployment.
