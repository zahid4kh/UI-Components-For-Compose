package my.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import my.ui.components.AchievementBadgeDemo
import my.ui.components.AnalogClock
import my.ui.components.AnimatedSlidersIcon
import my.ui.components.BearButton
import my.ui.components.BroadcastLiveStreamBadge
import my.ui.components.GamingLiveStreamBadge
import my.ui.components.GradientLiveStreamBadge
import my.ui.components.MinimalistLiveStreamBadge
import my.ui.components.NeonLiveStreamBadge
import my.ui.components.PrizeButton
import my.ui.components.ReactionSlider
import my.ui.components.ShareCardDemo
import my.ui.components.ShareCardHandler
import my.ui.components.StoryBubble
import my.ui.components.StoryProgressDemo
import my.ui.ui.theme.MyUITheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyUITheme {
                var selectedComponent: ComponentItem? by remember { mutableStateOf(null) }

                val components = listOf(
                    ComponentItem("Bouncing Bear Button") { BearButton() },
                    ComponentItem("Prize Button") { PrizeButton() },
                    ComponentItem("Story Bubble") { StoryBubble(size = 100) },
                    ComponentItem("Reaction Slider") { ReactionSlider() },
                    ComponentItem("Gradient Live Stream Badge") {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            GradientLiveStreamBadge(500)
                            NeonLiveStreamBadge(126)
                            GamingLiveStreamBadge(8888)
                            MinimalistLiveStreamBadge(34)
                            BroadcastLiveStreamBadge(69)
                        }
                    },
                    ComponentItem("Story Progress Bars") { StoryProgressDemo() },
                    ComponentItem("Share Cards") {
                        ShareCardHandler(
                            onShareClick = {}
                        ) {
                            ShareCardDemo()
                        }
                    },
                    ComponentItem("Achievement Badges") { AchievementBadgeDemo() },
                    ComponentItem("Animated Sliders") { AnimatedSlidersIcon(Modifier.size(70.dp)) },
                    ComponentItem("Analog Clock") {
                        AnalogClock(
                            modifier = Modifier.size(300.dp),
                            circleColor = MaterialTheme.colorScheme.primary,
                            secondsHandColor = Color.Red.copy(alpha = 0.7f),
                        )
                    },
                )

                Scaffold(
                    topBar = {
                        MyTopAppBar(
                            selectedComponent = selectedComponent,
                            onCollapse = { selectedComponent = null }
                        )
                    }
                ){padding->
                    ComponentsGrid(
                        padding = padding,
                        selectedComponent = selectedComponent,
                        onSelected = { selectedComponent = it },
                        components = components
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(selectedComponent: ComponentItem?, onCollapse: () -> Unit) {
    TopAppBar(
        title = { Text("UI KIT") },
        actions = {
            if (selectedComponent != null) {
                TextButton(onClick = onCollapse) {
                    Text("Collapse")
                }
            }
        }
    )
}


@Composable
fun UIComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .padding(8.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}