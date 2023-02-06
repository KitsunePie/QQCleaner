package me.kyuubiran.qqcleaner.dialog


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ThemeDialogBinding
import me.kyuubiran.qqcleaner.theme.Theme
import me.kyuubiran.qqcleaner.theme.Theme.Type.AUTO_THEME
import me.kyuubiran.qqcleaner.theme.Theme.Type.DARK_THEME
import me.kyuubiran.qqcleaner.theme.Theme.Type.LIGHT_THEME
import me.kyuubiran.qqcleaner.uitls.dpInt


class ThemeDialog(activityStates: MainActivityStates) : BaseDialog(activityStates) {

    private val state: ThemeStates by viewModels()
    lateinit var binding: ThemeDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        state.initViewModel(model)
        binding = ThemeDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initColor()
        initIcon()
        initOnClick()
        return super.onCreateDialog(savedInstanceState)
    }

    private fun initColor() {
        lifecycleScope.launch {

            model.colorPalette.collect {
                binding.topDivider.setBackgroundColor(it.dividerColor)

                binding.lightTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    // 触发一次颜色的刷新
                    showAnimate()
                }

                binding.darkTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    showAnimate()
                }

                binding.followSystemTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    showAnimate()
                }

                binding.blackTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    showAnimate()
                }

                binding.bottomDivider.setBackgroundColor(it.dividerColor)

                binding.themeSelect.apply {
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

    @Keep
    private fun initIcon() {


        val sunDrawable = getDrawable(requireContext(), R.drawable.ic_sun)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }
        val moonDrawable = getDrawable(requireContext(), R.drawable.ic_moon)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }
        val androidDrawable = getDrawable(requireContext(), R.drawable.ic_android)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }

        val blackDrawable = getDrawable(requireContext(), R.drawable.ic_a)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }

        lifecycleScope.launch {

            state.tempTheme.collect {

                binding.lightTheme.apply {
                    checked = it.type == LIGHT_THEME
                    setIconDrawable(sunDrawable)
                }
                binding.darkTheme.apply {
                    checked = it.type == DARK_THEME
                    setIconDrawable(moonDrawable)
                }
                binding.followSystemTheme.apply {
                    checked = it.type == AUTO_THEME
                    setIconDrawable(androidDrawable)
                }

                binding.blackTheme.apply {
                    checked = it.isBlack
                    setIconDrawable(blackDrawable)
                }


            }

        }

        lifecycleScope.launch {

            state.tempTheme.combine(model.appTheme) { a, b -> a != b }
                .collect {
                    binding.themeSelect.apply {
                        modify = it
                    }
                }
        }
    }


    private fun initOnClick() {
        binding.lightTheme.setOnClickListener {
            state.setTheme(LIGHT_THEME)
        }
        binding.darkTheme.setOnClickListener {
            state.setTheme(Theme.Type.DARK_THEME)
        }
        binding.followSystemTheme.setOnClickListener {
            state.setTheme(Theme.Type.AUTO_THEME)
        }

        binding.blackTheme.setOnClickListener {
            state.switchBlackTheme()
        }

        binding.themeSelect.setOnClickListener {
            if (binding.themeSelect.modify)
                state.setActivityTheme(model, requireContext())
        }
    }

    class ThemeStates : StateHolder() {
        lateinit var tempTheme: MutableStateFlow<Theme>
        fun initViewModel(states: MainActivityStates) {
            // 给 temp 为当前值
            viewModelScope.launch {
                tempTheme = MutableStateFlow(states.appTheme.value)
            }
        }

        fun setTheme(theme: Theme.Type) {
            viewModelScope.launch {
                tempTheme.emit(tempTheme.value.copy(value = theme.value))
            }
        }

        fun switchBlackTheme() {
            viewModelScope.launch {
                tempTheme.emit(tempTheme.value.copy(isBlack = !tempTheme.value.isBlack))
            }
        }

        fun setActivityTheme(states: MainActivityStates, context: Context) {
            states.setTheme(this.tempTheme.value, context)
        }
    }

}