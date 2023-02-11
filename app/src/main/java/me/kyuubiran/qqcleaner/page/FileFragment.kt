package me.kyuubiran.qqcleaner.page

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.adapter.FileAdapter
import me.kyuubiran.qqcleaner.databinding.FileFragmentBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.checkDeviceHasNavigationBar
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.getNavigationBarHeight

class FileFragment : BaseFragment<FileFragmentBinding>(FileFragmentBinding::inflate),
    ThemeFragmentRegistry {
    private val sortState: SortFragment.SortStates by activityViewModels()
    private lateinit var adapter: FileAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    override fun initLayout() {
        super.initLayout()
        binding.toolBar.title = sortState.configContent.title
        val fileRecyclerView = binding.fileRecyclerView
        adapter = FileAdapter(
            model = model,
            dataSet = sortState.configContent.pathList
        )
        fileRecyclerView.setHasFixedSize(true)
        fileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        fileRecyclerView.adapter = adapter
        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            binding.fileLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else if (checkDeviceHasNavigationBar()) getNavigationBarHeight() else 0) + 24.dpInt
            }
            // 设置按钮边距
            binding.addFileBtn.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else if (checkDeviceHasNavigationBar()) getNavigationBarHeight() else 0) + 24.dpInt
            }
            insets
        }

    }

    override fun initColor() {
        lifecycleScope.launch {
            model.colorPalette.collect { colors ->
                binding.root.setBackgroundColor(colors.pageBackgroundColor)
                binding.toolBar.apply {
                    setIconRippleColor(colors.rippleColor)
                    setTitleColor(colors.firstTextColor)
                    setIconColor(colors.firstTextColor)
                }
                binding.fileLayout.setBackgroundColor(colors.appBarsAndItemBackgroundColor)

                binding.addFileBtn.setLayoutBackground(colors.mainThemeColor)
                binding.addFileBtn.setLayoutBackgroundTrue(colors.whiteColor)
                binding.addFileBtn.setShadowColor(colors.sixtyThreePercentThemeColor)
            }
        }
    }

    override fun initListener() {

    }


}