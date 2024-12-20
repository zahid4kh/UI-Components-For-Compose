# Modern UI Components for Jetpack Compose 🚀

A collection of carefully crafted, highly customizable 20 + UI components for your Jetpack Compose projects. Each component is built with attention to detail, smooth animations, and user interaction in mind.

## What's Inside 📦

### 1. Bouncing Bear Button
- Engaging bounce animation on press
- Customizable colors and text
- Smooth spring animations
- Perfect for call-to-action buttons

### 2. Prize Button
- Playful prize box design
- Continuous shake animation
- Interactive press feedback
- Great for rewards and achievements

### 3. Story Bubble
- Instagram-style story bubbles
- Animated gradient ring
- Online/offline states
- Username display support

### 4. Reaction Slider
- Innovative emoji selector
- Smooth scrolling animation
- Center-snap functionality
- Over 200 built-in emojis
- Real-time emoji insertion at cursor position

### 5. Live Stream Badge
Five unique variations:
- Gradient Live Stream Badge
- Neon Live Stream Badge
- Gaming Live Stream Badge
- Minimalist Live Stream Badge
- Broadcast Live Stream Badge

Features:
- Viewer count display
- Online/offline state animations
- Theme-aware colors
- Customizable animations

### 6. Share Cards
Five different styles:
- Material 3 Elegant Card
- Material 3 Compact Card
- Custom Gradient Card
- Glassmorphic Card
- Neon Card

Features:
- Expandable share options
- Smooth animations
- Platform-specific sharing
- Theme support

### 7. Profile Achievement Badges
Three distinct variations:
- Circular Achievement Badge
- Hexagonal Achievement Badge
- Crystal Achievement Badge

Features:
- Bronze, Silver, and Gold tiers
- Unlock animations
- Glow effects
- Interactive feedback

## Tech Stack 💻

- Jetpack Compose
- Material 3
- Kotlin
- Custom Canvas drawings
- Advanced animations using:
    - animateFloatAsState
    - Animatable
    - rememberInfiniteTransition
    - AnimatedVisibility
    - Custom animation specs


## Installation 📥

1. Download the component package
2. Extract the files to your project

## Customization Options 🎨

Each component comes with extensive customization options:

### Colors and Themes
- Material 3 dynamic color support
- Custom color schemes
- Dark/light theme variations
- Gradient customization

### Animations
- Duration adjustment
- Animation curve modification
- Custom transition effects
- Interactive feedback tuning

### Sizing and Layout
- Responsive scaling
- Dynamic sizing
- Custom padding and margins
- Layout adaptability

### Interaction
- Custom click handlers
- State management
- Event callbacks
- Gesture support

## Usage Examples 💡

Here's a quick example of how to use a component:

```kotlin
@Composable
fun MyScreen() {
    Column {
        // Bouncing Bear Button
        BearButton(
            text = "Click Me!",
            onClick = { /* your action */ }
        )

        // Live Stream Badge
        GradientLiveStreamBadge(
            viewerCount = 1234
        )

        // Achievement Badge
        CircularAchievementBadge(
            level = AchievementLevel.GOLD,
            isUnlocked = true
        )
    }
}
```

## Coming Soon 🌟

- More animation variations
- Additional component themes
- New interactive elements
- Enhanced customization options

---

Made with ❤️ for the Jetpack Compose community. Get started with modern, engaging UI components today!