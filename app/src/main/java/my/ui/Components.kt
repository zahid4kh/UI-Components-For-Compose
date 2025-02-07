package my.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
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
