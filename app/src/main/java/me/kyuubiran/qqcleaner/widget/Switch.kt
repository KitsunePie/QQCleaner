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
    fun setChecked(on: Boolean, hasAnim: Boolean) {
        this.contentDescription = getContentDescriptionRes(on)
        if (hasAnim) {
            if (!isRun) showAnimate(on)
        } else {
            setImageResource(getAnimateImageRes(!on))
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

    fun getAnimateImageRes(on: Boolean): Int {
        return if (on)
            R.drawable.switch_default_on_to_off
        else
            R.drawable.switch_default_off_to_on
    }

    private fun showAnimate(on: Boolean) {
        val animateRes = getAnimateImageRes(on)
        setImageResource(animateRes)
        AnimatedVectorDrawableCompat.clearAnimationCallbacks(drawable)
        AnimatedVectorDrawableCompat.registerAnimationCallback(
            drawable,
            object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    AnimatedVectorDrawableCompat.clearAnimationCallbacks(drawable)
                    isRun = false
                    setImageResource(getAnimateImageRes(!on))
                }
            })
        (drawable as AnimatedVectorDrawable).start()
    }

}