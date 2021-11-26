package me.kyuubiran.qqcleaner.ui.utils

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

// Todo 重新完成弥散
/**
 * @see <a href="https://github.com/itaqhi/ComposeShadow/blob/master/app/src/main/java/com/itaqhi/compose/shadow/ui/util/Shadow.kt">
 *     控件来源
 * </a>
 * 应用阴影效果，支持颜色、偏移和模糊半径。
 * 注意 Modifier 的应用顺序，应在 background 之前应用。
 * 由于父控件会限制子控件的绘制区域，请确保父控件的大小超出子控件的阴影区域。
 */
fun Modifier.shadow(
    shadow: Shadow,
    shape: Shape = RectangleShape
) = this.then(
    ShadowModifier(shadow, shape)
)

/**
 * 参考 Flutter 中 Shadow 的实现
 */
private class ShadowModifier(
    val shadow: Shadow,
    val shape: Shape
) : DrawModifier {

    override fun ContentDrawScope.draw() {
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = shadow.color
                asFrameworkPaint().apply {
                    maskFilter = BlurMaskFilter(
                        convertRadiusToSigma(shadow.blurRadius),
                        BlurMaskFilter.Blur.NORMAL
                    )
                }
            }
            shape.createOutline(
                size, layoutDirection, this
            ).let { outline ->
                canvas.drawWithOffset(shadow.offset) {
                    when (outline) {
                        is Outline.Rectangle -> {
                            drawRect(outline.rect, paint)
                        }
                        is Outline.Rounded -> {
                            drawPath(
                                Path().apply { addRoundRect(outline.roundRect) },
                                paint
                            )
                        }
                        is Outline.Generic -> {
                            drawPath(outline.path, paint)
                        }
                    }
                }
            }
        }
        drawContent()
    }

    private fun convertRadiusToSigma(
        radius: Float,
        enable: Boolean = true
    ): Float = if (enable) {
        (radius * 0.57735 + 0.5).toFloat()
    } else {
        radius
    }

    private fun Canvas.drawWithOffset(
        offset: Offset,
        block: Canvas.() -> Unit
    ) {
        save()
        translate(offset.x, offset.y)
        block()
        restore()
    }
}