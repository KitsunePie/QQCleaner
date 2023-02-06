package me.kyuubiran.qqcleaner.uitls

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import group.infotech.drawable.dsl.ColorInt

/**
 *  com.github.infotech-group:android-drawable-dsl 不支持 content 为 null
 *  所以新增了这样的一个函数
 */
fun rippleDrawable(color: ColorInt, content: Drawable? = null, mask: Drawable? = null): Drawable =
    RippleDrawable(ColorStateList.valueOf(color), content, mask)
