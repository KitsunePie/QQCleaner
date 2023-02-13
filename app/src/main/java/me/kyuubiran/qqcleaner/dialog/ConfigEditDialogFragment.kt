package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import group.infotech.drawable.dsl.shapeDrawable
import group.infotech.drawable.dsl.solidColor
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ConfigDeleteDialogBinding
import me.kyuubiran.qqcleaner.databinding.ConfigEditDialogBinding
import me.kyuubiran.qqcleaner.databinding.ConfigEditNameDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable
import me.kyuubiran.qqcleaner.uitls.setTextCursorDrawableColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleLeftColor
import me.kyuubiran.qqcleaner.uitls.setTextSelectHandleRightColor

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

        binding.editNameItem.setOnClickListener {
            // 可以用自定义 View 包装起来，然后对外调用
            baseBinding.dialogLayout.removeView(layout)
            val configEditNameDialog = ConfigEditNameDialogBinding.inflate(layoutInflater)
            lifecycleScope.launch {
                model.colorPalette.collect {
                    configEditNameDialog.topBar.apply {
                        setTitleColor(it.firstTextColor)
                        setIconColor(it.firstTextColor)
                        setIconRippleColor(it.rippleColor)
                    }
                    configEditNameDialog.configNameEdit.apply {
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
                    configEditNameDialog.authorNameEdit.apply {
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
                    configEditNameDialog.select.apply {
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


            layout = configEditNameDialog.root
            baseBinding.dialogLayout.addView(
                layout,
                0,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        binding.saveItem.setOnClickListener { }

        binding.cilpboardItem.setOnClickListener { }

        binding.deleteItem.setOnClickListener {
            // 可以用自定义 View 包装起来，然后对外调用
            baseBinding.dialogLayout.removeView(layout)
            val configDeleteDialog = ConfigDeleteDialogBinding.inflate(layoutInflater)
            lifecycleScope.launch {
                model.colorPalette.collect {
                    configDeleteDialog.topBar.apply {
                        setTitleColor(it.firstTextColor)
                        setIconColor(it.firstTextColor)
                        setIconRippleColor(it.rippleColor)
                    }
                    configDeleteDialog.text.setTextColor(it.secondTextColor)
                    configDeleteDialog.cancel.apply {
                        background = rippleDrawable(
                            it.whiteColor,
                            shapeDrawable {
                                shape = GradientDrawable.RECTANGLE
                                solidColor = it.mainThemeColor
                                cornerRadius = 10.dp
                            },
                            shapeDrawable {
                                shape = GradientDrawable.RECTANGLE
                                solidColor = Color.WHITE
                                cornerRadius = 10.dp
                            }
                        )
                        setTextColor(it.whiteColor)
                    }
                    configDeleteDialog.delConfirm.apply {
                        background = rippleDrawable(
                            it.thirtyEightPercentThemeColor,
                            shapeDrawable {
                                shape = GradientDrawable.RECTANGLE
                                solidColor = it.fourPercentThemeColor
                                cornerRadius = 10.dp
                            },
                            shapeDrawable {
                                shape = GradientDrawable.RECTANGLE
                                solidColor = Color.WHITE
                                cornerRadius = 10.dp
                            }
                        )
                        setTextColor(it.mainThemeColor)
                    }
                }
            }
            layout = configDeleteDialog.root
            baseBinding.dialogLayout.addView(
                layout,
                0,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        binding.select.setOnClickListener { }
    }

    class ConfigEditStates : StateHolder() {

    }


}