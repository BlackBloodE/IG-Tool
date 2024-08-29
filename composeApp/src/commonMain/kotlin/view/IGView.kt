package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.github.panpf.zoomimage.ZoomImage
import com.github.panpf.zoomimage.compose.rememberZoomState
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetState
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import org.jetbrains.compose.ui.tooling.preview.Preview
import utlis.ImageRatio
import utlis.cropImage
import utlis.determineAspectRatio


@Preview
@Composable
fun IGView() {
    InstagramPostItem()
}

@Composable
fun InstagramPostItem() {
    var selectImage: ImageBitmap? by remember {
        mutableStateOf(null)
    }

    var showImage: ImageBitmap? by remember {
        mutableStateOf(null)
    }

    var isShowFillBtn by remember {
        mutableStateOf(false)
    }

    var isFill by remember {
        mutableStateOf(false)
    }

    var isShowSheet by remember {
        mutableStateOf(false)
    }

    val sheetState = rememberFlexibleBottomSheetState(
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.9f,
            intermediatelyExpanded = 0.9f,
            slightlyExpanded = 0.9f
        ),
        isModal = true,
        skipSlightlyExpanded = false,
    )

    val scope = rememberCoroutineScope()

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                // Process the selected images' ByteArrays.
                selectImage = it.toImageBitmap()
                if (selectImage.determineAspectRatio() == ImageRatio.Unknown) {
                    isShowFillBtn = true
                    isShowSheet = true
                } else {
                    showImage = selectImage
                    isShowFillBtn = false
                }
            }
        }
    )

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // 使用者資訊列
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //使用者頭像
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            // 使用者名稱
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(24.dp)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.weight(1f))
            // 更多選項圖示
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "更多選項"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // 貼文圖片
        Box(
            modifier = Modifier.clickable {
                singleImagePicker.launch()
            }.background(color = Color.Red)
        ) {
            if (showImage == null) {
                Box(
                    modifier = Modifier
                        .aspectRatio(16f / 9f) // 設定 16:9 的長寬比
                        .background(Color.LightGray) // 設定佔位符的背景顏色
                )
            } else {
                Image(
                    bitmap = showImage!!,
                    contentDescription = "貼文圖片",
                    modifier = Modifier.fillMaxWidth()
                )
            }
//            if (isShowFillBtn) {
//                if (isFill) {
//                    selectImage?.let {
//                        showImage = autoAddBorder(
//                            originalImage = it
//                        )
//                    }
//                } else {
//                    val targetAspectRatio =
//                        if (selectImage!!.width > selectImage!!.height) 3f / 2f else 4f / 5f
//                    selectImage?.let {
//                        showImage = it.cropToAspectRatio(targetAspectRatio)
//                    }
//                }
//                IconButton(
//                    onClick = {
//                        isFill = !isFill
//                    },
//                    modifier = Modifier.align(Alignment.BottomStart)
//                ) {
//                    Image(
//                        imageVector = if (isFill) Octicons.ScreenNormal24 else Octicons.ScreenFull24,
//                        contentDescription = "縮放"
//                    )
//                }
//            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // 貼文互動列
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 喜歡圖示
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // 留言圖示
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // 分享圖示
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.weight(1f))
            // 儲存圖示
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // 喜歡人數
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(12.dp)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(4.dp))
        // 貼文說明
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Color.LightGray)
        )
    }

    if (isShowSheet && selectImage != null) {
        ImageEditorView(
            image = selectImage!!,
            sheetState = sheetState,
            onDismissRequest = { resultImage ->
                isShowSheet = false
                showImage = resultImage
            }
        )
    }
}

@Composable
fun ImageEditorView(
    image: ImageBitmap,
    sheetState: FlexibleSheetState,
    onDismissRequest: (resultImage: ImageBitmap) -> Unit
) {
    val zoomState = rememberZoomState()
    val ratio = when (image.determineAspectRatio()) {
        ImageRatio._16_9 -> 16f / 9f
        ImageRatio._3_2 -> 3f / 2f
        ImageRatio._4_3 -> 4f / 3f
        ImageRatio._3_4 -> 3f / 4f
        ImageRatio._4_5 -> 4f / 5f
        ImageRatio._1_1 -> 1f
        else -> {
            if (image.determineAspectRatio().isLandscape) {
                16f / 9f
            } else {
                4f / 5f
            }
        }
    }

    FlexibleBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            val resultImage = image.cropImage(
                offset = zoomState.zoomable.transform.offset,
                scaleX = zoomState.zoomable.transform.scaleX,
                scaleY = zoomState.zoomable.transform.scaleY,
                targetAspectRatio = ratio
            )
            println(resultImage.width)
            println(resultImage.height)
            onDismissRequest(resultImage)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().aspectRatio(ratio)
        ) {
            ZoomImage(
                painter = BitmapPainter(image),
                contentDescription = "view image",
                modifier = Modifier.fillMaxSize(),
                zoomState = zoomState,
                contentScale = ContentScale.FillWidth,
                scrollBar = null
            )
        }
    }
}