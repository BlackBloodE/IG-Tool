package utlis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs

enum class ImageRatio(
    val widthScale: Int = 0,
    val heightScale: Int = 0,
    val isLandscape: Boolean = widthScale > heightScale
) {
    _16_9(
        widthScale = 16,
        heightScale = 9
    ),
    _3_2(
        widthScale = 3,
        heightScale = 2
    ),
    _4_3(
        widthScale = 4,
        heightScale = 3
    ),
    _3_4(
        widthScale = 3,
        heightScale = 4
    ),
    _4_5(
        widthScale = 4,
        heightScale = 5
    ),
    _1_1(
        widthScale = 1,
        heightScale = 1
    ),
    Unknown
}

fun ImageBitmap?.determineAspectRatio(): ImageRatio {
    if (this == null) {
        return ImageRatio.Unknown
    }
    val aspectRatio = this.width.toFloat() / this.height.toFloat()
    return when {
        aspectRatio.isCloseTo(16f / 9f) -> ImageRatio._16_9
        aspectRatio.isCloseTo(3f / 2f) -> ImageRatio._3_2
        aspectRatio.isCloseTo(4f / 3f) -> ImageRatio._4_3
        aspectRatio.isCloseTo(3f / 4f) -> ImageRatio._3_4
        aspectRatio.isCloseTo(4f / 5f) -> ImageRatio._4_5
        aspectRatio.isCloseTo(1f) -> ImageRatio._1_1
        else -> ImageRatio.Unknown
    }
}

fun autoAddBorder(
    originalImage: ImageBitmap
): ImageBitmap {
    return createImageWithBorder(
        originalImage = originalImage,
        targetAspectRatio = ImageRatio._1_1,
        borderColor = Color.White
    )
}

fun ImageBitmap.cropImage(
    offset: Offset = Offset.Zero,
    scaleX: Float = 1f,
    scaleY: Float = 1f,
    targetAspectRatio: Float
): ImageBitmap {
    val width = this.width
    val height = this.height
    val currentAspectRatio = width.toFloat() / height.toFloat()

    // Determine the target width and height for the cropped image
    val (targetWidth, targetHeight, xOffset, yOffset) = if (currentAspectRatio > targetAspectRatio) {
        val targetWidth = (height * targetAspectRatio).toInt()
        val xOffset = (width - targetWidth) / 2
        Quadruple(targetWidth, height, xOffset, 0)
    } else {
        val targetHeight = (width / targetAspectRatio).toInt()
        val yOffset = (height - targetHeight) / 2
        Quadruple(width, targetHeight, 0, yOffset)
    }

    // Calculate the scaled dimensions
    val scaledWidth = (targetWidth * scaleX).toInt()
//    val scaledWidth = (targetWidth / scaleX).toInt()
    val scaledHeight = (targetHeight * scaleY).toInt()
//    val scaledHeight = (targetHeight / scaleY).toInt()

    // Create a new ImageBitmap with the target dimensions
    val croppedBitmap = ImageBitmap(scaledWidth, scaledHeight)
//    val croppedBitmap = ImageBitmap(targetWidth, targetHeight)

    // Draw the cropped area onto the new ImageBitmap
    val canvas = Canvas(croppedBitmap)
    canvas.scale(scaleX, scaleY)
    canvas.drawImageRect(
        image = this,
//        srcOffset = IntOffset(0, 0),
        srcSize = IntSize(targetWidth, targetHeight),
        dstOffset = IntOffset(offset.x.toInt(), offset.y.toInt()),
        dstSize = IntSize(scaledWidth, scaledHeight),
        paint = Paint()
    )
//    canvas.drawImage(
//        image = this,
//        topLeftOffset = offset,
//        paint = Paint()
//    )

    return croppedBitmap
}

data class Quadruple(val targetWidth: Int, val targetHeight: Int, val xOffset: Int, val yOffset: Int)

fun createImageWithBorder(
    originalImage: ImageBitmap,
    targetAspectRatio: ImageRatio,
    borderColor: Color
): ImageBitmap {
    val originalWidth = originalImage.width
    val originalHeight = originalImage.height
    val originalAspectRatio = originalWidth.toDouble() / originalHeight.toDouble()

    val targetWidth = targetAspectRatio.widthScale
    val targetHeight = targetAspectRatio.heightScale
    val targetAspectRatioDouble = targetWidth.toDouble() / targetHeight.toDouble()

    val finalWidth: Int
    val finalHeight: Int

    if (originalAspectRatio > targetAspectRatioDouble) {
        finalWidth = originalWidth
        finalHeight = (originalWidth / targetAspectRatioDouble).toInt()
    } else {
        finalHeight = originalHeight
        finalWidth = (originalHeight * targetAspectRatioDouble).toInt()
    }

    val resultBitmap = ImageBitmap(finalWidth, finalHeight)
    val canvas = Canvas(resultBitmap)

    // Fill the canvas with border color
    canvas.drawRect(
        Rect(0f, 0f, finalWidth.toFloat(), finalHeight.toFloat()),
        Paint().apply { this.color = borderColor }
    )

    // Draw the original image centered in the new canvas
    val offsetX = (finalWidth - originalWidth) / 2f
    val offsetY = (finalHeight - originalHeight) / 2f
    canvas.drawImage(originalImage, Offset(offsetX, offsetY), Paint())

    return resultBitmap
}

// 輔助函數，用於判斷兩個浮點數是否相近
private fun Float.isCloseTo(other: Float, tolerance: Float = 0.01f): Boolean {
    return abs(this - other) < tolerance
}