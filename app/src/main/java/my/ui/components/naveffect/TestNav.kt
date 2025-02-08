package my.ui.components.naveffect

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TestNavHost() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        NavHost(
            navController = navController,
            startDestination = "pageA",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = LinearOutSlowInEasing
                    )
                ) + slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth/2 },
                    animationSpec = spring(
                        dampingRatio = 1f,
                        stiffness = 300f
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(280)
                ) + slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth/2 },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = LinearOutSlowInEasing
                    )
                ) + slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth/2 },
                    animationSpec = spring(
                        dampingRatio = 1f,
                        stiffness = 300f
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(280)
                ) + slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth/2 },
                    animationSpec = tween(300)
                )
            }
        ) {
            composable("pageA") {
                PageA(navController = navController)
            }

            composable("pageB") {
                PageB(navController = navController)
            }
        }
    }
}