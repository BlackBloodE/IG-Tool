package view

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import utlis.ImageRatio
import utlis.autoAddBorder
import utlis.determineAspectRatio

class IGViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(IGViewState())
    val uiState: StateFlow<IGViewState> = _uiState.asStateFlow()

    fun loadImage() {
        //載入圖片邏輯，更新 _state.value
    }

    fun toggleFillMode() {
        _uiState.value = _uiState.value.copy(
            isFill = !_uiState.value.isFill,
            showImg = if (_uiState.value.isFill) _uiState.value.editedImg else _uiState.value.borderImg
        )
    }

    fun showEditSheet() {
        _uiState.value = _uiState.value.copy(isSheetVisible = true)
    }

    fun hideEditSheet() {
        _uiState.value = _uiState.value.copy(isSheetVisible = false)
    }

    fun onImageSelected(image: ImageBitmap) {// 處理選擇的圖片，更新 _uiState.value
        if (image.determineAspectRatio() == ImageRatio.Unknown) {
            _uiState.value = _uiState.value.copy(
                selectImg = image,
                borderImg = autoAddBorder(image),
                isFillButtonVisible = true,
                isSheetVisible = true
            )
        } else {
            _uiState.value = _uiState.value.copy(
                selectImg = image,
                showImg = image,
                isFillButtonVisible = false
            )
        }
    }

    fun onImageEdited(image: ImageBitmap) {
        _uiState.value = _uiState.value.copy(
            editedImg = image,
            showImg = image,
            isSheetVisible = false
        )
    }
}