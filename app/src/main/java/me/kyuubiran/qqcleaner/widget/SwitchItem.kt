package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ItemSwitchBinding


class SwitchItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    public var checked = false

    private var isRun = false

    init {
        val binding = ItemSwitchBinding.inflate(
            LayoutInflater.from(getContext()),
            this,
            true
        )
        binding.switchImg.showAnimate(checked)

        this.setOnClickListener {
            if (!isRun) {
                checked = !checked
                isRun = true
                binding.switchImg.showAnimate(checked)
            }


        }
    }

    private fun ImageView.showAnimate(on: Boolean) {
        val animateRes =
            if (on)
                R.drawable.switch_default_on_to_off
            else
                R.drawable.switch_default_off_to_on
        setImageResource(animateRes)
        AnimatedVectorDrawableCompat.clearAnimationCallbacks(drawable)
        AnimatedVectorDrawableCompat.registerAnimationCallback(
            drawable,
            object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    AnimatedVectorDrawableCompat.clearAnimationCallbacks(drawable)
                    isRun = false
                    val drawableRes =
                        if (on) R.drawable.switch_default_off_to_on else R.drawable.switch_default_on_to_off
                    setImageResource(drawableRes)
                }
            })
        (drawable as AnimatedVectorDrawable).start()
    }

}