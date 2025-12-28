package wingoritm.mobile.recall.features.editor.data

data class EditorialUIState (
    val title: String = "",
    val content: String = "",
    val toolbarTitle: String = "",
    val isDeleteDisabled: Boolean = false,
    val isLoading: Boolean = false
)