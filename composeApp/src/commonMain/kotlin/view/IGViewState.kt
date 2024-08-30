package view

import androidx.compose.ui.graphics.ImageBitmap

data class IGViewState(
    val selectImg: ImageBitmap? = null,
    val showImg: ImageBitmap? = null,
    val editedImg: ImageBitmap? = null,
    val borderImg: ImageBitmap? = null,
    val isFillButtonVisible: Boolean = false,
    val isFill: Boolean = false,
    val isSheetVisible: Boolean = false
)
