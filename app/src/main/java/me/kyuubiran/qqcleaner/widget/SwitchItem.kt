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
import me.kyuubiran.qqcleaner.R.drawable.switch_default_off_to_on
import me.kyuubiran.qqcleaner.R.drawable.switch_default_on_to_off
import me.kyuubiran.qqcleaner.R.styleable.Item_text
import me.kyuubiran.qqcleaner.databinding.ItemSwitchBinding


class SwitchItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    public var checked = false

    private var isRun = false

    private lateinit var text: String

    init {
        initAttrs(attr)
        val binding = ItemSwitchBinding.inflate(
            LayoutInflater.from(getContext()),
            this,
            true
        )
        binding.switchText.text = text
        binding.switchImg.showAnimate(checked)
        this.setOnClickListener {
            if (!isRun) {
                checked = !checked
                isRun = true
                binding.switchImg.showAnimate(checked)
            }
        }
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Item)
        text = typedArray.getString(Item_text).toString()
        typedArray.recycle()
    }


    fun getAnimateImageRes(on: Boolean): Int{

        return if (on)
            switch_default_on_to_off
        else
            switch_default_off_to_on
    }
    private fun ImageView.showAnimate(on: Boolean) {
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