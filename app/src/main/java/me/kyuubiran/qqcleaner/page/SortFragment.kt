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
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.adapter.SortAdapter
import me.kyuubiran.qqcleaner.data.ConfigContent
import me.kyuubiran.qqcleaner.databinding.SortFragmentBinding
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.checkDeviceHasNavigationBar
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.getNavigationBarHeight

class SortFragment : BaseFragment<SortFragmentBinding>(SortFragmentBinding::inflate),
    ThemeFragmentRegistry {

    private val configState: ConfigFragment.ConfigStates by activityViewModels()
    private val state: SortStates by activityViewModels()
    private lateinit var adapter: SortAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    override fun initLayout() {
        super.initLayout()
        binding.toolBar.title = configState.selectConfig.title
        adapter = SortAdapter(
            model = model,
            sortState = state,
            dataSet = configState.selectConfig.content
        )
        val sortRecyclerView = binding.sortRecyclerView

        sortRecyclerView.setHasFixedSize(true)
        sortRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        sortRecyclerView.adapter = adapter

        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            binding.sortLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else if (checkDeviceHasNavigationBar()) getNavigationBarHeight() else 0) + 24.dpInt
            }
            // 设置按钮边距
            binding.addSortBtn.updateLayoutParams<ViewGroup.MarginLayoutParams> {
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
                binding.sortLayout.setBackgroundColor(colors.appBarsAndItemBackgroundColor)

                binding.configSwitch.apply {
                    setTextColor(colors.whiteColor)
                    setSwitchColor(true, colors != LightColorPalette)
                    setRippleColor(colors.whiteColor, colors.mainThemeColor)


                    setSwitchListener {
                        setTextColor(if (it) colors.firstTextColor else colors.whiteColor)
                        setSwitchColor(!it, colors != LightColorPalette)
                        setRippleColor(
                            if (it) colors.rippleColor else colors.whiteColor,
                            if (it) colors.whiteColor else colors.mainThemeColor
                        )

                    }
                }
                binding.addSortBtn.setLayoutBackground(colors.mainThemeColor)
                binding.addSortBtn.setLayoutBackgroundTrue(colors.whiteColor)
                binding.addSortBtn.setShadowColor(colors.sixtyThreePercentThemeColor)
            }
        }
    }

    override fun initListener() {
        binding.addSortBtn.setOnClickListener {
            navigatePage(R.id.action_sortFragment_to_fileFragment)
        }
    }

    class SortStates : StateHolder() {
        lateinit var configContent: ConfigContent
    }
}