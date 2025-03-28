package my.ui.components.progressbars

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Shared data class for segment information
data class StorySegment(
    val id: Int,
    val duration: Int = 5000 // Duration in milliseconds
)

// Classic gradient progress bar with smooth transitions
@Composable
fun GradientStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    // Theme-aware gradients
    val activeGradient = if (isDarkTheme) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF64B5F6),
                Color(0xFF2196F3),
                Color(0xFF1976D2)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF90CAF9),
                Color(0xFF42A5F5),
                Color(0xFF1E88E5)
            )
        )
    }

    val progress = remember(currentSegment) { Animatable(0f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(CircleShape)
                    .background(
                        if (isDarkTheme) Color(0x40FFFFFF) else Color(0x40000000)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(activeGradient)
                )
            }
        }
    }
}

// Neon-style progress bar with glowing effect
@Composable
fun NeonStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    val neonColor = if (isDarkTheme) {
        Color(0xFF00FF87)
    } else {
        Color(0xFF00C853)
    }

    val progress = remember(currentSegment) { Animatable(0f) }
    val glowIntensity = remember { Animatable(0.5f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        while(true) {
            glowIntensity.animateTo(1f, animationSpec = tween(500))
            glowIntensity.animateTo(0.5f, animationSpec = tween(500))
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (isDarkTheme) Color(0x40FFFFFF) else Color(0x40000000)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(neonColor.copy(alpha = glowIntensity.value))
                )
            }
        }
    }
}

// Liquid flowing progress with wave animation
@Composable
fun LiquidStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    val waveColor = if (isDarkTheme) {
        Color(0xFF448AFF)
    } else {
        Color(0xFF2979FF)
    }

    val progress = remember(currentSegment) { Animatable(0f) }
    val waveOffset = remember { Animatable(0f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        while(true) {
            waveOffset.animateTo(1f, animationSpec = tween(1000))
            waveOffset.snapTo(0f)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (isDarkTheme) Color(0x40FFFFFF) else Color(0x40000000)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    waveColor.copy(alpha = 0.7f),
                                    waveColor,
                                    waveColor.copy(alpha = 0.7f)
                                ),
                                startX = waveOffset.value * 100
                            )
                        )
                )
            }
        }
    }
}

// Minimalist clean progress bar
@Composable
fun MinimalistStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    val activeColor = if (isDarkTheme) {
        Color.White
    } else {
        Color.Black
    }

    val progress = remember(currentSegment) { Animatable(0f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .clip(CircleShape)
                    .background(
                        if (isDarkTheme) Color(0x40FFFFFF) else Color(0x40000000)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(activeColor.copy(alpha = 0.9f))
                )
            }
        }
    }
}

// Rainbow progress with color cycling
@Composable
fun RainbowStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val progress = remember(currentSegment) { Animatable(0f) }
    val rainbowOffset = remember { Animatable(0f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        while(true) {
            rainbowOffset.animateTo(1f, animationSpec = tween(2000))
            rainbowOffset.snapTo(0f)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0x40808080))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFF0000),
                                    Color(0xFFFFa500),
                                    Color(0xFFFFFF00),
                                    Color(0xFF00FF00),
                                    Color(0xFF0000FF),
                                    Color(0xFF4B0082),
                                    Color(0xFF8F00FF)
                                ),
                                startX = rainbowOffset.value * 1000
                            )
                        )
                )
            }
        }
    }
}

// Pulse progress with bouncing animation
@Composable
fun PulseStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    val pulseColor = if (isDarkTheme) {
        Color(0xFFE040FB)
    } else {
        Color(0xFFAA00FF)
    }

    val progress = remember(currentSegment) { Animatable(0f) }
    val pulseScale = remember { Animatable(1f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        while(true) {
            pulseScale.animateTo(1.2f, animationSpec = tween(300))
            pulseScale.animateTo(1f, animationSpec = tween(300))
            delay(400)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (isDarkTheme) Color(0x40FFFFFF) else Color(0x40000000)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(pulseColor)
                ) {
                    if (index == currentSegment) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(8.dp)
                                .offset(x = (progress.value * 100).dp)
                                .scale(pulseScale.value)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            pulseColor,
                                            pulseColor.copy(alpha = 0f)
                                        )
                                    )
                                )
                        )
                    }
                }
            }
        }
    }
}

// Metallic progress with shine effect
@Composable
fun MetallicStoryProgress(
    segments: List<StorySegment>,
    currentSegment: Int,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    val progress = remember(currentSegment) { Animatable(0f) }
    val shinePosition = remember { Animatable(0f) }

    LaunchedEffect(currentSegment) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = segments[currentSegment].duration,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(Unit) {
        while(true) {
            shinePosition.animateTo(1f, animationSpec = tween(1500))
            shinePosition.snapTo(-0.2f)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        segments.forEachIndexed { index, _ ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (isDarkTheme) {
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF424242),
                                    Color(0xFF616161),
                                    Color(0xFF424242)
                                )
                            )
                        } else {
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFE0E0E0),
                                    Color(0xFFBDBDBD),
                                    Color(0xFFE0E0E0)
                                )
                            )
                        }
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(
                            when {
                                index < currentSegment -> 1f
                                index == currentSegment -> progress.value
                                else -> 0f
                            }
                        )
                        .background(
                            Brush.horizontalGradient(
                                colors = if (isDarkTheme) {
                                    listOf(
                                        Color(0xFF78909C),
                                        Color(0xFFB0BEC5),
                                        Color(0xFF78909C)
                                    )
                                } else {
                                    listOf(
                                        Color(0xFFB0BEC5),
                                        Color(0xFFECEFF1),
                                        Color(0xFFB0BEC5)
                                    )
                                }
                            )
                        )
                ) {
                    // Add shine effect
                    if (index == currentSegment) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(20.dp)
                                .offset(
                                    x = (shinePosition.value * (progress.value * 100)).dp
                                )
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0f),
                                            Color.White.copy(alpha = 0.5f),
                                            Color.White.copy(alpha = 0f)
                                        ),
                                        startX = 0f,
                                        endX = 20f
                                    )
                                )
                        )
                    }
                }
            }
        }
    }
}

// Usage demonstration component that shows all progress bar variations
@Composable
fun StoryProgressDemo() {
    val segments = remember {
        List(5) { index -> StorySegment(id = index) }
    }

    var currentSegment by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // Auto-advance segments
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000L)
            scope.launch {
                currentSegment = (currentSegment + 1) % segments.size
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        GradientStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )

        NeonStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )

        LiquidStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )

        MinimalistStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )

        RainbowStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )

        PulseStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )

        MetallicStoryProgress(
            segments = segments,
            currentSegment = currentSegment
        )
    }
}