package my.ui.checklater

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

val emojis = listOf(
    "🤢", "😀", "😂", "😅", "😍", "😎", "🤔", "😴", "🤯", "🥳",
    "😱", "😭", "😡", "🤬", "🙈", "🙉", "🙊", "😇", "🤩", "😜",
    "😝", "🤤", "😒", "😏", "😔", "😞", "😟", "😕", "🙁", "☹️",
    "😣", "😖", "😫", "😩", "🥺", "😤", "😮", "😱", "😨", "😰",
    "😯", "😦", "😧", "😢", "😥", "🤧", "😷", "🤒", "🤕", "🤠",
    "🥳", "🥴", "😵", "🤯", "🤪", "😲", "🤓", "🧐", "😤", "😠",
    "🤬", "😈", "👿", "💀", "☠️", "👻", "👽", "👾", "🤖", "💩",
    "👋", "🤚", "🖐", "✋", "🖖", "👌", "🤌", "🤏", "✌️", "🤞",
    "🤟", "🤘", "🤙", "👈", "👉", "👆", "👇", "☝️", "👍", "👎",
    "✊", "👊", "🤛", "🤜", "👏", "🙌", "👐", "🤲", "🤝", "🙏",
    "✍️", "💅", "🤳", "💪", "🦵", "🦶", "👂", "🦻", "👃", "🫶",
    "🧠", "🫀", "🫁", "👀", "👁", "👅", "👄", "🫦", "👶", "👧",
    "🧒", "👦", "👩", "🧑", "👨", "👩‍🦱", "👨‍🦱", "👩‍🦳", "👨‍🦳", "👩‍🦰",
    "👨‍🦰", "👱‍♀️", "👱‍♂️", "👩‍🦲", "👨‍🦲", "🧔", "🧔‍♂️", "🧔‍♀️", "👵", "🧓",
    "👴", "👲", "👳‍♀️", "👳‍♂️", "🧕", "👮‍♀️", "👮‍♂️", "👷‍♀️", "👷‍♂️", "💂‍♀️",
    "💂‍♂️", "🕵️‍♀️", "🕵️‍♂️", "👩‍⚕️", "👨‍⚕️", "👩‍🌾", "👨‍🌾", "👩‍🍳", "👨‍🍳", "👩‍🎓",
    "👨‍🎓", "👩‍🎤", "👨‍🎤", "👩‍🏫", "👨‍🏫", "👩‍🏭", "👨‍🏭", "👩‍💻", "👨‍💻", "👩‍💼",
    "👨‍💼", "👩‍🔧", "👨‍🔧", "👩‍🔬", "👨‍🔬", "👩‍🎨", "👨‍🎨", "👩‍🚒", "👨‍🚒", "👩‍✈️",
    "👨‍✈️", "👩‍🚀", "👨‍🚀", "👩‍⚖️", "👨‍⚖️", "👰", "🤵", "👰‍♀️", "🤵‍♂️", "👰‍♂️",
    "🤵‍♀️", "👸", "🤴", "🧑‍🦳", "🧑‍🦰", "🧑‍🦱", "🧑‍🦲", "💃", "🕺", "🕴️",
    "👯", "👯‍♀️", "👯‍♂️", "🧖", "🧖‍♀️", "🧖‍♂️", "🧘", "🧘‍♀️", "🧘‍♂️", "💏",
    "👩‍❤️‍💋‍👨", "👩‍❤️‍💋‍👩", "👨‍❤️‍💋‍👨", "💑", "👩‍❤️‍👨", "👩‍❤️‍👩", "👨‍❤️‍👨", "👪", "👨‍👩‍👦", "👨‍👩‍👧",
    "👨‍👩‍👧‍👦", "👨‍👩‍👦‍👦", "👨‍👩‍👧‍👧", "👩‍👩‍👦", "👩‍👩‍👧", "👩‍👩‍👧‍👦", "👩‍👩‍👦‍👦", "👩‍👩‍👧‍👧", "👨‍👨‍👦", "👨‍👨‍👧",
    "👨‍👨‍👧‍👦", "👨‍👨‍👦‍👦", "👨‍👨‍👧‍👧", "👩‍👦", "👩‍👧", "👩‍👧‍👦", "👩‍👦‍👦", "👩‍👧‍👧", "👨‍👦", "👨‍👧",
    "👨‍👧‍👦", "👨‍👦‍👦", "👨‍👧‍👧", "🧑‍🤝‍🧑", "👭", "👫", "👬", "💪", "👈", "👉",
    "👆", "👇", "🖖", "🦵", "🦶", "👂", "🦻", "👃", "💋", "🦷",
    "🌞", "🌝", "🌛", "🌜", "🌚", "🌕", "🌖", "🌗", "🌘", "🌑",
    "🌒", "🌓", "🌔", "🌙", "🌟", "⭐", "🌠", "💫", "✨", "⚡",
    "🔥", "💥", "☄️", "☀️", "🌤", "⛅", "🌥", "🌦", "🌧", "⛈",
    "🌩", "🌨", "🌪", "🌫", "🌬", "🌀", "🌈", "☂️", "☔", "⚽",
    "🏀", "🏈", "⚾", "🎾", "🏐", "🏉", "🥏", "🎱", "🪀", "🏓",
    "🏸", "🥊", "🥋", "🎣", "🤿", "🏹", "🎯", "🪁", "🏆", "🥇",
    "🥈", "🥉", "🎖", "🏅", "🎗", "🏵", "🎫", "🎟", "🎪", "🎭",
    "🎨", "🎬", "🎤", "🎧", "🎼", "🎹", "🥁", "🎷", "🎺", "🎸",
    "🎻", "🪕", "🎲", "♟", "🃏", "🀄", "🎴", "🎳", "🧩", "🪅",
    "🪆", "🛝", "🏖", "🏝", "🌋", "🏔", "⛰", "🏕", "🛤", "🏠",
    "🏡", "🏘", "🏚", "🏢", "🏬", "🏭", "🏣", "🏤", "🏥", "🏦",
    "🏨", "🏩", "💒", "🏛", "⛪", "🕌", "🛕", "🕍", "⛩", "🕋",
    "⛲", "⛺", "🌁", "🌃", "🏙", "🌄", "🌅", "🌆", "🌇", "🌉",
    "🗾", "🏞", "🎠", "🎡", "🎢", "🚂", "🚃", "🚄", "🚅", "🚆",
    "🚇", "🚈", "🚉", "🚊", "🚝", "🚞", "🚋", "🚌", "🚍", "🚎",
    "🚐", "🚑", "🚒", "🚓", "🚔", "🚕", "🚖", "🚗", "🚘", "🚙",
    "🚚", "🚛", "🚜", "🏎", "🏍", "🛵", "🛺", "🚲", "🛴", "🚏"
)

