# SmartExpense React Native App - Complete Setup Guide 📱

## 🎉 Your React Web App has been Converted to React Native!

---

## 📋 What Was Converted

### React Web → React Native Conversions:

| Web Component | React Native Component |
|---------------|------------------------|
| `<div>` | `<View>` |
| `<span>`, `<p>`, `<h1>` | `<Text>` |
| `<button>` | `<TouchableOpacity>` or Custom `<Button>` |
| `<input>` | `<TextInput>` or Custom `<Input>` |
| CSS/Tailwind | `StyleSheet` |
| React Router | React Navigation |
| `localStorage` | `AsyncStorage` |
| `recharts` | `react-native-chart-kit` |

### Screens Converted:
✅ **AuthPage.jsx** → **AuthScreen.js**  
✅ **Dashboard.jsx** → **DashboardScreen.js**  
✅ **CategoryManager.jsx** → **CategoryScreen.js**  
✅ **BudgetManager.jsx** → **BudgetScreen.js**  
✅ **Analytics.jsx** → **AnalyticsScreen.js**  
✅ **AddExpenseModal.jsx** → **AddExpenseScreen.js**

### Features Included:
- ✅ Authentication (Login/Register)
- ✅ Dashboard with wallet & transactions
- ✅ Add/Delete transactions
- ✅ Category management
- ✅ Budget management  
- ✅ Analytics with charts
- ✅ Navigation (Stack Navigator)
- ✅ Offline storage (AsyncStorage)
- ✅ API integration (same backend)

---

## 🚀 Quick Start (3 Steps)

### Step 1: Ensure Backend is Running

```bash
# Terminal 1: Start backend
cd /app/backend
python server.py

# Verify:
curl http://localhost:8001/api/wallet
```

### Step 2: Install Dependencies & Setup

```bash
cd /app/mobile

# Install Node modules (already done)
yarn install

# Install iOS pods (macOS only - SKIP for now)
# cd ios && pod install && cd ..
```

### Step 3: Run on Android

```bash
# Start Metro bundler
yarn start

# In another terminal, run Android
yarn android
```

---

## 📱 Detailed Android Setup

### Prerequisites

1. **Android Studio** installed
2. **JDK 17** installed
3. **Android SDK** (API 23-34)
4. **Android Emulator** OR Physical device

### Method 1: Using Android Studio (Recommended)

1. **Open Android Studio**
2. **Open Project**: `/app/mobile/android`
3. **Wait for Gradle Sync**
4. **Create/Start Emulator**:
   - Tools → Device Manager
   - Create Pixel 5 with API 33/34
5. **In Terminal** (from `/app/mobile`):
   ```bash
   yarn start
   ```
6. **In Another Terminal**:
   ```bash
   yarn android
   ```

### Method 2: Command Line Only

```bash
cd /app/mobile

# Start Metro
yarn start &

# Run Android
yarn android

# Or manually:
cd android
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.smartexpense/.MainActivity
```

---

## 🔧 Configuration

### Backend URL Configuration

**File**: `/app/mobile/src/config/api.js`

```javascript
// For Android Emulator:
const API_BASE_URL = 'http://10.0.2.2:8001/api';

// For Physical Device (replace with your computer's IP):
const API_BASE_URL = 'http://YOUR_IP_ADDRESS:8001/api';

// Find your IP:
// macOS/Linux: ifconfig | grep inet
// Windows: ipconfig
```

---

## 📂 Project Structure

```
/app/mobile/
├── android/                    # Android native code
│   ├── app/
│   │   ├── build.gradle
│   │   └── src/main/
│   │       ├── AndroidManifest.xml
│   │       └── java/com/smartexpense/
│   ├── build.gradle
│   └── settings.gradle
├── src/
│   ├── components/             # Reusable components
│   │   ├── Button.js
│   │   ├── Input.js
│   │   └── Card.js
│   ├── config/
│   │   └── api.js             # API configuration
│   ├── context/
│   │   └── AuthContext.js     # Auth state management
│   ├── navigation/
│   │   └── AppNavigator.js    # Navigation setup
│   ├── screens/               # All screens
│   │   ├── AuthScreen.js
│   │   ├── DashboardScreen.js
│   │   ├── AddExpenseScreen.js
│   │   ├── CategoryScreen.js
│   │   ├── BudgetScreen.js
│   │   └── AnalyticsScreen.js
│   └── utils/
│       └── currency.js
├── App.js                      # App entry point
├── index.js                    # RN entry point
├── package.json
└── metro.config.js
```

