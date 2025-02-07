package my.ui

import androidx.compose.runtime.Composable

data class ComponentItem(
    val text: String,
    val component: @Composable () -> Unit
)