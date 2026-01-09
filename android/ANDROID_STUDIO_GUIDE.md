# Running SmartExpense in Android Studio 🚀

## Complete Step-by-Step Guide

---

## Part 1: Install Android Studio (If Not Already Installed)

### Download & Install

1. **Download Android Studio**
   - Go to: https://developer.android.com/studio
   - Download the latest version for your OS (Mac/Windows/Linux)

2. **Install Android Studio**
   - **macOS**: 
     - Open the `.dmg` file
     - Drag Android Studio to Applications folder
     - Open Android Studio from Applications
   
   - **Windows**:
     - Run the `.exe` installer
     - Follow installation wizard
     - Accept default settings
   
   - **Linux**:
     - Extract the `.tar.gz` file
     - Run `./studio.sh` from `bin` directory

3. **First Launch Setup**
   - Choose "Standard" installation
   - Accept licenses
   - Wait for SDK components to download (~3-5 GB)
   - This includes:
     - Android SDK
     - Android SDK Platform
     - Android Virtual Device (AVD)

---

## Part 2: Ensure Backend is Running

**CRITICAL**: The app needs the FastAPI backend running!

### Start Backend

```bash
# Open a NEW terminal window
cd /app/backend

# Start the backend server
python server.py
```

**Verify backend is running**:
```bash
# In another terminal
curl http://localhost:8001/api/wallet
# Should return: {"detail":"Not authenticated"}
```

**Keep this terminal open** - backend must run while testing the app!

---

## Part 3: Open Project in Android Studio

### Method 1: Open Existing Project

1. **Launch Android Studio**

2. **Open Project**
   - Click "Open" on welcome screen
   - OR File → Open
   - Navigate to: `/app/android`
   - Click "Open"

3. **Wait for Gradle Sync**
   - Android Studio will automatically sync Gradle
   - This may take 2-5 minutes on first open
   - You'll see progress at bottom: "Gradle Build Running..."
   - **Wait until it says "BUILD SUCCESSFUL"**

### Method 2: From Terminal

```bash
# Open project directly
open -a "Android Studio" /app/android

# Or use 'studio' command if configured
studio /app/android
```

---

## Part 4: Set Up Android Emulator (Virtual Device)

### Option A: Create New Emulator (Recommended)

1. **Open Device Manager**
   - Click device icon in toolbar (phone icon)
   - OR Tools → Device Manager

2. **Create Virtual Device**
   - Click "Create Device"
   - Select a device definition:
     - **Recommended**: Pixel 5 or Pixel 6
     - Click "Next"

3. **Select System Image**
   - Choose Android version:
     - **Recommended**: Android 14.0 (API 34) or Android 13.0 (API 33)
     - Click "Download" if not already downloaded
     - Wait for download to complete
     - Click "Next"

4. **Verify Configuration**
   - AVD Name: "Pixel_5_API_34" (or similar)
   - Startup orientation: Portrait
   - Click "Finish"

### Option B: Use Existing Emulator

- If you already have an emulator, you can use it
- Minimum requirement: **Android 8.0 (API 26) or higher**

---

## Part 5: Run the App

### Step 1: Select Run Configuration

1. **Check toolbar** at top of Android Studio
2. You should see:
   - **app** (in dropdown on left)
   - **Device selector** (in middle)
   - **Run button** ▶️ (green play button)

### Step 2: Select Device

1. Click the **device dropdown** (middle of toolbar)
2. Choose your emulator:
   - "Pixel_5_API_34" (or your emulator name)
   - If emulator not running, it will start automatically

### Step 3: Run App

1. Click the green **Run button** ▶️
   - OR Press `Shift + F10` (Windows/Linux)
   - OR Press `Control + R` (macOS)

2. **First Build**:
   - Takes 2-5 minutes (downloading dependencies)
   - Progress shown at bottom
   - Wait for "BUILD SUCCESSFUL"

3. **Emulator Launch**:
   - Emulator will start automatically
   - Takes 30-60 seconds to boot
   - Wait until home screen appears

4. **App Installation**:
   - App automatically installs
   - App launches automatically
   - You'll see the SmartExpense splash screen!

---

## Part 6: Testing the App

### Initial Test Flow

1. **Register Account**
   - App opens to Login/Register screen
   - Click "Register" button
   - Fill in:
     - Name: "Test User"
     - Email: "test@example.com"
     - Password: "test123"
   - Click "Register"