---

## 🎨 Key Features & Usage

### 1. Authentication
- **Register**: Create new account
- **Login**: Access existing account
- **Auto-login**: Token persisted in AsyncStorage

### 2. Dashboard
- **Wallet Card**: Shows current balance
- **Overview Cards**: Income & Expense summary
- **Transaction List**: Recent 10 transactions
- **FAB Button**: Add new transaction
- **Header Icons**: Quick access to Analytics, Categories, Budgets, Logout

### 3. Add Transaction
- **Type Toggle**: Switch between Expense/Income
- **Amount Input**: Numeric keypad
- **Category Picker**: Dropdown with all categories
- **Payment Method**: Cash, UPI, Debit Card, Credit Card
- **Date Picker**: Native date picker
- **Notes**: Optional text input

### 4. Categories
- **Add Category**: Inline form
- **Delete Category**: Swipe or tap delete icon
- **Category List**: All user categories

### 5. Budgets
- **Set Budget**: Category + Amount + Period
- **Budget List**: All active budgets
- **Delete Budget**: Remove budget

### 6. Analytics
- **Summary Cards**: Income, Expense, Net Savings
- **Bar Chart**: Top 5 categories
- **Category Breakdown**: Full list with percentages & progress bars

---

## 🐛 Troubleshooting

### Issue 1: "Could not connect to development server"

**Solution**:
```bash
# Kill existing Metro
pkill -f "react-native"

# Restart
yarn start --reset-cache
```

### Issue 2: "Unable to load script"

**Solution**:
```bash
# Clear cache
cd android
./gradlew clean
cd ..
yarn start --reset-cache

# In another terminal
yarn android
```

### Issue 3: Backend Connection Failed (Network Error)

**Symptoms**: Login fails, "Network Error" alerts

**Solutions**:

**A. For Emulator**: Ensure API URL is `http://10.0.2.2:8001/api`

**B. For Physical Device**:
1. Find your computer's IP:
   ```bash
   # macOS/Linux
   ifconfig | grep "inet " | grep -v 127.0.0.1
   
   # Windows
   ipconfig | findstr IPv4
   ```

2. Update `/app/mobile/src/config/api.js`:
   ```javascript
   const API_BASE_URL = 'http://192.168.1.100:8001/api'; // Your IP
   ```

3. Rebuild:
   ```bash
   yarn android
   ```

**C. Verify backend is accessible**:
```bash
# From computer
curl http://localhost:8001/api/wallet

# From emulator
adb shell
curl http://10.0.2.2:8001/api/wallet
```

### Issue 4: "Application has not been registered"

**Solution**: Ensure these match:
- `app.json`: `"name": "SmartExpense"`
- `index.js`: `AppRegistry.registerComponent('SmartExpense', ...)`
- `android/app/src/main/res/values/strings.xml`: `<string name="app_name">SmartExpense</string>`

### Issue 5: Build Failed - Gradle Errors

**Solution**:
```bash
cd android

# Clean
./gradlew clean

# Check Java version (must be 17)
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/jdk-17

# Rebuild
./gradlew assembleDebug
```

### Issue 6: Metro Bundler Port Already in Use

**Solution**:
```bash
# Kill process on port 8081
lsof -ti:8081 | xargs kill -9

# Or use different port
yarn start --port 8082
```

---

## 📊 Comparison: Web vs Native

| Feature | React Web | React Native |
|---------|-----------|--------------|
| **Routing** | React Router | React Navigation |
| **Storage** | localStorage | AsyncStorage |
| **Styling** | CSS/Tailwind | StyleSheet |
| **Icons** | Font/SVG | react-native-vector-icons |
| **Charts** | recharts | react-native-chart-kit |
| **Forms** | HTML inputs | TextInput |
| **Buttons** | HTML button | TouchableOpacity |
| **Scroll** | CSS overflow | ScrollView |
| **Platform** | Browser | iOS/Android |

