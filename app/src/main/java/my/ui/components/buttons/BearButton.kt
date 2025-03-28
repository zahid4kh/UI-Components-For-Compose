package my.ui.components.buttons

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.ui.R

@Composable
fun BearButton(
    text: String = "Click Me!",
    onClick: () -> Unit = {}
) {
    // Create a stable key for the button instance
    val buttonKey = remember { System.nanoTime() }

    // Create an interaction source that will track touch events
    val interactionSource = remember(buttonKey) { MutableInteractionSource() }

    // Create a coroutine scope for handling animation timing
    val scope = rememberCoroutineScope()

    // State to track whether the button should be "pressed down"
    var isPressed by remember { mutableStateOf(false) }

    // Collect press events to handle both taps and long presses
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                // When the press starts, immediately show the pressed state
                is PressInteraction.Press -> {
                    isPressed = true
                }
                // When the press ends (either tap or long press), animate back after a short delay
                is PressInteraction.Release -> {
                    scope.launch {
                        delay(50) // Short delay to make the animation visible on taps
                        isPressed = false
                    }
                }
                // If the press is cancelled, immediately reset
                is PressInteraction.Cancel -> {
                    isPressed = false
                }
            }
        }
    }

    // Animate the vertical offset with optimized spring parameters
    val buttonOffset by animateDpAsState(
        targetValue = if (isPressed) 12.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = 0.6f,    // Controls the bounciness
            stiffness = 400f        // Controls the speed
        ),
        label = "buttonPressAnimation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(y = buttonOffset)
            .clip(RoundedCornerShape(35.dp))
            .border(3.dp, Color(0xFF107665), RoundedCornerShape(35.dp))
            .clickable(
                // Use our custom interaction source
                interactionSource = interactionSource,
                // Keep the default ripple indication
                indication = LocalIndication.current,
                onClick = onClick
            )
    ) {
        // Background rectangle
        Icon(
            painter = painterResource(R.drawable.rectangle_1),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(350.dp, 72.dp)
        )

        // Create a row to position bear and text
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bear icon at the start
            Icon(
                painter = painterResource(R.drawable.bear),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Button text
            Text(
                text = text,
                color = Color(0xFF107665),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}