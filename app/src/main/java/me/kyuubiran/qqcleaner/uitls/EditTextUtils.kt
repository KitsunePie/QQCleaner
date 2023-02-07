package me.kyuubiran.qqcleaner.uitls

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import group.infotech.drawable.dsl.shapeDrawable
import group.infotech.drawable.dsl.size
import group.infotech.drawable.dsl.solidColor
import me.kyuubiran.qqcleaner.R

fun EditText.setTextSelectHandleRightColor(@ColorInt color: Int) {
    val icon = getDrawable(context, R.drawable.text_select_handle_right)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        setTextSelectHandleRight(
            icon!!.apply {
                setTintList(ColorStateList.valueOf(color))
            }
        )
    }
}

fun EditText.setTextSelectHandleLeftColor(@ColorInt color: Int) {
    val icon = getDrawable(context, R.drawable.text_select_handle_left)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        setTextSelectHandleLeft(
            icon!!.apply {
                setTintList(ColorStateList.valueOf(color))
            }
        )
    }
}


fun EditText.setTextSelectHandleColor(@ColorInt color: Int) {
    val icon = getDrawable(context, R.drawable.text_select_handle)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        setTextSelectHandle(
            icon!!.apply {
                setTintList(ColorStateList.valueOf(color))
            }
        )
    }
}

fun EditText.setTextCursorDrawableColor(@ColorInt color: Int) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        textCursorDrawable = shapeDrawable {
            shape = GradientDrawable.RECTANGLE
            solidColor = color
            cornerRadius = 1.dp
            size {
                height = ViewGroup.LayoutParams.MATCH_PARENT
                width = 2.dpInt
            }
        }
    }
}

