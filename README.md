# android-homework-problem

This project serves as a starter template for a typical list/details Android coding problem.  The project utilizes the latest Android libraries including Jetpack Compose,
Dagger Hilt, Retrofit, Coil, Timber, MockK, Espresso, and Kotlin DSL gradle build scripts. 

## Features

- **Architecture:** MVVM architecture
- **UI:** Jetpack Compose for UI and Navigation
- **Networking:** Utilizes Retrofit for API calls to fetch data from the GitHub API
- **Dependency Injection:** Leverages Dagger Hilt for dependency management
- **Testing:** Includes unit tests with MockK and Compose UI screenshot tests with Espresso

## Project Overview

The app fetches a list of GitHub repositories from the [GitHub API](https://docs.github.com/en/rest) and displays them in a list. When a repository is tapped, the app navigates to a detailed view showing 
additional information about the selected repository.  Paging is supported by swiping up on the list page.

### Key Screens
1. **Repository List:** Displays a list of repositories with key details like name, description, and star count
2. **Repository Details:** Shows detailed information about a specific repository

## Libraries

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Navigation:** Compose Navigation
- **Networking:** Retrofit
- **Dependency Injection:** Dagger Hilt
- **Architecture:** MVVM
- **Testing:** MockK for unit testing, Jetpack Compose for screenshot tests
- **Build System:** Gradle with Kotlin DSL

## Setup

### Prerequisites
- Android Studio Ladybug or newer

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/lmorda/android-homework-problem.git
   ```
2. Open the project in Android Studio
3. Sync Gradle
4. Run the app on an emulator or a physical device

## Asset Acknowledgement

Thanks for the app icon!

https://www.flaticon.com/authors/agung-rama

## License

This project is licensed under the Apache License. See the [LICENSE](LICENSE) file for details.
