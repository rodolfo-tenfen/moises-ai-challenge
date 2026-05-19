package tenfen.rodolfo.moisesaichallenge.presentation.songs.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tenfen.rodolfo.moisesaichallenge.presentation.theme.MoisesAIChallengeTheme

@Composable
internal fun SearchInput(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = query,
        onValueChange = onQueryChanged,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        cursorBrush = SolidColor(Color.White),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchTriggered()
                focusManager.clearFocus()
                keyboardController?.hide()
            },
        ),
        decorationBox = { textField -> SearchInputDecorationBox(textField, query, onQueryChanged) },
        singleLine = true,
        modifier = modifier.fillMaxWidth().height(44.dp),
    )
}

@Composable
private fun SearchInputDecorationBox(
    textField: @Composable (() -> Unit),
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(horizontal = 16.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                )
            }

            textField()
        }

        if (query.isNotEmpty()) {
            IconButton(
                onClick = { onQueryChanged("") },
                modifier = Modifier.size(24.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear text", // FIXME
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SearchInputPreview() {
    MoisesAIChallengeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SearchInput(query = "", onQueryChanged = {}, onSearchTriggered = {})

            SearchInput(query = "Purple Rain", onQueryChanged = {}, onSearchTriggered = {})
        }
    }
}
