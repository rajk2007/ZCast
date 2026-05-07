# ZCast
A premium Android streaming app powered by CloudStream.

## Build (Debug)
```bash
./gradlew assembleDebug
```
APK output: `app/build/outputs/apk/debug/app-debug.apk`

## First Run
On first launch, ZCast prompts to install content repos automatically.

## GitHub Actions
Every push to `main` auto-builds a debug APK downloadable from the Actions tab.

## Stack
- Language: Kotlin
- Min SDK: 21
- Target SDK: 34
- Build: Gradle + GitHub Actions
