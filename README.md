# AudiobookPlayer

The project was developed as a test task focused on implementing audio playback for book summary
chapters. The main objective was to replicate the provided screen UI and implement an audio player
with support for background playback.

![](/docs/assets/cover_main_screen.png)
&nbsp;&nbsp;
![](/docs/assets/cover_notification.png)

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on an emulator or device

## Assumptions

- The main focus is on seamless audio playback and UI experience.
- Minimal clean architecture entities should be set up, but no complex domain logic or persistence
  is required.
- It's sufficient to use local or static audio files and book data.

## Project Description

### UI & Audio Playback

UI is implemented in Compose.

Audio playback starts as soon as the app opens and data loads. It's available with the app in
foreground, or with a system media notification if the app is closed.

The app allows users to play, pause, seek forward and backward, seek using a slider, navigate to the
next and previous chapters, and change playback speed. All player interactions are handled directly
in the UI using the `MediaController`. All player state changes are reactive and implemented with
custom Compose state wrappers around `Player`.

### Audio Implementation

Audio playback is managed using an `AudioSessionService` and `ExoPlayer`, ensuring smooth background
playback.

### Architecture

The project demonstrates a minimal Clean Architecture structure, with clear separation of concerns.
The UI layer is built with Jetpack Compose and follows the MVI (Model-View-Intent) pattern.

### Data

No database or external APIs are used. All book and chapters data are static and bundled with the
app. Media files are resolved from public URLs.

### Dependency Injection

Koin and Koin Annotations are used for dependency injection.

### Code style

The project uses `ktlint` for Kotlin code style. Run `ktlintFormat --rerun-tasks` to format code.

Additionally, a linter for Compose is set up to display warnings in IDE code editor.

## Tech Stack

- **Kotlin**
- **Coroutines**
- **Jetpack Compose**
- **Media3**
- **ExoPlayer**
- **Koin** (with Koin Annotations)
- **ktlint** (plus Compose linter)

## Project Structure

- `app/` - Main application entities (like Application class)
- `data/` - Repository with static book and audio data
- `domain/` - Minimal domain entities and use cases
- `presentation/` - UI screens, player logic, MVI state management, Compose theme definitions, utils

## Linting

- Run `ktlint` to check code style.
- Compose linter is also set up for UI best practices.