---

## 🔄 Key Code Differences

### Before (React Web):
```jsx
<div className="card">
  <h2>Hello</h2>
  <button onClick={handleClick}>
    Click me
  </button>
</div>
```

### After (React Native):
```jsx
<View style={styles.card}>
  <Text style={styles.heading}>Hello</Text>
  <TouchableOpacity onPress={handleClick}>
    <Text style={styles.buttonText}>Click me</Text>
  </TouchableOpacity>
</View>

const styles = StyleSheet.create({
  card: {
    padding: 16,
    backgroundColor: '#fff',
  },
  heading: {
    fontSize: 20,
    fontWeight: 'bold',
  },
});
```

---

## 🚢 Building Release APK

### Debug APK (Testing)
```bash
cd android
./gradlew assembleDebug

# APK location:
# android/app/build/outputs/apk/debug/app-debug.apk
```

### Release APK (Production)

1. **Generate Keystore**:
   ```bash
   keytool -genkeypair -v -storetype PKCS12 -keystore smartexpense-release.keystore -alias smartexpense -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Configure Signing** in `android/app/build.gradle`:
   ```gradle
   android {
       signingConfigs {
           release {
               storeFile file('smartexpense-release.keystore')
               storePassword 'YOUR_PASSWORD'
               keyAlias 'smartexpense'
               keyPassword 'YOUR_PASSWORD'
           }
       }
       buildTypes {
           release {
               signingConfig signingConfigs.release
           }
       }
   }
   ```

3. **Build**:
   ```bash
   cd android
   ./gradlew assembleRelease
   
   # APK: android/app/build/outputs/apk/release/app-release.apk
   ```

---

## 📝 Testing Workflow

1. **Start Backend**:
   ```bash
   cd /app/backend && python server.py
   ```

2. **Start Metro**:
   ```bash
   cd /app/mobile && yarn start
   ```

3. **Run App**:
   ```bash
   # In another terminal
   yarn android
   ```

4. **Test Flow**:
   - Register: test@example.com / test123
   - Add Categories: Food, Transport, Shopping
   - Add Expense: ₹500 in Food
   - Set Budget: ₹5000 for Food
   - View Analytics: Check charts

5. **Hot Reload**: Save files → App updates automatically!

---

## 🎯 Next Steps

### Enhancements You Can Add:

1. **Biometric Auth**: Fingerprint/Face ID login
2. **Push Notifications**: Budget alerts
3. **Camera**: Receipt scanning with OCR
4. **Offline Sync**: Queue operations when offline
5. **Dark Mode**: Theme switching
6. **Widgets**: Home screen widgets
7. **Animations**: Smooth transitions with Animated API
8. **i18n**: Multi-language support

---

## 📚 Resources

- **React Native Docs**: https://reactnative.dev
- **React Navigation**: https://reactnavigation.org
- **Chart Kit**: https://github.com/indiespirit/react-native-chart-kit
- **Vector Icons**: https://github.com/oblador/react-native-vector-icons

---

## ✅ Checklist

Before running:
- [ ] Backend running on port 8001
- [ ] Node modules installed (`yarn install`)
- [ ] Android Studio installed
- [ ] Emulator created or device connected
- [ ] API URL configured correctly
- [ ] Java 17 installed

To run:
- [ ] `yarn start` (Metro bundler)
- [ ] `yarn android` (Build & install)
- [ ] Test authentication
- [ ] Test all screens
- [ ] Verify backend connectivity

---

## 🎉 You're Ready!

Your React web app is now a fully functional React Native mobile app!

**Command Summary**:
```bash
# 1. Start backend
cd /app/backend && python server.py

# 2. Start Metro (new terminal)
cd /app/mobile && yarn start

# 3. Run Android (new terminal)
cd /app/mobile && yarn android
```

**Need help?** Check the troubleshooting section or review the original React code in `/app/frontend` for reference.

Happy Mobile Development! 🚀📱
