package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ItemChevronBinding

class ChevronItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {


    private lateinit var text: String

    init {
        initAttrs(attr)
        val binding = ItemChevronBinding.inflate(
            LayoutInflater.from(getContext()),
            this,
            true
        )
        binding.chevronText.text = text
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Item)
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
    }
}