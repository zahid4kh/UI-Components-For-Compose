package my.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import my.ui.R

@Composable
fun StoryBubble(
    modifier: Modifier = Modifier,
    size: Int = 64 // Default size in dp
) {
    // State for the switch
    var isRotating by remember { mutableStateOf(false) }

    // Create infinite rotation animation
    val rotationAngle by rememberInfiniteTransition(label = "gradientRotation")
        .animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation"
        )
    val pulseScale by rememberInfiniteTransition(label = "pulseAnimation")
        .animateFloat(
            initialValue = 1f,
            targetValue = 1.1f,  // Pulse up to 110% of original size
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,  // Complete one pulse every second
                    easing = FastOutSlowInEasing  // Smooth easing for natural pulse feel
                ),
                repeatMode = RepeatMode.Reverse  // Smoothly reverse the animation
            ),
            label = "pulse"
        )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Container box with specific size
        Box(
            modifier = modifier.size(size.dp),
            contentAlignment = Alignment.Center
        ) {
            // Outer gradient ring with conditional rotation
            Icon(
                painter = painterResource(R.drawable.outer_gradient),
                contentDescription = "Story ring",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(size.dp)
                    .rotate(if (isRotating) rotationAngle else 0f)
                    .scale(if (isRotating) pulseScale else 1f)
            )

            // Inner circle with profile image
            Icon(
                painter = painterResource(R.drawable.inner_white_circle),
                contentDescription = "Profile image",
                tint = Color.Unspecified,
                modifier = Modifier.size((size - 8).dp)
            )

            // User icon
            Icon(
                Icons.Default.Person,
                contentDescription = "User",
                tint = Color.White,
                modifier = Modifier.size(size.dp - 20.dp)
            )
        }

        Text(text = "Username", fontWeight = FontWeight.Bold)

        // Rotation control switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(if (isRotating) "User is online" else "User is offline")
            Switch(
                checked = isRotating,
                onCheckedChange = { isRotating = it }
            )
        }
    }
}