2. **Dashboard**
   - Should redirect to Dashboard
   - Initially empty (no transactions)
   - Wallet balance: ₹0

3. **Add Categories**
   - Click menu (⋮) → "Manage Categories"
   - Add categories: "Food", "Transport", "Shopping"
   - Go back to Dashboard

4. **Add Expense**
   - Click FAB (+ button)
   - Fill form:
     - Type: Expense
     - Amount: 500
     - Category: Food
     - Method: Cash
     - Date: Today
     - Note: "Lunch"
   - Click "Add"
   - Verify transaction appears on Dashboard

5. **View Analytics**
   - Click "View Analytics" card
   - Should show charts and breakdown
   - Add more transactions to see better visualizations

6. **Set Budget**
   - Menu → "Manage Budgets"
   - Set budget for "Food": ₹5000/monthly
   - Add more expenses to see progress

---

## Part 7: Common Issues & Solutions

### Issue 1: "SDK not found" Error

**Solution**:
```bash
# Set ANDROID_HOME environment variable
# macOS/Linux - Add to ~/.zshrc or ~/.bashrc:
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools

# Windows - Set in System Environment Variables:
ANDROID_HOME = C:\Users\YourName\AppData\Local\Android\Sdk
```

Then restart Android Studio.

### Issue 2: "Build Failed" - Dependency Issues

**Solution**:
1. File → Invalidate Caches → "Invalidate and Restart"
2. Wait for restart and Gradle sync
3. Try running again

### Issue 3: Backend Connection Failed

**Symptoms**: 
- Login fails
- "No internet connection" messages
- Empty dashboard

**Solutions**:

**A. Verify backend is running**:
```bash
curl http://localhost:8001/api/wallet
```

**B. Check emulator can reach localhost**:
```bash
# In terminal
adb shell
# Inside emulator shell
curl http://10.0.2.2:8001/api/wallet
```

Should return: `{"detail":"Not authenticated"}`

**C. If still failing**, the API URL is correctly configured in:
- File: `/app/android/app/build.gradle.kts`
- Line: `buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8001/api\"")`
- **10.0.2.2** = Emulator's way to access host's localhost

### Issue 4: Emulator is Slow

**Solutions**:
1. **Enable Hardware Acceleration**:
   - macOS/Linux: Ensure HAXM/KVM is installed
   - Windows: Enable Hyper-V or HAXM

2. **Allocate More RAM**:
   - Device Manager → Edit emulator
   - Advanced Settings → RAM: 2048 MB or more

3. **Use x86_64 image** (not ARM) for better performance

### Issue 5: App Crashes on Launch

**Check Logcat**:
1. View → Tool Windows → Logcat
2. Filter by "SmartExpense" or "AndroidRuntime"
3. Look for red error messages
4. Common causes:
   - Missing permissions
   - Database initialization failed
   - Network configuration issues

**Solution**: Share the logcat error for specific help

### Issue 6: Gradle Sync Failed

**Solution**:
```bash
# Clean and rebuild
cd /app/android
./gradlew clean
./gradlew build --refresh-dependencies
```

Then retry opening in Android Studio.

---

## Part 8: Using Physical Android Device

### Prerequisites
- Android device (Android 8.0+)
- USB cable
- Developer options enabled

### Enable Developer Options

1. **On Phone**:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Enter PIN if prompted
   - "You are now a developer!" message appears

2. **Enable USB Debugging**:
   - Settings → System → Developer Options
   - Enable "USB Debugging"

3. **Connect Device**:
   - Connect phone to computer via USB
   - Select "File Transfer" mode
   - Allow USB debugging when prompted

### Update Backend URL

Since physical device can't use `10.0.2.2`, you need your computer's IP:

1. **Find your IP address**:
   ```bash
   # macOS/Linux
   ifconfig | grep inet
   # Look for something like: 192.168.1.100
   
   # Windows
   ipconfig
   # Look for IPv4 Address
   ```

2. **Update build.gradle.kts**:
   ```kotlin
   // Change this line:
   buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8001/api\"")
   
   // To (use YOUR IP):
   buildConfigField("String", "API_BASE_URL", "\"http://192.168.1.100:8001/api\"")
   ```

3. **Rebuild app**:
   - Build → Clean Project
   - Build → Rebuild Project

4. **Run on device**:
   - Select your device from device dropdown
   - Click Run ▶️

