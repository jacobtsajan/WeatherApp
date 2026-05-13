# ☁️ WeatherApp

A clean, minimal Android weather application that fetches real-time weather data for any city using the [OpenWeatherMap API](https://openweathermap.org/api). Built with Java and designed with a gradient dark UI.

![Android](https://img.shields.io/badge/Android-7.0%2B-green?logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-8-orange?logo=openjdk&logoColor=white)
![API](https://img.shields.io/badge/API-OpenWeatherMap-blue?logo=cloud&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green)

---

## ✨ Features

- **Search by city name** — Type any city and get instant weather data
- **Real-time weather data** — Temperature, humidity, wind speed, and weather description
- **Dynamic weather icons** — Displays condition-specific icons (sunny, cloudy, rain, snow, etc.) matching OpenWeatherMap's icon codes
- **Gradient dark UI** — Sleek dark background with a modern, card-style layout
- **Custom typography** — Uses the Urbanist font for a clean, modern look
- **Default city** — Loads weather for Kochi on app launch

---

## 📸 Screenshots

> *Add screenshots of your app here*

---

## 🏗️ Architecture

This is a **single-Activity** Android app with a straightforward architecture:

```
User Input (city name)
       │
       ▼
┌──────────────────┐     ┌─────────────────────────┐     ┌──────────────┐
│  MainActivity    │────▶│  OpenWeatherMap API      │────▶│  JSON Parse  │
│  (UI + Logic)    │     │  (OkHttp GET request)   │     │  (org.json)  │
└──────────────────┘     └─────────────────────────┘     └──────┬───────┘
                                                                │
                                                                ▼
                                                         Update UI Views
                                                    (temp, humidity, wind,
                                                     icon, description)
```

---

## 📁 Project Structure

```
WeatherApp/
├── app/
│   ├── build.gradle.kts                    # App-level dependencies & SDK config
│   ├── proguard-rules.pro                  # ProGuard rules for release builds
│   └── src/main/
│       ├── AndroidManifest.xml             # Permissions (INTERNET) & activity declaration
│       ├── java/.../weatherapp/
│       │   └── MainActivity.java           # Main (and only) activity — handles UI & API calls
│       └── res/
│           ├── drawable/                   # Weather icons (ic_01d–ic_50n), backgrounds, assets
│           ├── font/urbanist.xml           # Custom Urbanist font definition
│           ├── layout/activity_main.xml    # UI layout — city, temp, humidity, wind, search
│           ├── mipmap-*/                   # App launcher icons (all densities)
│           ├── values/                     # Colors, strings, themes
│           └── xml/                        # Backup & data extraction rules
├── build.gradle.kts                        # Root-level Gradle config (AGP 8.12.0)
├── settings.gradle.kts                     # Project settings & repositories
├── gradle/                                 # Gradle wrapper
├── gradlew / gradlew.bat                   # Gradle wrapper scripts
└── .gitignore
```

---

## 🛠️ Tech Stack

| Component          | Technology                                    |
| ------------------ | --------------------------------------------- |
| **Language**        | Java 8                                       |
| **UI Framework**    | Android XML Layouts (RelativeLayout + LinearLayout) |
| **HTTP Client**     | [OkHttp 4.9.3](https://square.github.io/okhttp/) |
| **Weather API**     | [OpenWeatherMap Current Weather](https://openweathermap.org/current) |
| **JSON Parsing**    | `org.json` (built-in Android)                |
| **Threading**       | `ExecutorService` (single-thread executor)   |
| **Design Library**  | Material Components 1.12.0                   |
| **Min SDK**         | 24 (Android 7.0 Nougat)                     |
| **Target SDK**      | 34 (Android 14)                              |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Hedgehog or later recommended)
- **JDK 17+** (bundled with Android Studio)
- An **OpenWeatherMap API key** — [get one free here](https://openweathermap.org/appid)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/jacobtsajan/WeatherApp.git
   ```

2. **Open in Android Studio**
   - File → Open → select the `WeatherApp` folder

3. **Replace the API key** (optional — a key is already included)
   
   In `MainActivity.java`, line 28:
   ```java
   private static final String API_KEY = "YOUR_API_KEY_HERE";
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click ▶️ Run

---

## 🔑 API Reference

The app calls the [OpenWeatherMap Current Weather API](https://openweathermap.org/current):

```
GET https://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}&units=metric
```

### Response fields used:

| JSON Path                        | UI Element       | Example        |
| -------------------------------- | ---------------- | -------------- |
| `name`                           | City name        | "Kochi"        |
| `main.temp`                      | Temperature      | "30°"          |
| `main.humidity`                   | Humidity         | "75%"          |
| `wind.speed`                      | Wind speed       | "12 km/h"      |
| `weather[0].description`         | Description      | "clear sky"    |
| `weather[0].icon`                 | Weather icon     | "01d" → `ic_01d.png` |

---

## 🎨 UI Design

- **Background**: Custom gradient drawable (dark blue → deep purple)
- **Font**: Urbanist (Google Fonts) — loaded via Android's downloadable fonts
- **Temperature color**: Amber (`#FFBF00`) for visual emphasis
- **Button**: Dark navy (`#2B3A67`) with white text
- **Layout**: Vertically stacked — city → temperature → humidity/wind row → weather icon → description → search input → button

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 🙏 Acknowledgements

- [OpenWeatherMap](https://openweathermap.org/) — Free weather API
- [OkHttp](https://square.github.io/okhttp/) — HTTP client for Android
- [Google Fonts — Urbanist](https://fonts.google.com/specimen/Urbanist) — Typography
