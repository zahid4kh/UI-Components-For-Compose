package my.ui.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.ZoneId
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    numbersColor: Color = MaterialTheme.colorScheme.onSurface,
    hoursHandColor: Color = MaterialTheme.colorScheme.secondary,
    minutesHandColor: Color = MaterialTheme.colorScheme.tertiary,
    secondsHandColor: Color = MaterialTheme.colorScheme.error,
    onClick: () -> Unit = {}
) {
    var showNumbers by remember { mutableStateOf(false) }

    val transitionState = updateTransition(
        targetState = showNumbers,
        label = "clockTransition"
    )

    val markerScale by transitionState.animateFloat(
        label = "markerScale",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        }
    ) { if (it) 1f else 0f }

    val numberScale by transitionState.animateFloat(
        label = "numberScale",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        }
    ) { if (it) 1f else 0f }

    val alpha by transitionState.animateFloat(
        label = "alpha",
        transitionSpec = {
            tween(durationMillis = 500)
        }
    ) { if (it) 1f else 0f }

    var currentTime by remember { mutableStateOf(LocalTime.now(ZoneId.systemDefault())) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = LocalTime.now(ZoneId.systemDefault())
        }
    }

    val hours = currentTime.hour.toFloat()
    val minutes = currentTime.minute.toFloat()
    val seconds = currentTime.second.toFloat()

    val hoursAngle = (hours + minutes / 60f) * 360f / 12f
    val minutesAngle = (minutes + seconds / 60f) * 360f / 60f
    val secondsAngle = seconds * 360f / 60f




    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(50))
            .clickable(onClick = {showNumbers = !showNumbers})
    ) {
        val diameter = min(size.width, size.height) * 0.96f
        val radius = diameter / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        // outer circle
        drawCircle(
            color = circleColor,
            radius = radius,
            center = center,
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
        )

        // hour markers (dots and numbers)
        for (hour in 1..12) {
            val angle = hour * 360f / 12f - 90f
            val markerRadius = radius - 20.dp.toPx()
            val markerPoint = Offset(
                x = center.x + cos(angle * PI / 180f).toFloat() * markerRadius,
                y = center.y + sin(angle * PI / 180f).toFloat() * markerRadius
            )

            // dots with fade out animation
            drawCircle(
                color = numbersColor.copy(alpha = 1f - alpha),
                radius = 4.dp.toPx() * (1f - markerScale),
                center = markerPoint
            )

            // numbers with fade in animation
            if (showNumbers) {
                drawIntoCanvas { canvas ->
                    val textPaint = Paint().asFrameworkPaint().apply {
                        this.textAlign = android.graphics.Paint.Align.CENTER
                        this.textSize = 18.sp.toPx()
                        this.color = numbersColor
                            .copy(alpha = alpha)
                            .toArgb()
                        this.isFakeBoldText = true
                        this.textScaleX = numberScale
                    }

                    canvas.nativeCanvas.drawText(
                        hour.toString(),
                        markerPoint.x,
                        markerPoint.y + textPaint.textSize / 3,
                        textPaint
                    )
                }
            }
        }


        // hour hand
        drawClockHand(
            center = center,
            angle = hoursAngle - 90f,
            length = radius * 0.5f,
            color = hoursHandColor,
            strokeWidth = 8.dp.toPx()
        )

        // minute hand
        drawClockHand(
            center = center,
            angle = minutesAngle - 90f,
            length = radius * 0.7f,
            color = minutesHandColor,
            strokeWidth = 4.dp.toPx()
        )

        // second hand
        drawClockHand(
            center = center,
            angle = secondsAngle - 90f,
            length = radius * 0.8f,
            color = secondsHandColor,
            strokeWidth = 2.dp.toPx()
        )

        // center dot
        drawCircle(
            color = circleColor,
            radius = 8.dp.toPx(),
            center = center
        )
    }

}

private fun DrawScope.drawClockHand(
    center: Offset,
    angle: Float,
    length: Float,
    color: Color,
    strokeWidth: Float
) {
    rotate(angle, center) {
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x + length, center.y),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