---

## Part 9: Debugging Tips

### View Logs in Real-Time

1. **Open Logcat**:
   - View → Tool Windows → Logcat
   - OR Alt+6 (Windows/Linux) / Cmd+6 (macOS)

2. **Filter logs**:
   - In search box, type: `SmartExpense`
   - Shows only app logs

3. **Log levels**:
   - 🔴 **E** (Error): Critical issues
   - 🟠 **W** (Warning): Potential problems
   - 🔵 **I** (Info): General information
   - 🟢 **D** (Debug): Debug messages

### Debug with Breakpoints

1. **Set breakpoint**:
   - Click in left margin of code editor (line number area)
   - Red dot appears

2. **Run in Debug mode**:
   - Click Debug button 🐛 (next to Run)
   - OR Shift+F9 (Windows/Linux) / Control+D (macOS)

3. **Step through code**:
   - F8: Step over
   - F7: Step into
   - F9: Resume

### Inspect Database

1. **Open App Inspection**:
   - View → Tool Windows → App Inspection

2. **Database Inspector**:
   - Select your app
   - View tables: users, transactions, categories, budgets
   - Run SQL queries

---

## Part 10: Hot Reload & Live Updates

### Compose Preview

1. **See UI without running**:
   - Open any Composable file (e.g., `DashboardScreen.kt`)
   - Split view shows preview on right
   - Click "Interactive" mode to test interactions

### Apply Changes

1. **After code changes**:
   - Click "Apply Changes" ⚡ button
   - OR Ctrl+F10 (Windows/Linux) / Cmd+F10 (macOS)
   - App updates without full rebuild!

2. **Types of Apply Changes**:
   - **Apply Code Changes**: Fast, most changes
   - **Apply Changes and Restart Activity**: Medium
   - **Run**: Full rebuild (slower)

---

## Part 11: Generate APK for Testing

### Debug APK (For Testing)

1. **Build APK**:
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - Wait for build to complete
   - "locate" link appears in notification

2. **Find APK**:
   - Path: `app/build/outputs/apk/debug/app-debug.apk`
   - Click "locate" to open folder

3. **Install on device**:
   - Transfer APK to phone
   - Enable "Install from Unknown Sources"
   - Open APK to install

---

## Quick Reference Card

### Essential Shortcuts

| Action | Windows/Linux | macOS |
|--------|---------------|-------|
| Run app | Shift+F10 | Control+R |
| Debug app | Shift+F9 | Control+D |
| Stop app | Ctrl+F2 | Cmd+F2 |
| Open Logcat | Alt+6 | Cmd+6 |
| Find file | Ctrl+Shift+N | Cmd+Shift+O |
| Build project | Ctrl+F9 | Cmd+F9 |
| Clean project | N/A | Menu → Build |

### Key Files to Know

| File | Purpose |
|------|---------|
| `build.gradle.kts` | Dependencies, SDK versions |
| `AndroidManifest.xml` | Permissions, activities |
| `MainActivity.kt` | App entry point |
| `AppNavigation.kt` | Navigation setup |
| `*.kt` in screens/ | UI screens |

---

## Troubleshooting Checklist

Before asking for help, verify:

- [ ] Backend is running (`curl http://localhost:8001/api/wallet`)
- [ ] Gradle sync completed successfully
- [ ] Android SDK installed (API 26-34)
- [ ] Emulator is running (home screen visible)
- [ ] USB debugging enabled (physical device)
- [ ] Checked Logcat for errors
- [ ] Tried "Invalidate Caches and Restart"
- [ ] Tried `./gradlew clean build`

---

## Additional Resources

- **Android Studio User Guide**: https://developer.android.com/studio/intro
- **Jetpack Compose Docs**: https://developer.android.com/jetpack/compose
- **Kotlin Docs**: https://kotlinlang.org/docs/home.html

---

## 🎉 You're Ready!

If you followed this guide, you should now have:
- ✅ Android Studio installed and configured
- ✅ Project opened and synced
- ✅ Emulator running
- ✅ App running and ready to test

**Next Steps**:
1. Test all features (Register, Add Expense, Categories, Budgets, Analytics)
2. Try adding real data
3. Check analytics charts
4. Report any issues you find

---

**Need Help?** 
- Check the troubleshooting section above
- Review Logcat for specific errors
- Ensure backend is running on port 8001

**Happy Coding! 🚀**
