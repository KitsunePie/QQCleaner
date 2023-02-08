package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.EditFragmentBinding
import me.kyuubiran.qqcleaner.uitls.navigatePage

class EditFragment : BaseFragment() {

    private val binding get() = _binding!! as EditFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditFragmentBinding.inflate(inflater, container, false)
        intoLayout()
        binding.addSortBtn.setOnClickListener {
            navigatePage(R.id.action_editFragment_to_fileFragment)

        }
        return binding.root
    }

    private fun intoLayout() {
        // 设置主题颜色
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
}