# android-interview-problem

This project serves as a starter template for a typical list/details Android interview homework problem.  The project utilizes the latest Android libraries including Jetpack Compose,
Dagger Hilt, Retrofit, Coil, Timber, MockK, Espresso, and Kotlin DSL gradle build scripts. 

## Architecture and Design Decisions

**Single Module:** This app uses a single module due to its small project size. For larger and more complex projects, a modular architecture—such as separating domain, data, and feature modules—would be a cleaner and more scalable approach.

**Overengineering:** While separating the data and UI layers with a domain layer may seem excessive for this project, it serves as an intentional demonstration of clean architecture principles.

**Paging:** I implemented a custom paging solution instead of using the Paging 3 library because the project deals with a small amount of data for each item. My own testing showed that even with a theoretical load of one million pages, the app did not encounter out of memory issues, making a third-party library unnecessary for this use case.

**Future Improvements:** Additional unit tests and screenshot tests could enhance the project. For instance, unit tests for the mapper are currently missing and should be added to ensure better test coverage.

## Project Overview

The app fetches a list of GitHub repositories from the [GitHub API](https://docs.github.com/en/rest) and displays them in a list. When a repository is tapped, the app navigates to a detailed view showing 
additional information about the selected repository.  Paging is supported by swiping up on the list page.

## Libraries

- **Language:** Kotlin
- **UI:** Jetpack Compose, Coil
- **Navigation:** Compose Navigation
- **Networking:** Retrofit
- **Dependency Injection:** Dagger Hilt
- **Architecture:** MVVM
- **Testing:** MockK, Espresso
- **Build Scripts:** Kotlin DSL

## Setup

### Prerequisites
- Android Studio Ladybug or newer

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/lmorda/android-interview-problem.git
   ```
2. Open the project in Android Studio
3. Sync Gradle
4. Run the app on an emulator or a physical device

## Asset Acknowledgement

Thanks for the app icon!

https://www.flaticon.com/authors/agung-rama

## License

This project is licensed under the Apache License. See the [LICENSE](LICENSE) file for details.
