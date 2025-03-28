package my.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import localrun.ComponentItem
import localrun.ComponentsGrid
import localrun.components
import my.ui.ui.theme.MyUITheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyUITheme {
                var selectedComponent: ComponentItem? by remember { mutableStateOf(null) }

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