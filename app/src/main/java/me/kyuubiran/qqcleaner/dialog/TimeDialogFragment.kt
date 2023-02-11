package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import group.infotech.drawable.dsl.shapeDrawable
import group.infotech.drawable.dsl.solidColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.databinding.TimeDialogBinding
import me.kyuubiran.qqcleaner.page.HomeFragment
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.setTextCursorDrawableColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleLeftColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleRightColor


class TimeDialogFragment : EditDialogFragment(),
    ThemeFragmentRegistry {

    private val state: TimeStates by viewModels()

    // 与 HomeFragment 共享一个 ViewModels
    private val homeStates: HomeFragment.HomeStates by activityViewModels()
    lateinit var binding: TimeDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 把当前值发送给保存起来

        binding = TimeDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initFragment()

        return super.onCreateDialog(savedInstanceState)
    }


    override fun initLayout() {
        lifecycleScope.launch {
            state.tempTime.emit(homeStates.autoCleanerTimeState.value)
        }
        homeStates.autoCleanerTimeState.value.apply {
            binding.timeEdit.setText(if (this == 24) "" else this.toString())

        }

    }

    override fun initListener() {
        binding.timeEdit.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch {
                state.tempTime.emit(
                    if (text.isNullOrEmpty()) 24 else text.toString().toInt()

                )
            }
        }

        binding.topBar.setIconOnClickListener {
            animateDismiss()
        }
        lifecycleScope.launch {
            state.tempTime.combine(homeStates.autoCleanerTimeState) { new, old -> new != old }
                .collect {
                    binding.timeSelect.modify = it
                }
        }

        binding.timeSelect.setOnClickListener {
            homeStates.setAutoCleanerTime(requireContext(), state.tempTime.value)
        }
    }

    override fun initColor() {
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