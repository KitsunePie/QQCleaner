package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import group.infotech.drawable.dsl.shapeDrawable
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ConfigEditDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

class ConfigEditDialogFragment : EditDialogFragment(), ThemeFragmentRegistry {

    private val state: ConfigEditStates by viewModels()

    lateinit var binding: ConfigEditDialogBinding

    companion object {
        fun newInstance(title: String): ConfigEditDialogFragment {
            val args = Bundle().apply {
                putString("title", title)
            }
            return ConfigEditDialogFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ConfigEditDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initFragment()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun initLayout() {
        super.initLayout()
        if (arguments != null) {
            binding.topBar.setTitle(requireArguments().getString("title")!!)
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

                binding.topDivider.setBackgroundColor(it.dividerColor)

                binding.cilpboardItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }
                binding.openItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }
                binding.editItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }

                binding.editNameItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }

                binding.saveItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }

                binding.cilpboardItem.apply {
                    setRippleColor(it.rippleColor)
                    setDrawableColor(it.firstTextColor)
                    setTextColor(it.firstTextColor)
                }

                binding.deleteItem.apply {
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

        binding.openItem.setOnClickListener { }

        binding.editItem.setOnClickListener {
            val fragment =
                (requireContext() as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
            val navController = NavHostFragment.findNavController(fragment)
            navController.navigate(
                R.id.action_configFragment_to_sortFragment
            )
            animateDismiss()
        }

        binding.editNameItem.setOnClickListener { }

        binding.saveItem.setOnClickListener { }

        binding.cilpboardItem.setOnClickListener { }

        binding.deleteItem.setOnClickListener { }

        binding.select.setOnClickListener { }
    }

    class ConfigEditStates : StateHolder() {

    }


}