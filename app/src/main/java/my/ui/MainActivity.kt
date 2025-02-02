package my.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import my.ui.components.dialog.DialogExample
import my.ui.ui.theme.MyUITheme
import java.time.Clock

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyUITheme {
                Components()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Components() {
    var showBearButton by remember { mutableStateOf(false) }
    var showPrizeButton by remember { mutableStateOf(false) }
    var showStoryBubble by remember { mutableStateOf(false) }
    var showReactionSlider by remember { mutableStateOf(false) }
    var showLiveBadge by remember { mutableStateOf(false) }
    var showStoryProgress by remember { mutableStateOf(false) }
    var showShareCards by remember { mutableStateOf(false) }
    var showAchievementBadge by remember { mutableStateOf(false) }
    var showAnimatedSliders by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var showAnalogClock by remember { mutableStateOf(false) }

    LazyColumn(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 30.dp),
        userScrollEnabled = true

    ){
        item{
            UIComponent(
                text = "Bouncing Bear Button",
                onClick = {showBearButton = !showBearButton},
                showComponent = showBearButton,
                component = { BearButton() }
            )
        }

        item{
            UIComponent(
                text = "Prize Button",
                onClick = {showPrizeButton = !showPrizeButton},
                showComponent = showPrizeButton,
                component = { PrizeButton() }
            )
        }

        item{
            UIComponent(
                text = "Story Bubble",
                onClick = {showStoryBubble = !showStoryBubble},
                showComponent = showStoryBubble,
                component = { StoryBubble(size = 100) }
            )
        }

        item{
            UIComponent(
                text = "Reaction Slider",
                onClick = {showReactionSlider = !showReactionSlider},
                showComponent = showReactionSlider,
                component = { ReactionSlider() }
            )
        }

        item{
            UIComponent(
                text = "Gradient Live Stream Badge",
                onClick = {showLiveBadge = !showLiveBadge},
                showComponent = showLiveBadge,
                component = {
                    FlowRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth(),
//                        maxItemsInEachRow = 2
                    ){
                        GradientLiveStreamBadge(500)
                        NeonLiveStreamBadge(126)
                        GamingLiveStreamBadge(8888)
                        MinimalistLiveStreamBadge(34)
                        BroadcastLiveStreamBadge(69)
                    }
                }
            )
        }

        item{
            UIComponent(
                text = "Story Progress Bars",
                onClick = { showStoryProgress = !showStoryProgress },
                showComponent = showStoryProgress,
                component = { StoryProgressDemo() }
            )
        }

        item{
            UIComponent(
                text = "Share Cards",
                onClick = { showShareCards = !showShareCards },
                showComponent = showShareCards,
                component = {
                    ShareCardHandler(
                        onShareClick = { platform ->
                            // Handle share action based on platform
                        }
                    ) {
                        ShareCardDemo()
                    }
                }
            )
        }

        item{
            UIComponent(
                text = "Achievement Badges",
                onClick = {showAchievementBadge = !showAchievementBadge},
                showComponent = showAchievementBadge,
                component = { AchievementBadgeDemo() }
            )
        }

        item{
            UIComponent(
                text = "Animated Sliders",
                onClick = { showAnimatedSliders = !showAnimatedSliders },
                showComponent = showAnimatedSliders,
                component = { AnimatedSlidersIcon(Modifier.size(70.dp)) }
            )
        }

        item{
            UIComponent(
                text = "Custom Dialog",
                onClick = {showCustomDialog = !showCustomDialog},
                showComponent = showCustomDialog,
                component = { DialogExample() }
            )
        }

        item{
            UIComponent(
                text = "Analog Clock",
                onClick = {showAnalogClock = !showAnalogClock},
                showComponent = showAnalogClock,
                component = { AnalogClock(
                    modifier = Modifier.size(300.dp),
                    circleColor = MaterialTheme.colorScheme.primary,
                    secondsHandColor = Color.Red.copy(alpha = 0.7f),
                ) }
            )
        }
    }
}


@Composable
fun UIComponent(text: String, onClick: () -> Unit, showComponent: Boolean, component: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        TextButton(onClick = onClick){
            Text(text = text)
        }
        AnimatedVisibility(visible = showComponent) {
            component()
        }
    }

}