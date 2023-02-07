package me.kyuubiran.qqcleaner.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import group.infotech.drawable.dsl.shapeDrawable
import group.infotech.drawable.dsl.solidColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.databinding.TimeDialogBinding
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.setTextCursorDrawableColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleLeftColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleRightColor


class TimeDialog(model: MainActivity.MainActivityStates, private val time: Int) :
    EditDialog(model) {

    private val state: TimeStates by viewModels()

    lateinit var binding: TimeDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 把当前值发送给保存起来

        binding = TimeDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initColor()

        lifecycleScope.launch {
            state.tempTime.emit(time)
        }
        binding.timeEdit.setText(if (time == 24) "" else time.toString())

        binding.timeEdit.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch {
                state.tempTime.emit(
                    if (text.isNullOrEmpty()) 24 else text.toString().toInt()

                )
            }

        }

        lifecycleScope.launch {
            state.tempTime.collect {
                binding.timeSelect.modify = time != it
            }
        }

        return super.onCreateDialog(savedInstanceState)
    }


    @SuppressLint("SoonBlockedPrivateApi")
    private fun initColor() {
        lifecycleScope.launch {
            model.colorPalette.collect {
                binding.topBar.apply {
                    setTitleColor(it.firstTextColor)
                    setIconColor(it.firstTextColor)
                    setIconRippleColor(it.rippleColor)
                }
                binding.timeEdit.apply {
                    background = shapeDrawable {
                        shape = GradientDrawable.RECTANGLE
                        solidColor = it.typeBoxBackgroundColor
                        cornerRadius = 10.dp
                    }
                    setTextColor(it.secondTextColor)
                    setHintTextColor(it.disableSecondTextColor)

                    highlightColor = it.thirtyEightPercentThemeColor

                    setTextSelectHandleRightColor(it.mainThemeColor)

                    setTextSelectHandleLeftColor(it.mainThemeColor)
                    setTextSelectHandleColor(it.mainThemeColor)
                    setTextCursorDrawableColor(it.mainThemeColor)
                }

                binding.timeSelect.apply {
                    buttonTextColor = it.thirtyEightPercentThemeColor
                    buttonTextModifyColor = it.mainThemeColor
                    buttonBackgroundColor = it.twoPercentThemeColor
                    buttonBackgroundModifyColor = it.fourPercentThemeColor
                    buttonRippleColor = it.fourPercentThemeColor
                    // 触发一次颜色的刷新
                    modifyBackground()
                }

            }
        }
    }

    class TimeStates : StateHolder() {
        var tempTime = MutableStateFlow(0)
    }

}