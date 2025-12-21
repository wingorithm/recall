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
import androidx.compose.material3.FilledTonalIconButton
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import wingoritm.mobile.recall.core.designSystem.AppTheme
import wingoritm.mobile.recall.features.editor.data.EditorialUIState

@Composable
fun EditorialScreen(
    onBackClick: () -> Unit,
    isPreviewEnabled: Boolean = false,
    noteId: String?,
    viewModel: EditorialViewModel = koinViewModel<EditorialViewModel>(
        parameters = { parametersOf(noteId) }
    )

) {
    val state by viewModel.uiState.collectAsState()

    EditorialContent(
        state = state,
        onBackClick = onBackClick,
        onSaveClick = { viewModel.save() },
        onTitleChange = { viewModel.onTitleChange(it) },
        onContentChange = { viewModel.onContentChange(it) },
        isPreviewEnabled = isPreviewEnabled
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditorialContent(
    state: EditorialUIState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    isPreviewEnabled: Boolean = false
) {
    val focusRequester = remember { FocusRequester() } // Focus Requester to handle keyboard auto-show

    // Request focus immediately when screen launches and the title is empty
    LaunchedEffect(Unit) {
        if (state.title.isEmpty()){
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.toolbarTitle,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                actions = {
                    // Eye icon disabled/enabled based on param
                    FilledTonalIconButton(
                        onClick = { /* Handle Preview */ },
                        enabled = isPreviewEnabled
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview",
                        )
                    }

                    // Save icon
                    FilledTonalIconButton(onClick = onSaveClick) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save"
                        )
                    }
                }
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
                text = state.title,
                onValueChange = onTitleChange,
                placeholder = "Title skibidi",
                textStyle = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                modifier = Modifier.focusRequester(focusRequester) // Attach the focus requester here
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Req 2: Content Field
            TransparentTextField(
                text = state.content,
                onValueChange = onContentChange,
                placeholder = "Type something...",
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
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
fun EditorialScreenPreview() {
    AppTheme {
        EditorialContent(
            state = EditorialUIState(
                title = "Recall Title Idea",
                content = "This is a preview of the insight on recall..."
            ),
            onBackClick = {},
            onSaveClick = {},
            onTitleChange = {},
            onContentChange = {},
            isPreviewEnabled = true
        )
    }
}