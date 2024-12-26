package my.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp


@Composable
fun AnimatedSlidersIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    // Animation states for three slider handles
    val infiniteTransition = rememberInfiniteTransition(label = "sliders")

    // Top slider animation
    val topSliderX by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "top slider"
    )

    // Middle slider animation with delay
    val middleSliderX by infiniteTransition.animateFloat(
        initialValue = 5f,
        targetValue = -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing, delayMillis = 100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "middle slider"
    )

    // Bottom slider animation with longer delay
    val bottomSliderX by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bottom slider"
    )

    Canvas(
        modifier = modifier.size(24.dp)
    ) {
        val strokeWidth = size.width * 0.083f // Equivalent to 2dp stroke width

        // Draw horizontal lines and handles for each slider
        // Top slider
        drawLine(
            color = tint,
            start = Offset(size.width * 0.125f, size.height * 0.167f),
            end = Offset(size.width * 0.875f, size.height * 0.167f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(size.width * (0.583f + topSliderX/100), size.height * 0.083f),
            end = Offset(size.width * (0.583f + topSliderX/100), size.height * 0.25f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Middle slider
        drawLine(
            color = tint,
            start = Offset(size.width * 0.125f, size.height * 0.5f),
            end = Offset(size.width * 0.875f, size.height * 0.5f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(size.width * (0.333f + middleSliderX/100), size.height * 0.417f),
            end = Offset(size.width * (0.333f + middleSliderX/100), size.height * 0.583f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Bottom slider
        drawLine(
            color = tint,
            start = Offset(size.width * 0.125f, size.height * 0.833f),
            end = Offset(size.width * 0.875f, size.height * 0.833f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(size.width * (0.667f + bottomSliderX/100), size.height * 0.75f),
            end = Offset(size.width * (0.667f + bottomSliderX/100), size.height * 0.917f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}