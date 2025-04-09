# Healio - A Health App

Healio is a health tracking Android app built with Kotlin and Jetpack Compose. It helps users manage their health by tracking steps, water intake, medicines, and exercise. It also offers a BMI calculator as well as calendar reminders to help the user stay on track.

## Features
- Step tracking using phone sensors (step detector or accelerometer fallback)
- Water intake tracker
- Calorie tracker
- BMI calculator
- Medicine logger
- Exercise logger
- A calendar for helpful reminders

## Project Structure
### Data/Repository/
- Handles communication with Firebase (Firestore, Authentication).
- Provides data to ViewModels.

### Data/Models/
- Holds data classes that are used across the app.
  - UI data classes (e.g., `TabItem` for the navbar).
  - API response data classes.

### UI/Components/
- Components that are used across multiple screens (e.g., navbar, buttons).

### UI/Screens/{screenName}/
- Contains UI code used specifically in that screen.
- Contains the ViewModel for that screen (ViewModel holds logic between data and UI).

### Navigation/
- `HealioNavigation.kt` has the main `NavHost` that is loaded in `MainActivity`. It holds the final navigation.
- `Routes.kt` has route constants that are used across the app. This ensures that the route names stay the same.

### DI/
- Configured with Hilt.
- Provides singletons.

### Utils/
- Utility classes not tied to UI.

