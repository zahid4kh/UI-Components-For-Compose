package my.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComponentsGrid(
    padding: PaddingValues,
    selectedComponent: ComponentItem?,
    onSelected: (ComponentItem) -> Unit,
    components: List<ComponentItem>
) {
    var selectedCardBounds by remember { mutableStateOf<CardBounds?>(null) }
    var screenSize by remember { mutableStateOf<Size?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .onGloballyPositioned { coordinates ->
                screenSize = Size(
                    coordinates.size.width.toFloat(),
                    coordinates.size.height.toFloat()
                )
            }
    ) {
        AnimatedContent(
            targetState = selectedComponent,
            label = "componentTransition",
            transitionSpec = {
                val springSpec = spring<Float>(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )

                val (initialScale, targetScale) = when {
                    targetState != null -> 0.3f to 1f
                    else -> 1f to 0.3f
                }

                val transformOrigin = selectedCardBounds?.let { bounds ->
                    screenSize?.let { size ->
                        TransformOrigin(
                            pivotFractionX = (bounds.left + bounds.width / 2) / size.width,
                            pivotFractionY = (bounds.top + bounds.height / 2) / size.height
                        )
                    }
                } ?: TransformOrigin(0.5f, 0.5f)

                scaleIn(
                    animationSpec = springSpec,
                    initialScale = initialScale,
                    transformOrigin = transformOrigin
                ) togetherWith scaleOut(
                    animationSpec = springSpec,
                    targetScale = targetScale,
                    transformOrigin = transformOrigin
                )
            }
        ) { targetComponent ->
            if (targetComponent == null) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 130.dp),
                    contentPadding = padding,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(components) { componentItem ->
                        var cardBounds by remember { mutableStateOf<CardBounds?>(null) }
                        var isHovered by remember { mutableStateOf(false) }

                        val hoverScale by animateFloatAsState(
                            targetValue = if (isHovered) 1.05f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "hoverScale"
                        )

                        UIComponent(
                            text = componentItem.text,
                            onClick = {
                                selectedCardBounds = cardBounds
                                onSelected(componentItem)
                            },
                            modifier = Modifier
                                .scale(hoverScale)
                                .trackBounds { bounds ->
                                    cardBounds = bounds
                                }
                                .pointerInput(Unit) {
                                    awaitPointerEventScope {
                                        while (true) {
                                            val event = awaitPointerEvent()
                                            when (event.type) {
                                                PointerEventType.Enter -> isHovered = true
                                                PointerEventType.Exit -> isHovered = false
                                            }
                                        }
                                    }
                                }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    targetComponent.component()
                }
            }
        }
    }
}

@Composable
fun Modifier.trackBounds(
    onBoundsChange: (CardBounds) -> Unit
): Modifier = composed {
    val density = LocalDensity.current

    this.then(
        Modifier.onGloballyPositioned { coordinates ->
            with(density) {
                onBoundsChange(
                    CardBounds(
                        left = coordinates.positionInRoot().x,
                        top = coordinates.positionInRoot().y,
                        width = coordinates.size.width.toFloat(),
                        height = coordinates.size.height.toFloat()
                    )
                )
            }
        }
    )
}

data class CardBounds(
    val left: Float,
    val top: Float,
    val width: Float,
    val height: Float
)
