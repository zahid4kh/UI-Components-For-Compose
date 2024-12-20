package my.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.ui.R

// Modern gradient badge with pulsing animation
@Composable
fun GradientLiveStreamBadge(
    viewerCount: Int = 0,
) {
    var isLive by remember { mutableStateOf(false) }

    // Pulse animation for the entire badge
    val scale by animateFloatAsState(
        targetValue = if (isLive) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAnimation"
    )

    // Text opacity animation
    val textAlpha by animateFloatAsState(
        targetValue = if (isLive) 1f else 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "textOpacityAnimation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .scale(if (isLive) scale else 1f)
                .width(120.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF0F7B),
                            Color(0xFFF89B29)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LIVE",
                    color = Color.White.copy(alpha = if (isLive) textAlpha else 1f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.visibility),
                        contentDescription = "Viewers",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = viewerCount.toString(),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Switch(
            checked = isLive,
            onCheckedChange = { isLive = it }
        )
    }
}

// Neon-styled badge with glowing effect
@Composable
fun NeonLiveStreamBadge(
    viewerCount: Int = 0,
) {
    var isLive by remember { mutableStateOf(false) }

    val glowIntensity by animateFloatAsState(
        targetValue = if (isLive) 1f else 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAnimation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1A1A1A))
                .padding(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF00FF87).copy(alpha = glowIntensity),
                                Color(0xFF00FF87).copy(alpha = 0f)
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LIVE",
                        color = Color(0xFF00FF87),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.visibility),
                            contentDescription = "Viewers",
                            tint = Color(0xFF00FF87),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = viewerCount.toString(),
                            color = Color(0xFF00FF87),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Switch(
            checked = isLive,
            onCheckedChange = { isLive = it }
        )
    }
}

// Minimalist badge with dot indicator
@Composable
fun MinimalistLiveStreamBadge(
    viewerCount: Int = 0,
) {
    var isLive by remember { mutableStateOf(false) }

    val dotScale by animateFloatAsState(
        targetValue = if (isLive) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotAnimation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFFF5F5F5))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .scale(dotScale)
                            .clip(CircleShape)
                            .background(if (isLive) Color(0xFFFF4444) else Color(0xFFCCCCCC))
                    )
                    Text(
                        text = "LIVE",
                        color = Color(0xFF333333),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = viewerCount.toString(),
                    color = Color(0xFF666666),
                    fontSize = 12.sp
                )
            }
        }

        Switch(
            checked = isLive,
            onCheckedChange = { isLive = it }
        )
    }
}

// Gaming-inspired badge with RGB effect
@Composable
fun GamingLiveStreamBadge(
    viewerCount: Int = 0,
) {
    var isLive by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "rgbTransition")
    val hue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hueAnimation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E1E1E))
                .padding(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isLive) {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.hsv(hue * 360, 1f, 1f),
                                    Color.hsv((hue * 360 + 120) % 360, 1f, 1f),
                                    Color.hsv((hue * 360 + 240) % 360, 1f, 1f)
                                )
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF2A2A2A), Color(0xFF2A2A2A))
                            )
                        }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.visibility),
                            contentDescription = "Viewers",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = viewerCount.toString(),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Switch(
            checked = isLive,
            onCheckedChange = { isLive = it }
        )
    }
}

// Professional broadcast-style badge
@Composable
fun BroadcastLiveStreamBadge(
    viewerCount: Int = 0,
) {
    var isLive by remember { mutableStateOf(false) }

    val dotOpacity by animateFloatAsState(
        targetValue = if (isLive) 1f else 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotOpacityAnimation"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2B2B2B),
                            Color(0xFF1A1A1A)
                        )
                    )
                )
                .padding(1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFF0000).copy(alpha = dotOpacity))
                    )
                    Text(
                        text = "LIVE",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.visibility),
                        contentDescription = "Viewers",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = viewerCount.toString(),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Switch(
            checked = isLive,
            onCheckedChange = { isLive = it }
        )
    }
}