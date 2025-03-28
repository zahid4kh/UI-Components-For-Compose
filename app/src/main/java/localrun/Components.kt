package localrun

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
import my.ui.checklater.AchievementBadgeDemo
import my.ui.checklater.AnimatedSlidersIcon
import my.ui.checklater.BroadcastLiveStreamBadge
import my.ui.checklater.GamingLiveStreamBadge
import my.ui.checklater.GradientLiveStreamBadge
import my.ui.checklater.MinimalistLiveStreamBadge
import my.ui.checklater.NeonLiveStreamBadge
import my.ui.checklater.ReactionSlider
import my.ui.components.progressbars.StoryProgressDemo
import my.ui.components.buttons.BearButton
import my.ui.components.buttons.PrizeButton
import my.ui.components.clocks.AnalogClock
import my.ui.naveffect.TestNavHost
import my.ui.components.sharecards.ShareCardDemo
import my.ui.components.sharecards.ShareCardHandler

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
val components = listOf(
    ComponentItem("Bouncing Bear Button") { BearButton() },
    ComponentItem("Prize Button") { PrizeButton() },
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
    ComponentItem("Material Transition") { TestNavHost() }
)
