package my.ui.components.buttons

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import my.ui.R
import kotlin.math.sin

@Composable
fun PrizeButton(
    text: String = "Click Me!",
    onClick: () -> Unit = {}
) {
    // Create our infinite transition for continuous animation
    val infiniteTransition = rememberInfiniteTransition(label = "prizeButtonAnimation")

    // Create a time-based animation that we'll use to drive both shake and bounce
    // We use sine waves to create smooth, natural-looking motion
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                // Complete one full cycle every 1000ms
                durationMillis = 1000,
                // Use easing to make the motion feel more natural
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shakeAnimation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(35.dp))
            .clickable(onClick = onClick)
            .graphicsLayer {
                // Create rotation effect using sine wave
                // Multiply by 3 degrees for subtle rotation
                rotationZ = sin(time) * 3f

                // Create bouncing effect using a different phase of the sine wave
                // Offset the sine wave by PI/2 to create interesting combined motion
                translationY = sin(time + Math.PI.toFloat() / 2) * 3f
            }
    ) {
        // Background rectangle (prize button image)
        Icon(
            painter = painterResource(R.drawable.prize_button),
            contentDescription = "Prize Button",
            tint = Color.Unspecified,
        )
    }
}
