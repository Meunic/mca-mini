# SmartNative - React Native Expense Tracker 📱

> A beautiful, feature-rich mobile expense tracking application built with React Native

[![React Native](https://img.shields.io/badge/React%20Native-0.73-blue.svg)](https://reactnative.dev/)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 🌟 Features

- 📊 **Dashboard** - Overview of wallet balance, income, and expenses
- 💰 **Transaction Management** - Add, view, and delete transactions
- 🏷️ **Categories** - Organize expenses by custom categories
- 💳 **Budget Tracking** - Set and monitor budgets by category
- 📈 **Analytics** - Visual charts and spending insights
- 🔐 **Authentication** - Secure login and registration
- 💾 **Offline Support** - AsyncStorage for local data persistence
- 🎨 **Beautiful UI** - Material Design with gradient themes
- ₹ **INR Currency** - Indian Rupee formatting throughout

## 📸 Screenshots

*Add screenshots here after running the app*

## 🏗️ Tech Stack

- **React Native 0.73** - Mobile framework
- **React Navigation** - Navigation library
- **Axios** - HTTP client
- **AsyncStorage** - Local storage
- **React Native Chart Kit** - Charts and graphs
- **React Native Vector Icons** - Icon library
- **FastAPI Backend** - REST API (separate repository)

## 📋 Prerequisites

Before you begin, ensure you have:

- **Node.js** (v18 or higher)
- **npm** or **yarn**
- **Android Studio** (for Android development)
- **JDK 17**
- **Android SDK** (API 23-34)
- **Backend API** running on `http://localhost:8001`

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/smartNative.git
cd smartNative
```

### 2. Install Dependencies

```bash
# Using yarn (recommended)
yarn install

# Or using npm
npm install
```

### 3. Start Backend Server

*Note: You need the backend API running. Clone the backend repository separately.*

```bash
# In another terminal
cd /path/to/backend
python server.py
```

Verify backend is running:
```bash
curl http://localhost:8001/api/wallet
# Should return: {"detail":"Not authenticated"}
```

### 4. Configure API URL

**For Android Emulator** (default):
- API URL is already set to `http://10.0.2.2:8001/api`
- No changes needed ✅

**For Physical Device**:
1. Find your computer's IP:
   ```bash
   # macOS/Linux
   ifconfig | grep inet
   
   # Windows
   ipconfig
   ```

2. Update `src/config/api.js`:
   ```javascript
   const API_BASE_URL = 'http://YOUR_IP_ADDRESS:8001/api';
   ```

### 5. Run on Android

**Method 1: Using React Native CLI**

```bash
# Start Metro bundler
yarn start

# In another terminal, run Android
yarn android
```

**Method 2: Using Android Studio**

1. Open `android/` folder in Android Studio
2. Wait for Gradle sync
3. Create/start an emulator
4. Run:
   ```bash
   yarn start
   yarn android
   ```

## 📱 Usage

### First Time Setup

1. **Register**: Create a new account
   - Email: `test@example.com`
   - Password: `test123`

2. **Add Categories**: 
   - Tap menu → "Manage Categories"
   - Add: Food, Transport, Shopping, etc.

3. **Add Transaction**:
   - Tap the `+` FAB button
   - Select type (Expense/Income)
   - Enter amount, category, date
   - Tap "Add Transaction"

4. **Set Budget**:
   - Tap menu → "Manage Budgets"
   - Select category and set amount
   - Choose period (Monthly/Weekly)

5. **View Analytics**:
   - Tap menu → "Analytics"
   - View charts and spending breakdown

## 🗂️ Project Structure

```
smartnative/
├── android/                 # Android native code
│   ├── app/
│   │   ├── build.gradle
│   │   └── src/
│   ├── build.gradle
│   └── settings.gradle
├── src/
│   ├── components/          # Reusable UI components
│   │   ├── Button.js
│   │   ├── Card.js
│   │   └── Input.js
│   ├── config/
│   │   └── api.js          # API configuration
│   ├── context/
│   │   └── AuthContext.js  # Authentication state
│   ├── navigation/
│   │   └── AppNavigator.js # Navigation setup
│   ├── screens/            # All app screens
│   │   ├── AuthScreen.js
│   │   ├── DashboardScreen.js
│   │   ├── AddExpenseScreen.js
│   │   ├── CategoryScreen.js
│   │   ├── BudgetScreen.js
│   │   └── AnalyticsScreen.js
│   └── utils/
│       └── currency.js     # Currency formatting
├── App.js                  # Root component
├── index.js                # Entry point
├── package.json
├── metro.config.js
└── README.md
```

## 🔧 Configuration

### Backend API

Edit `src/config/api.js` to change the backend URL:

```javascript
// For Android Emulator
const API_BASE_URL = 'http://10.0.2.2:8001/api';

// For Physical Device
const API_BASE_URL = 'http://192.168.1.100:8001/api'; // Your IP

// For Production
const API_BASE_URL = 'https://your-api-domain.com/api';
```

## 🐛 Troubleshooting

### Metro Bundler Issues

```bash
# Clear cache and restart
pkill -f "react-native"
yarn start --reset-cache
```

### Build Errors

```bash
# Clean Android build
cd android
./gradlew clean
cd ..
yarn android
```

### Backend Connection Failed

```bash
# Test backend connectivity
curl http://localhost:8001/api/wallet

# From emulator
adb shell
curl http://10.0.2.2:8001/api/wallet
```

### Port Already in Use

```bash
# Kill process on port 8081
lsof -ti:8081 | xargs kill -9

# Or use different port
yarn start --port 8082
```

## 📦 Building APK

### Debug APK

```bash
cd android
./gradlew assembleDebug

# APK location:
# android/app/build/outputs/apk/debug/app-debug.apk
```

### Release APK

1. Generate keystore:
   ```bash
   keytool -genkeypair -v -storetype PKCS12 -keystore smartnative-release.keystore -alias smartnative -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Configure signing in `android/app/build.gradle`

3. Build:
   ```bash
   cd android
   ./gradlew assembleRelease
   ```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@YOUR_USERNAME](https://github.com/YOUR_USERNAME)

## 🙏 Acknowledgments

- React Native Community
- FastAPI Backend Framework
- Material Design Icons

## 📞 Support

For support, email your-email@example.com or open an issue in the repository.

---

⭐️ If you find this project useful, please give it a star!

**Made with ❤️ using React Native**
