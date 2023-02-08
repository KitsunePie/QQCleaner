package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.EditFragmentBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class EditFragment : BaseFragment<EditFragmentBinding>(EditFragmentBinding::inflate),
    ThemeFragmentRegistry {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()

    }

    override fun initColor() {
        lifecycleScope.launch {
            model.colorPalette.collect {

                binding.root.setBackgroundColor(it.pageBackgroundColor)
                binding.toolBar.apply {
                    setIconRippleColor(it.rippleColor)
                    setTitleColor(it.firstTextColor)
                    setIconColor(it.firstTextColor)
                }
            }
        }
    }

    override fun initListener() {
        binding.addSortBtn.setOnClickListener {
            navigatePage(R.id.action_editFragment_to_fileFragment)
        }
    }
}