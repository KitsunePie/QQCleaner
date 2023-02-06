package me.kyuubiran.qqcleaner.dialog


import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.TextViewCompat.setCompoundDrawableTintList
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ThemeDialogBinding
import me.kyuubiran.qqcleaner.theme.Theme
import me.kyuubiran.qqcleaner.theme.Theme.Type.AUTO_THEME
import me.kyuubiran.qqcleaner.theme.Theme.Type.DARK_THEME
import me.kyuubiran.qqcleaner.theme.Theme.Type.LIGHT_THEME
import me.kyuubiran.qqcleaner.uitls.dpInt


class ThemeDialog(states: MainActivityStates) : BaseDialog(states) {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ThemeDialogBinding.inflate(layoutInflater)
        layout = binding.root
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

            states.appTheme.collect {

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

        lifecycleScope.launch {

            states.colorPalette.collect {
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

        binding.lightTheme.setOnClickListener {
            states.setTheme(LIGHT_THEME, false, requireContext())
        }
        binding.darkTheme.setOnClickListener {
            states.setTheme(Theme.Type.DARK_THEME, false, requireContext())
        }
        binding.followSystemTheme.setOnClickListener {
            states.setTheme(Theme.Type.AUTO_THEME, false, requireContext())
        }

        binding.blackTheme.setOnClickListener {
            states.setTheme(Theme.Type.DARK_THEME, true, requireContext())
        }

        return super.onCreateDialog(savedInstanceState)
    }

}