# Android Build Guide 🛠️

## Quick Start

### 1. Ensure Backend is Running

Make sure your FastAPI backend is running on `localhost:8001`:

```bash
cd /app/backend
python server.py
```

Verify with:
```bash
curl http://localhost:8001/api/auth/login
```

### 2. Build the Android Project

```bash
cd /app/android

# Clean previous builds
./gradlew clean

# Build debug APK
./gradlew assembleDebug
```

### 3. Expected Output

If successful, you'll see:
```
BUILD SUCCESSFUL in Xms
XX actionable tasks: XX executed
```

APK location:
```
/app/android/app/build/outputs/apk/debug/app-debug.apk
```

## Testing with Android Emulator

### Option 1: Using Android Studio

1. Open Android Studio
2. Open `/app/android` as a project
3. Wait for Gradle sync
4. Click "Run" (green play button)
5. Select an emulator or connected device

### Option 2: Command Line (Advanced)

```bash
# List available AVDs
emulator -list-avds

# Start emulator
emulator -avd <avd_name> &

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.smartexpense.app/.MainActivity
```

## Testing with Physical Device

### 1. Enable Developer Mode on Android Device
- Go to Settings → About Phone
- Tap "Build Number" 7 times
- Enable USB Debugging in Developer Options

### 2. Update Backend URL

Edit `/app/android/app/build.gradle.kts`:

```kotlin
buildConfigField("String", "API_BASE_URL", "\"http://YOUR_MACHINE_IP:8001/api\"")
```

Find your machine's IP:
```bash
ifconfig | grep inet  # macOS/Linux
ipconfig              # Windows
```

### 3. Connect and Install

```bash
# Connect device via USB
# Enable File Transfer mode on device

# Verify connection
adb devices

# Install
./gradlew installDebug
```

## Troubleshooting

### Build Fails - "SDK not found"

**Solution**: Install Android SDK
```bash
# Download Android command line tools
wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
unzip commandlinetools-linux-9477386_latest.zip
mv cmdline-tools latest
mkdir -p $HOME/android-sdk/cmdline-tools
mv latest $HOME/android-sdk/cmdline-tools/

# Set environment variables
export ANDROID_HOME=$HOME/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin

# Install required components
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### Build Fails - "Java version mismatch"

**Solution**: Ensure Java 17 is used
```bash
java -version  # Should show 17.x.x

# If not, set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64
```

### Emulator Can't Connect to Backend

**Problem**: `10.0.2.2` is not reaching localhost

**Solutions**:

1. **Check backend is running**:
   ```bash
   curl http://localhost:8001/api/auth/login
   ```

2. **From emulator, test connection**:
   ```bash
   adb shell
   curl http://10.0.2.2:8001/api/auth/login
   ```

3. **Alternative**: Use actual IP instead of 10.0.2.2

### App Crashes on Launch

**Check logs**:
```bash
adb logcat | grep SmartExpense
```

Common issues:
- Missing internet permission (already added)
- Backend not accessible
- API endpoint mismatch

## Build Variants

### Debug Build (Development)
```bash
./gradlew assembleDebug
```
- Includes debugging symbols
- Logging enabled
- No code obfuscation

### Release Build (Production)
```bash
./gradlew assembleRelease
```
- Optimized and minified
- ProGuard enabled
- Requires signing for distribution

## Signing Release APK

### 1. Generate Keystore
```bash
keytool -genkey -v -keystore smartexpense-release.keystore \
  -alias smartexpense -keyalg RSA -keysize 2048 -validity 10000
```

### 2. Configure Signing in `app/build.gradle.kts`

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("smartexpense-release.keystore")
            storePassword = "YOUR_STORE_PASSWORD"
            keyAlias = "smartexpense"
            keyPassword = "YOUR_KEY_PASSWORD"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### 3. Build Signed APK
```bash
./gradlew assembleRelease
```

## Distribution

### Google Play Store
1. Build signed release APK
2. Create developer account ($25 one-time fee)
3. Upload APK to Play Console
4. Fill store listing details
5. Submit for review

### Direct Distribution
- Share `app-release.apk` directly
- Users must enable "Install from Unknown Sources"

## Performance Optimization

### Enable R8 (Recommended for Release)

Already configured in `proguard-rules.pro`

### Reduce APK Size
```kotlin
android {
    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
        }
    }
}
```

## Next Steps

 After successful build:
1. Test authentication flow
2. Add test transactions
3. Verify wallet balance updates
4. Test offline mode
5. Check biometric authentication (if device supports)

## Useful Commands

```bash
# View build logs
./gradlew assembleDebug --info

# Run unit tests
./gradlew test

# Generate test coverage report
./gradlew jacocoTestReport

# Lint check
./gradlew lint

# Dependency updates
./gradlew dependencyUpdates

# Clean project
./gradlew clean

# Force dependency refresh
./gradlew build --refresh-dependencies
```

---

**Need Help?** Check the main README.md or consult Android documentation.
