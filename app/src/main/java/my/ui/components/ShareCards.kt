package my.ui.components

import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Data class for share options
data class ShareOption(
    val icon: ImageVector,
    val label: String,
    val color: Color
)

// Material 3 - Elegant Card with Scale Animation
@Composable
fun ElegantShareCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0.95f,
        label = "scaleAnimation"
    )

    val color1 = MaterialTheme.colorScheme.primary
    val color2 = MaterialTheme.colorScheme.secondary
    val color3 = MaterialTheme.colorScheme.tertiary

    val shareOptions = remember {
        listOf(
            ShareOption(Icons.Default.Email, "Email", color1),
            ShareOption(Icons.Default.Share, "Message", color2),
            ShareOption(Icons.AutoMirrored.Filled.Send, "Forward", color3)
        )
    }

    Card(
        modifier = Modifier
            .scale(scale)
            .width(280.dp)
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Share this content",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    shareOptions.forEach { option ->
                        ShareButton(
                            icon = option.icon,
                            label = option.label,
                            color = option.color
                        )
                    }
                }
            }
        }
    }
}

// Material 3 - Compact Card with Fade Animation
@Composable
fun CompactShareCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0.6f,
        label = "fadeAnimation"
    )

    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { isExpanded = !isExpanded }
            .alpha(alpha),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Quick Share",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Circular share buttons
                    ShareIconButton(
                        icon = Icons.Default.Email,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ShareIconButton(
                        icon = Icons.Default.Share,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ShareIconButton(
                        icon = Icons.Default.Send,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

// Custom Gradient Card with Pulse Animation
@Composable
fun GradientShareCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val pulseScale by animateFloatAsState(
        targetValue = if (isExpanded) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAnimation"
    )

    Box(
        modifier = Modifier
            .scale(pulseScale)
            .width(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6190E8),
                        Color(0xFFA7BFE8)
                    )
                )
            )
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Share your story",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GradientShareButton(Icons.Default.Email, "Email")
                    GradientShareButton(Icons.Default.Share, "Share")
                    GradientShareButton(Icons.Default.Send, "Send")
                }
            }
        }
    }
}

// Modern Glassmorphic Card with Slide Animation
@Composable
fun GlassmorphicShareCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val isDarkTheme = isSystemInDarkTheme()

    val offsetX by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else (-50).dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "slideAnimation"
    )

    // Improved colors for better visibility in light theme
    val backgroundColor = if (isDarkTheme) {
        Color(0xFF1A1A1A).copy(alpha = 0.7f)
    } else {
        // Darker background for light theme to improve visibility
        Color(0xFF4A4A4A).copy(alpha = 0.15f)
    }

    val borderColor = if (isDarkTheme) {
        Color(0xFF2A2A2A).copy(alpha = 0.8f)
    } else {
        // More visible border in light theme
        Color(0xFF808080).copy(alpha = 0.3f)
    }

    val textColor = if (isDarkTheme) {
        Color.White.copy(alpha = 0.87f)
    } else {
        // Darker text for better readability in light theme
        Color(0xFF2A2A2A)
    }

    val iconTint = if (isDarkTheme) {
        Color(0xFF64B5F6)  // Light blue for dark theme
    } else {
        // Darker icon color for light theme
        Color(0xFF1976D2)  // Deeper blue for better visibility
    }

    val gradientColors = if (isDarkTheme) {
        listOf(
            Color(0xFF1A1A1A).copy(alpha = 0.8f),
            Color(0xFF2A2A2A).copy(alpha = 0.6f),
            Color(0xFF1A1A1A).copy(alpha = 0.8f)
        )
    } else {
        listOf(
            // More visible gradient in light theme
            Color(0xFF4A4A4A).copy(alpha = 0.2f),
            Color(0xFF6A6A6A).copy(alpha = 0.1f),
            Color(0xFF4A4A4A).copy(alpha = 0.2f)
        )
    }

    // Enhanced shadow for better depth perception in light theme
    val shadowColor = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.2f)
    } else {
        Color.Black.copy(alpha = 0.1f)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslation"
    )

    Box(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(20.dp))
            // Add subtle shadow for depth
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp)
    ) {
        val arrowRotate by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "",
            animationSpec = tween(300))
        Column(modifier = Modifier.animateContentSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Share content",
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold  // Added weight for better visibility
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = iconTint,
                    modifier = Modifier.size(24.dp).rotate(arrowRotate)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + slideInHorizontally(animationSpec = tween(350)),
                exit = fadeOut() + slideOutHorizontally(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .offset(x = offsetX)
                        .padding(top = 16.dp)
                ) {
                    GlassShareOption(
                        icon = Icons.Default.Email,
                        label = "Email",
                        iconTint = iconTint,
                        textColor = textColor,
                        shimmerOffset = translateAnim,
                        gradientColors = gradientColors,
                        isDarkTheme = isDarkTheme  // Pass theme information
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    GlassShareOption(
                        icon = Icons.Default.Share,
                        label = "Share",
                        iconTint = iconTint,
                        textColor = textColor,
                        shimmerOffset = translateAnim,
                        gradientColors = gradientColors,
                        isDarkTheme = isDarkTheme
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    GlassShareOption(
                        icon = Icons.Default.Send,
                        label = "Send",
                        iconTint = iconTint,
                        textColor = textColor,
                        shimmerOffset = translateAnim,
                        gradientColors = gradientColors,
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
}

@Composable
private fun GlassShareOption(
    icon: ImageVector,
    label: String,
    iconTint: Color,
    textColor: Color,
    shimmerOffset: Float,
    gradientColors: List<Color>,
    isDarkTheme: Boolean
) {
    var isHovered by remember { mutableStateOf(false) }

    // Enhanced background for better visibility in light theme
    val optionBackground = if (isDarkTheme) {
        Brush.horizontalGradient(
            colors = gradientColors,
            startX = shimmerOffset - 300,
            endX = shimmerOffset
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF5A5A5A).copy(alpha = 0.1f),
                Color(0xFF7A7A7A).copy(alpha = 0.05f),
                Color(0xFF5A5A5A).copy(alpha = 0.1f)
            ),
            startX = shimmerOffset - 300,
            endX = shimmerOffset
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(optionBackground)
            .clickable { isHovered = !isHovered }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier
                .size(24.dp)
                .scale(if (isHovered) 1.1f else 1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium  // Added weight for better visibility
        )
    }
}

// Neon-styled Card with Glow Animation
@Composable
fun NeonShareCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val glowIntensity by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAnimation"
    )

    Box(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .border(
                width = 2.dp,
                color = Color(0xFF00FF87).copy(alpha = glowIntensity),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Share",
                color = Color(0xFF00FF87),
                style = MaterialTheme.typography.titleMedium
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NeonShareButton(Icons.Default.Email, "Email")
                    NeonShareButton(Icons.Default.Share, "Share")
                    NeonShareButton(Icons.Default.Send, "Send")
                }
            }
        }
    }
}

