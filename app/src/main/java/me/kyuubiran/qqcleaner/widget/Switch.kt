package me.kyuubiran.qqcleaner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import me.kyuubiran.qqcleaner.R

@SuppressLint("AppCompatCustomView")
class Switch(context: Context, attr: AttributeSet) : ImageView(context, attr) {

    private var isRun = false
    fun setChecked(on: Boolean, isWhite: Boolean, isDark: Boolean, hasAnim: Boolean) {
        this.contentDescription = getContentDescriptionRes(on)
        if (hasAnim) {
            if (!isRun) showAnimate(on, isWhite, isDark)
        } else {
            setImageResource(getAnimateImageRes(!on, isWhite, isDark))
        }

    }

    private fun getContentDescriptionRes(on: Boolean): String {
        return context.getString(
            if (on)
                R.string.switch_on_tip
            else
                R.string.switch_off_tip
        )
    }

    fun getAnimateImageRes(on: Boolean, isWhite: Boolean, isDark: Boolean): Int {
        return if (on)
            if (isDark)
                if (isWhite)
                    R.drawable.switch_on_to_off_white_dark
                else
                    R.drawable.switch_default_on_to_off_dark
            else
                if (isWhite)
                    R.drawable.switch_on_to_off_white
                else
                    R.drawable.switch_default_on_to_off
        else
            if (isDark)
                if (isWhite)
                    R.drawable.switch_off_to_on_white_dark
                else
                    R.drawable.switch_default_off_to_on_dark
            else
                if (isWhite)
                    R.drawable.switch_off_to_on_white
                else
                    R.drawable.switch_default_off_to_on

    }

    private fun showAnimate(on: Boolean, isWhite: Boolean, isDark: Boolean) {
        val animateRes = getAnimateImageRes(on, isWhite, isDark)
        setImageResource(animateRes)
        AnimatedVectorDrawableCompat.clearAnimationCallbacks(drawable)
        AnimatedVectorDrawableCompat.registerAnimationCallback(
            drawable,
            object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    AnimatedVectorDrawableCompat.clearAnimationCallbacks(drawable)
                    isRun = false
                    setImageResource(getAnimateImageRes(!on, isWhite, isDark))
                }
            })
        (drawable as AnimatedVectorDrawable).start()
    }

}