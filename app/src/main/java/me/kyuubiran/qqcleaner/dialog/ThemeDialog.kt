package me.kyuubiran.qqcleaner.dialog


import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.TextViewCompat.setCompoundDrawableTintList
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
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
        initIcon()
        setItemBackground()
        lifecycleScope.launch {

            model.colorPalette.collect {
                binding.topDivider.setBackgroundColor(it.dividerColor)
                setCompoundDrawableTintList(
                    binding.lightTheme,
                    ColorStateList.valueOf(it.mainThemeColor)
                )

                setCompoundDrawableTintList(
                    binding.darkTheme,
                    ColorStateList.valueOf(it.mainThemeColor)
                )
                setCompoundDrawableTintList(
                    binding.followSystemTheme,
                    ColorStateList.valueOf(it.mainThemeColor)
                )
                setCompoundDrawableTintList(
                    binding.blackTheme,
                    ColorStateList.valueOf(it.mainThemeColor)
                )
                binding.bottomDivider.setBackgroundColor(it.dividerColor)
            }
        }

        initOnClick()

        return super.onCreateDialog(savedInstanceState)
    }

    private fun initIcon() {
        val chosenDrawable = getDrawable(requireContext(), R.drawable.ic_chosen)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }

        val sunDrawable = getDrawable(requireContext(), R.drawable.ic_sun)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }
        val moonDrawable = getDrawable(requireContext(), R.drawable.ic_sun)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }
        val androidDrawable = getDrawable(requireContext(), R.drawable.ic_sun)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }

        val blackDrawable = getDrawable(requireContext(), R.drawable.ic_a)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }

        lifecycleScope.launch {

            state.tempTheme.collect {

                binding.lightTheme.apply {
                    setCompoundDrawables(
                        sunDrawable,
                        null,
                        if (it.type == LIGHT_THEME) chosenDrawable else null,
                        null
                    )
                }
                binding.darkTheme.apply {
                    setCompoundDrawables(
                        moonDrawable,
                        null,
                        if (it.type == DARK_THEME) chosenDrawable else null,
                        null
                    )
                }
                binding.followSystemTheme.apply {
                    setCompoundDrawables(
                        androidDrawable,
                        null,
                        if (it.type == AUTO_THEME) chosenDrawable else null,
                        null
                    )
                }

                binding.followSystemTheme.apply {
                    setCompoundDrawables(
                        androidDrawable,
                        null,
                        if (it.type == AUTO_THEME) chosenDrawable else null,
                        null
                    )
                }
                binding.blackTheme.apply {
                    setCompoundDrawables(
                        blackDrawable,
                        null,
                        if (it.isBlack) chosenDrawable else null,
                        null
                    )
                }
            }
        }
    }

    private fun setItemBackground() {

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