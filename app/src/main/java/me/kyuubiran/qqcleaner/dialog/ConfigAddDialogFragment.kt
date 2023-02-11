package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import group.infotech.drawable.dsl.shapeDrawable
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.databinding.ConfigAddDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable
class ConfigAddDialogFragment : EditDialogFragment(), ThemeFragmentRegistry {

    private val state: ConfigStates by viewModels()

    lateinit var binding: ConfigAddDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ConfigAddDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initFragment()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun initColor() {
        lifecycleScope.launch {
            model.colorPalette.collect {
                binding.topBar.apply {
                    setTitleColor(it.firstTextColor)
                    setIconColor(it.firstTextColor)
                    setIconRippleColor(it.rippleColor)
                }

                binding.topDivider.setBackgroundColor(it.dividerColor)

                binding.cilpboardItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }
                binding.fileItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }
                binding.addItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }

                binding.defaultItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }

                binding.select.apply {
                    setTextColor(it.mainThemeColor)
                    background = rippleDrawable(
                        it.fourPercentThemeColor,
                        shapeDrawable {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 10.dp
                            setColor(it.fourPercentThemeColor)
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
    }

    override fun initListener() {
        binding.cilpboardItem.setOnClickListener { }

        binding.fileItem.setOnClickListener { }

        binding.addItem.setOnClickListener { }

        binding.defaultItem.setOnClickListener { }

        binding.select.setOnClickListener { }
    }

    class ConfigStates : StateHolder() {

    }


}