@Composable
fun ReactionSlider(
    onEmojiSelected: (String) -> Unit = {}
) {
    val color1 = Color(0xFF8AC5FF)
    val color2 = Color(0xFF505E6B)

    val localFocus = LocalFocusManager.current

    val listState = rememberLazyListState()
    var totalWidth by remember { mutableStateOf(0) }
    var itemWidth by remember { mutableStateOf(0) }

    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var lastInsertedIndex by remember { mutableStateOf(-1) }

    fun insertEmojiAtCursor(emoji: String) {
        val cursorPosition = textFieldValue.selection.start
        val text = textFieldValue.text

        val newText = buildString {
            append(text.substring(0, cursorPosition))
            append(emoji)
            append(text.substring(cursorPosition))
        }

        // Update both text and cursor position in a single state update
        textFieldValue = TextFieldValue(
            text = newText,
            selection = TextRange(cursorPosition + emoji.length)
        )
    }

    val centerIndex = remember {
        derivedStateOf {
            if (itemWidth == 0) return@derivedStateOf -1

            val centerPoint = 150f  // Center of our 300dp wide box
            val totalScrollOffset = (listState.firstVisibleItemIndex * itemWidth) +
                    listState.firstVisibleItemScrollOffset
            val centerPosition = totalScrollOffset + centerPoint - 150  // Account for padding
            val itemIndexAtCenter = (centerPosition / itemWidth).toInt()
            val positionInItem = centerPosition % itemWidth
            val isCentered = positionInItem in (itemWidth * 0.45f)..(itemWidth * 0.55f)

            if (isCentered) itemIndexAtCenter else -1  // Only select if truly centered
        }
    }

    LaunchedEffect(centerIndex.value) {
        if (centerIndex.value >= 0 && centerIndex.value != lastInsertedIndex) {
            delay(150) // Small delay to make sure the emoji is truly centered
            emojis.getOrNull(centerIndex.value)?.let { emoji ->
                insertEmojiAtCursor(emoji)
                lastInsertedIndex = centerIndex.value
            }
        }
    }

    val borderColor = if (isSystemInDarkTheme()) Color.White else Color.LightGray
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Box(
            modifier = Modifier
                .size(300.dp, 80.dp)
                .border(BorderStroke(width = 3.dp, color = borderColor), RoundedCornerShape(35.dp))
                .clip(RoundedCornerShape(35.dp))
                .background(brush = Brush.linearGradient(listOf(color1, color2)))
                .onSizeChanged { size ->
                    totalWidth = size.width
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                state = listState,
                // Add padding to ensure first and last items can be centered
                contentPadding = PaddingValues(horizontal = 150.dp),
                modifier = Modifier.fillMaxWidth().offset(y = (10).dp)
            ) {
                items(emojis.size) { index ->
                    EmojiItem(
                        emoji = emojis[index],
                        isSelected = index == centerIndex.value,
                        onItemSized = { width ->
                            if (itemWidth == 0) itemWidth = width
                        }
                    )
                }
            }

            // Center indicator (optional, helps with debugging)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(2.dp)
                    .height(16.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Enter Text") },
            shape = RoundedCornerShape(30.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocus.clearFocus() }),
        )
    }

}

@Composable
private fun EmojiItem(
    emoji: String,
    isSelected: Boolean,
    onItemSized: (Int) -> Unit
) {
    // Use a more precise animation with less bounciness
    val yOffset by animateDpAsState(
        targetValue = if (isSelected) -20.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = 0.8f,    // Less bouncy for more controlled movement
            stiffness = Spring.StiffnessLow  // Keep it smooth
        ),
        label = "yOffset"
    )

    // Smoother scale animation
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.4f else 1f,
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(60.dp)
            .onSizeChanged { size ->
                onItemSized(size.width)
            }
            .offset(y = yOffset)
    ) {
        Text(
            text = emoji,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.scale(scale)
        )
    }
}