// Helper composables for share buttons
@Composable
private fun ShareButton(
    icon: ImageVector,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { /* Handle share action */ },
            modifier = Modifier
                .clip(CircleShape)
                .background(color)
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color.White
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun ShareIconButton(
    icon: ImageVector,
    color: Color
) {
    IconButton(
        onClick = { /* Handle share action */ },
        modifier = Modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun GradientShareButton(
    icon: ImageVector,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { /* Handle share action */ },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color.White
            )
        }
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Composable
private fun NeonShareButton(
    icon: ImageVector,
    label: String
) {
    // Adding a pulsing animation state for the neon effect
    var isPulsing by remember { mutableStateOf(false) }
    val pulseScale by animateFloatAsState(
        targetValue = if (isPulsing) 1.1f else 1f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "neonPulse"
    )

    // Glow animation for the border
    val glowAlpha by animateFloatAsState(
        targetValue = if (isPulsing) 0.8f else 0.4f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "neonGlow"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(pulseScale)
            .clickable { isPulsing = !isPulsing }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A))
                .border(
                    width = 2.dp,
                    color = Color(0xFF00FF87).copy(alpha = glowAlpha),
                    shape = CircleShape
                )
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color(0xFF00FF87),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = Color(0xFF00FF87),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

// A comprehensive demo component to showcase all share card variations
@Composable
fun ShareCardDemo() {
    var selectedCard by remember { mutableStateOf<Int?>(null) }

    // Animated container scale for the selected card
    fun getScale(index: Int): Float {
        return if (selectedCard == index) 1.05f else 1f
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Material 3 Section
        Text(
            "Material Design Share Cards",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Elegant Share Card with scale animation
        Box(
            modifier = Modifier
                .scale(getScale(0))
                .clickable { selectedCard = if (selectedCard == 0) null else 0 }
        ) {
            ElegantShareCard()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Compact Share Card with fade animation
        Box(
            modifier = Modifier
                .scale(getScale(2))
                .clickable { selectedCard = if (selectedCard == 2) null else 2 }
        ) {
            CompactShareCard()
        }

        // Custom Styled Section
        Text(
            "Custom Styled Share Cards",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )

        // Gradient Share Card with pulse animation
        Box(
            modifier = Modifier
                .scale(getScale(3))
                .clickable { selectedCard = if (selectedCard == 3) null else 3 }
        ) {
            GradientShareCard()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Glassmorphic Share Card with slide animation
        Box(
            modifier = Modifier
                .scale(getScale(4))
                .clickable { selectedCard = if (selectedCard == 4) null else 4 }
        ) {
            GlassmorphicShareCard()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Neon Share Card with glow animation
        Box(
            modifier = Modifier
                .scale(getScale(5))
                .clickable { selectedCard = if (selectedCard == 5) null else 5 }
        ) {
            NeonShareCard()
        }
    }
}

// Extension composable to handle share actions
@Composable
fun ShareCardHandler(
    onShareClick: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}