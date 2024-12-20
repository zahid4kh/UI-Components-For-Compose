package my.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import my.ui.R
import kotlin.math.cos
import kotlin.math.sin

enum class AchievementLevel {
    BRONZE, SILVER, GOLD
}

// Circular Achievement Badge with Pulse and Shine
@Composable
fun CircularAchievementBadge(
    level: AchievementLevel,
    isUnlocked: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = when (level) {
        AchievementLevel.BRONZE -> listOf(Color(0xFFCD7F32), Color(0xFFDDA15E))
        AchievementLevel.SILVER -> listOf(Color(0xFFC0C0C0), Color(0xFFE8E8E8))
        AchievementLevel.GOLD -> listOf(Color(0xFFFFD700), Color(0xFFFFF263))
    }

    // Animation states
    var isAnimating by remember { mutableStateOf(false) }
    val pulseAnim = remember { Animatable(1f) }
    val rotationAnim = remember { Animatable(0f) }
    val shineAnim = remember { Animatable(0f) }

    // Trigger animations when unlocked
    LaunchedEffect(isUnlocked) {
        if (isUnlocked && !isAnimating) {
            isAnimating = true
            // Pulse animation
            launch {
                repeat(2) {
                    pulseAnim.animateTo(1.2f, spring(Spring.DampingRatioMediumBouncy))
                    pulseAnim.animateTo(1f, spring(Spring.DampingRatioMediumBouncy))
                }
            }
            // Rotation animation
            launch {
                rotationAnim.animateTo(
                    360f,
                    animationSpec = tween(1000, easing = LinearEasing)
                )
            }
            // Shine animation
            launch {
                shineAnim.animateTo(1f, tween(1000))
                shineAnim.animateTo(0f, tween(500))
            }
            isAnimating = false
        }
    }

    // Continuous subtle pulse for unlocked state
    val continuousPulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "continuousPulse"
    )

    Box(
        modifier = modifier
            .size(56.dp)
            .scale(if (isUnlocked) continuousPulse else 1f)
            .scale(pulseAnim.value)
            .drawBehind {
                // Draw glow effect
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors[0].copy(alpha = 0.2f),
                            colors[0].copy(alpha = 0f)
                        )
                    ),
                    radius = size.width * 0.6f,
                    center = center
                )

                // Draw shine effect when unlocked
                if (isUnlocked) {
                    drawShineEffect(shineAnim.value, colors[0])
                }
            }
            .clip(CircleShape)
            .background(
                Brush.linearGradient(colors)
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(colors),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Inner icon area
        Box(
            modifier = Modifier
                .size(40.dp)
                .rotate(rotationAnim.value),
            contentAlignment = Alignment.Center
        ) {
            when (level) {
                AchievementLevel.BRONZE -> Icon(
                    painter = painterResource(R.drawable.emoji_events),
                    contentDescription = "Bronze Achievement",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                AchievementLevel.SILVER -> Icon(
                    Icons.Default.Star,
                    contentDescription = "Silver Achievement",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                AchievementLevel.GOLD -> Icon(
                    painter = painterResource(R.drawable.whatshot),
                    contentDescription = "Gold Achievement",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Optional ribbon at bottom
        if (isUnlocked) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 24.dp)
                    .size(width = 24.dp, height = 12.dp)
                    .clip(CircleShape)
                    .background(colors[0])
            )
        }
    }
}

// Hexagonal Achievement Badge with 3D Effect
@Composable
fun HexagonalAchievementBadge(
    level: AchievementLevel,
    isUnlocked: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = when (level) {
        AchievementLevel.BRONZE -> listOf(
            Color(0xFFCD7F32),
            Color(0xFFDDA15E),
            Color(0xFFE6BE8A)
        )
        AchievementLevel.SILVER -> listOf(
            Color(0xFFC0C0C0),
            Color(0xFFE8E8E8),
            Color(0xFFF5F5F5)
        )
        AchievementLevel.GOLD -> listOf(
            Color(0xFFFFD700),
            Color(0xFFFFF263),
            Color(0xFFFFF9C4)
        )
    }

    var isAnimating by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isUnlocked) 360f else 0f,
        animationSpec = tween(1500),
        label = "rotation"
    )

    val scaleAnim = remember { Animatable(1f) }

    LaunchedEffect(isUnlocked) {
        if (isUnlocked && !isAnimating) {
            isAnimating = true
            // We now only need to handle scale animation here
            launch {
                scaleAnim.animateTo(1.2f, tween(750))
                scaleAnim.animateTo(1f, tween(750))
            }
            isAnimating = false
        }
    }
    // Continuous floating animation
    val floatingOffset by rememberInfiniteTransition(label = "float").animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatingAnimation"
    )

    Box(
        modifier = modifier
            .size(56.dp)
            .offset(y = if (isUnlocked) floatingOffset.dp else 0.dp)
            .scale(scaleAnim.value)
            .graphicsLayer {
                // Use the same rotation value for both X and Y
                // This creates a diagonal rotation effect
                rotationX = rotation
                rotationY = rotation
            }
            .drawBehind {
                // Draw hexagon shape with gradient
                drawHexagon(colors)

                // Draw glow effect
                if (isUnlocked) {
                    drawHexagonGlow(colors[0])
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Achievement icon
        Icon(
            when (level) {
                AchievementLevel.BRONZE -> Icons.Default.Notifications
                AchievementLevel.SILVER -> Icons.Default.Star
                AchievementLevel.GOLD -> Icons.Default.Favorite
            },
            contentDescription = "${level.name} Achievement",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

// Crystal Achievement Badge with Sparkle Effects
@Composable
fun CrystalAchievementBadge(
    level: AchievementLevel,
    isUnlocked: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = when (level) {
        AchievementLevel.BRONZE -> listOf(
            Color(0xFFCD7F32).copy(alpha = 0.8f),
            Color(0xFFDDA15E).copy(alpha = 0.9f)
        )
        AchievementLevel.SILVER -> listOf(
            Color(0xFFC0C0C0).copy(alpha = 0.8f),
            Color(0xFFE8E8E8).copy(alpha = 0.9f)
        )
        AchievementLevel.GOLD -> listOf(
            Color(0xFFFFD700).copy(alpha = 0.8f),
            Color(0xFFFFF263).copy(alpha = 0.9f)
        )
    }

    var isAnimating by remember { mutableStateOf(false) }
    val sparkleAnim = remember { Animatable(0f) }
    val rotationAnim = remember { Animatable(0f) }

    LaunchedEffect(isUnlocked) {
        if (isUnlocked && !isAnimating) {
            isAnimating = true
            launch {
                sparkleAnim.animateTo(1f, tween(1000))
                sparkleAnim.animateTo(0f, tween(500))
            }
            launch {
                rotationAnim.animateTo(360f, tween(1500))
            }
            isAnimating = false
        }
    }

    // Crystal shimmer effect
    val shimmerOffset by rememberInfiniteTransition(label = "shimmer").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerAnimation"
    )

    Box(
        modifier = modifier
            .size(56.dp)
            .drawBehind {
                // Draw crystal shape
                drawCrystal(colors, shimmerOffset)

                // Draw sparkle effects when unlocked
                if (isUnlocked) {
                    drawSparkles(sparkleAnim.value, colors[0])
                }
            }
            .rotate(rotationAnim.value),
        contentAlignment = Alignment.Center
    ) {
        // Achievement icon with glass effect
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(colors[0].copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                when (level) {
                    AchievementLevel.BRONZE -> Icons.Default.Notifications
                    AchievementLevel.SILVER -> Icons.Default.Star
                    AchievementLevel.GOLD -> Icons.Default.Favorite
                },
                contentDescription = "${level.name} Achievement",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        // Crystal fragments animation when unlocked
        if (isUnlocked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawCrystalFragments(sparkleAnim.value, colors[0])
                    }
            )
        }
    }
}

// Helper functions for drawing effects
private fun DrawScope.drawShineEffect(progress: Float, color: Color) {
    // Implementation of shine effect
    val radius = size.width * 0.8f
    val angle = progress * 360f
    val x = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * radius
    val y = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * radius

    drawCircle(
        color = color.copy(alpha = 0.6f * (1f - progress)),
        radius = 20f,
        center = Offset(x, y)
    )
}

private fun DrawScope.drawHexagon(colors: List<Color>) {
    // Implementation of hexagon drawing with gradient
    val path = Path().apply {
        val radius = size.width / 2
        val centerX = size.width / 2
        val centerY = size.height / 2

        moveTo(
            centerX + radius * cos(0.0).toFloat(),
            centerY + radius * sin(0.0).toFloat()
        )

        for (i in 1..6) {
            val angle = (i * 60.0) * Math.PI / 180.0
            lineTo(
                centerX + radius * cos(angle).toFloat(),
                centerY + radius * sin(angle).toFloat()
            )
        }
        close()
    }

    drawPath(
        path = path,
        brush = Brush.linearGradient(colors)
    )
}

private fun DrawScope.drawHexagonGlow(color: Color) {
    // Implementation of hexagon glow effect
    val path = Path().apply {
        val radius = size.width / 2 + 8.dp.toPx()
        val centerX = size.width / 2
        val centerY = size.height / 2

        moveTo(
            centerX + radius * cos(0.0).toFloat(),
            centerY + radius * sin(0.0).toFloat()
        )

        for (i in 1..6) {
            val angle = (i * 60.0) * Math.PI / 180.0
            lineTo(
                centerX + radius * cos(angle).toFloat(),
                centerY + radius * sin(angle).toFloat()
            )
        }
        close()
    }

    drawPath(
        path = path,
        color = color.copy(alpha = 0.2f),
        style = Stroke(
            width = 8.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

private fun DrawScope.drawCrystal(colors: List<Color>, shimmerProgress: Float) {
    // Create a crystal-like shape with multiple facets
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = size.width / 2

    // Draw main crystal shape
    val path = Path().apply {
        moveTo(centerX, centerY - radius)  // Top point
        lineTo(centerX + radius * 0.8f, centerY)  // Right point
        lineTo(centerX, centerY + radius)  // Bottom point
        lineTo(centerX - radius * 0.8f, centerY)  // Left point
        close()
    }

    // Create a shimmering gradient effect
    val gradient = Brush.linearGradient(
        colors = colors,
        start = Offset(
            x = size.width * shimmerProgress,
            y = 0f
        ),
        end = Offset(
            x = size.width * (shimmerProgress + 0.5f),
            y = size.height
        )
    )

    drawPath(
        path = path,
        brush = gradient
    )
}

private fun DrawScope.drawSparkles(progress: Float, color: Color) {
    // Draw multiple sparkles around the crystal
    val sparkleCount = 8
    val radius = size.width * 0.6f

    repeat(sparkleCount) { index ->
        val angle = (index * 360f / sparkleCount) + (progress * 360f)
        val x = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * radius
        val y = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * radius

        // Draw a four-point star
        drawCircle(
            color = color.copy(alpha = (1f - progress) * 0.6f),
            radius = 4.dp.toPx(),
            center = Offset(x, y)
        )
    }
}

private fun DrawScope.drawCrystalFragments(progress: Float, color: Color) {
    // Create flying crystal fragments animation
    val fragmentCount = 12
    val maxRadius = size.width * progress * 1.5f

    repeat(fragmentCount) { index ->
        val angle = (index * 360f / fragmentCount)
        val currentRadius = maxRadius * (0.5f + (index % 3) * 0.2f)
        val x = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * currentRadius
        val y = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * currentRadius

        // Draw fragment
        drawCircle(
            color = color.copy(alpha = (1f - progress) * 0.4f),
            radius = 2.dp.toPx(),
            center = Offset(x, y)
        )
    }
}

// Demo component to showcase all badge variations
@Composable
fun AchievementBadgeDemo() {
    var unlockedBadges by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Circular Badges Section
        Text(
            "Circular Achievements",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AchievementBadgeWithLabel(
                label = "Bronze",
                isUnlocked = unlockedBadges.contains("circular_bronze")
            ) {
                CircularAchievementBadge(
                    level = AchievementLevel.BRONZE,
                    isUnlocked = unlockedBadges.contains("circular_bronze"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("circular_bronze")) {
                            unlockedBadges - "circular_bronze"
                        } else {
                            unlockedBadges + "circular_bronze"
                        }
                    }
                )
            }

            AchievementBadgeWithLabel(
                label = "Silver",
                isUnlocked = unlockedBadges.contains("circular_silver")
            ) {
                CircularAchievementBadge(
                    level = AchievementLevel.SILVER,
                    isUnlocked = unlockedBadges.contains("circular_silver"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("circular_silver")) {
                            unlockedBadges - "circular_silver"
                        } else {
                            unlockedBadges + "circular_silver"
                        }
                    }
                )
            }

            AchievementBadgeWithLabel(
                label = "Gold",
                isUnlocked = unlockedBadges.contains("circular_gold")
            ) {
                CircularAchievementBadge(
                    level = AchievementLevel.GOLD,
                    isUnlocked = unlockedBadges.contains("circular_gold"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("circular_gold")) {
                            unlockedBadges - "circular_gold"
                        } else {
                            unlockedBadges + "circular_gold"
                        }
                    }
                )
            }
        }

        // Hexagonal Badges Section
        Text(
            "Hexagonal Achievements",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AchievementBadgeWithLabel(
                label = "Bronze",
                isUnlocked = unlockedBadges.contains("hex_bronze")
            ) {
                HexagonalAchievementBadge(
                    level = AchievementLevel.BRONZE,
                    isUnlocked = unlockedBadges.contains("hex_bronze"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("hex_bronze")) {
                            unlockedBadges - "hex_bronze"
                        } else {
                            unlockedBadges + "hex_bronze"
                        }
                    }
                )
            }

            AchievementBadgeWithLabel(
                label = "Silver",
                isUnlocked = unlockedBadges.contains("hex_silver")
            ) {
                HexagonalAchievementBadge(
                    level = AchievementLevel.SILVER,
                    isUnlocked = unlockedBadges.contains("hex_silver"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("hex_silver")) {
                            unlockedBadges - "hex_silver"
                        } else {
                            unlockedBadges + "hex_silver"
                        }
                    }
                )
            }

            AchievementBadgeWithLabel(
                label = "Gold",
                isUnlocked = unlockedBadges.contains("hex_gold")
            ) {
                HexagonalAchievementBadge(
                    level = AchievementLevel.GOLD,
                    isUnlocked = unlockedBadges.contains("hex_gold"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("hex_gold")) {
                            unlockedBadges - "hex_gold"
                        } else {
                            unlockedBadges + "hex_gold"
                        }
                    }
                )
            }
        }

        // Crystal Badges Section
        Text(
            "Crystal Achievements",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AchievementBadgeWithLabel(
                label = "Bronze",
                isUnlocked = unlockedBadges.contains("crystal_bronze")
            ) {
                CrystalAchievementBadge(
                    level = AchievementLevel.BRONZE,
                    isUnlocked = unlockedBadges.contains("crystal_bronze"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("crystal_bronze")) {
                            unlockedBadges - "crystal_bronze"
                        } else {
                            unlockedBadges + "crystal_bronze"
                        }
                    }
                )
            }

            AchievementBadgeWithLabel(
                label = "Silver",
                isUnlocked = unlockedBadges.contains("crystal_silver")
            ) {
                CrystalAchievementBadge(
                    level = AchievementLevel.SILVER,
                    isUnlocked = unlockedBadges.contains("crystal_silver"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("crystal_silver")) {
                            unlockedBadges - "crystal_silver"
                        } else {
                            unlockedBadges + "crystal_silver"
                        }
                    }
                )
            }

            AchievementBadgeWithLabel(
                label = "Gold",
                isUnlocked = unlockedBadges.contains("crystal_gold")
            ) {
                CrystalAchievementBadge(
                    level = AchievementLevel.GOLD,
                    isUnlocked = unlockedBadges.contains("crystal_gold"),
                    modifier = Modifier.clickable {
                        unlockedBadges = if (unlockedBadges.contains("crystal_gold")) {
                            unlockedBadges - "crystal_gold"
                        } else {
                            unlockedBadges + "crystal_gold"
                        }
                    }
                )
            }
        }
    }
}

// Helper component for badge labels
@Composable
private fun AchievementBadgeWithLabel(
    label: String,
    isUnlocked: Boolean,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isUnlocked) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            }
        )
    }
}