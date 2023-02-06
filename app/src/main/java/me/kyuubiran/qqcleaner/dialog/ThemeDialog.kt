package me.kyuubiran.qqcleaner.dialog


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import group.infotech.drawable.dsl.shapeDrawable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ThemeDialogBinding
import me.kyuubiran.qqcleaner.theme.Theme
import me.kyuubiran.qqcleaner.theme.Theme.Type.AUTO_THEME
import me.kyuubiran.qqcleaner.theme.Theme.Type.DARK_THEME
import me.kyuubiran.qqcleaner.theme.Theme.Type.LIGHT_THEME
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.rippleDrawable


class ThemeDialog(activityStates: MainActivityStates) : BaseDialog(activityStates) {

    private val state: ThemeStates by viewModels()
    lateinit var binding: ThemeDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        state.initViewModel(model)
        binding = ThemeDialogBinding.inflate(layoutInflater)
        layout = binding.root


        lifecycleScope.launch {

            model.colorPalette.collect {
                binding.topDivider.setBackgroundColor(it.dividerColor)

                binding.lightTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    setChecked(checked, true)
                }

                binding.darkTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    setChecked(checked, true)
                }

                binding.followSystemTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    setChecked(checked, true)
                }

                binding.blackTheme.apply {
                    itemTextColor = it.secondTextColor
                    itemTextColorPress = it.mainThemeColor
                    itemBackgroundColorPress = it.fourPercentThemeColor
                    setChecked(checked, true)
                }

                binding.bottomDivider.setBackgroundColor(it.dividerColor)

                binding.themeSelect.apply {
                    background = rippleDrawable(
                        it.thirtyEightPercentThemeColor,
                        shapeDrawable {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 10.dp
                            setColor(it.twoPercentThemeColor)
                        },
                        shapeDrawable {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 10.dp
                            setColor(Color.WHITE)
                        }
                    )
                }
            }
        }
        initIcon()
        initOnClick()

        return super.onCreateDialog(savedInstanceState)
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
                    setChecked(it.type == LIGHT_THEME, true)
                    setIconDrawable(sunDrawable)
                }
                binding.darkTheme.apply {

                    setChecked(it.type == DARK_THEME, true)
                    setIconDrawable(moonDrawable)
                }
                binding.followSystemTheme.apply {
                    setChecked(it.type == AUTO_THEME, true)
                    setIconDrawable(androidDrawable)
                }

                binding.blackTheme.apply {
                    setChecked(it.isBlack, true)
                    setIconDrawable(blackDrawable)
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