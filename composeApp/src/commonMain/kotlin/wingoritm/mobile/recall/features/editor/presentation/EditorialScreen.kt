package wingoritm.mobile.recall.features.editor.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import wingoritm.mobile.recall.core.designSystem.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorialScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    isPreviewEnabled: Boolean = false
) {
    // State for text inputs
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // Focus Requester to handle keyboard auto-show
    val focusRequester = remember { FocusRequester() }

    // Req 3: Request focus immediately when screen launches
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    // back icon
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Eye icon disabled/enabled based on param
                    IconButton(
                        onClick = { /* Handle Preview */ },
                        enabled = isPreviewEnabled
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview",
                            tint = if (isPreviewEnabled) MaterialTheme.colorScheme.onSurface else Color.Gray.copy(alpha = 0.5f)
                        )
                    }
                    IconButton(onClick = onSaveClick) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .imePadding() // padding for keyboard so text isn't hidden
        ) {
            // Req 2: Title Field
            TransparentTextField(
                text = title,
                onValueChange = { title = it },
                placeholder = "Title",
                textStyle = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                // Attach the focus requester here
                modifier = Modifier.focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Req 2: Content Field
            TransparentTextField(
                text = content,
                onValueChange = { content = it },
                placeholder = "Type something...",
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f) // Takes up remaining space
            )
        }
    }
}

/**
 * A helper composable for the transparent text fields seen in the design
 */
@Composable
fun TransparentTextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle,
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

/**
 * preview entry point
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        EditorialScreen(
           onBackClick = {},
           onSaveClick = {},
           isPreviewEnabled = false
        )
    }
}