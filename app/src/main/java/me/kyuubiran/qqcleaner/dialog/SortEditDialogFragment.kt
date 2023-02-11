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
import me.kyuubiran.qqcleaner.databinding.SortEditDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

class SortEditDialogFragment : EditDialogFragment(), ThemeFragmentRegistry {

    private val state: SortStates by viewModels()

    lateinit var binding: SortEditDialogBinding

    companion object {
        fun newInstance(title: String): SortEditDialogFragment {
            val args = Bundle().apply {
                putString("title", title)
            }
            return SortEditDialogFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = SortEditDialogBinding.inflate(layoutInflater)
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
        binding.editItem.setOnClickListener {
            val fragment =
                (requireContext() as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
            val navController = NavHostFragment.findNavController(fragment)
            navController.navigate(
                R.id.action_sortFragment_to_fileFragment
            )
            animateDismiss()
        }
        binding.editNameItem.setOnClickListener {

        }

        binding.deleteItem.setOnClickListener {

        }

        binding.select.setOnClickListener {

        }
    }

    class SortStates : StateHolder() {

    }


}