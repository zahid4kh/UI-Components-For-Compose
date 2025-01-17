package my.ui.components.dialog

import android.app.Dialog
import android.content.ContextWrapper
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    scrimColor: Color = Color(0xCC000000),
    content: @Composable () -> Unit
) {
    val currentView = LocalView.current
    val context = currentView.context

    // Get all necessary owners from the current view
    val lifecycleOwner = currentView.findViewTreeLifecycleOwner()
    val savedStateRegistryOwner = currentView.findViewTreeSavedStateRegistryOwner()
    val viewModelStoreOwner = currentView.findViewTreeViewModelStoreOwner()

    DisposableEffect(context) {
        val dialog = object : Dialog(context, android.R.style.Theme_DeviceDefault_Dialog) {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                window?.apply {
                    setBackgroundDrawable(ColorDrawable(scrimColor.toArgb()))
                    requestFeature(Window.FEATURE_NO_TITLE)
                    setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT
                    )
                }

                val composeView = ComposeView(context).apply {
                    // Set all necessary owners on our ComposeView
                    setViewTreeLifecycleOwner(lifecycleOwner)
                    setViewTreeSavedStateRegistryOwner(savedStateRegistryOwner)
                    viewModelStoreOwner?.let { setViewTreeViewModelStoreOwner(it) }

                    // Set a composition strategy that matches the lifecycle
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                    )

                    setContent {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { onDismissRequest() }
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {}
                            ) {
                                content()
                            }
                        }
                    }
                }

                setContentView(composeView)
            }
        }

        dialog.show()

        onDispose {
            dialog.dismiss()
        }
    }
}




// Usage example
@Composable
fun DialogExample() {
    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = { showDialog = true }) {
        Text("Show Custom Dialog")
    }

    if (showDialog) {
        CustomDialog(
            onDismissRequest = { showDialog = false },
            scrimColor = Color.Red.copy(alpha = 0.2f) // Custom red tinted scrim
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Custom Dialog",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "This dialog has a custom scrim color",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}