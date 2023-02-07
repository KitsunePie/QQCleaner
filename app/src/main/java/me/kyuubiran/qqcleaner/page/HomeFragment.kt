package me.kyuubiran.qqcleaner.page

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import group.infotech.drawable.dsl.shapeDrawable
import group.infotech.drawable.dsl.solidColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.HomeFragmentBinding
import me.kyuubiran.qqcleaner.dialog.ThemeDialog
import me.kyuubiran.qqcleaner.dialog.TimeDialog
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.AUTO_CLEANER_TIME
import me.kyuubiran.qqcleaner.uitls.IS_AUTO_CLEANER
import me.kyuubiran.qqcleaner.uitls.checkDeviceHasNavigationBar
import me.kyuubiran.qqcleaner.uitls.dataStore
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.editData
import me.kyuubiran.qqcleaner.uitls.getNavigationBarHeight
import me.kyuubiran.qqcleaner.uitls.navigatePage

class HomeFragment : BaseFragment() {

    private val binding get() = _binding!! as HomeFragmentBinding

    private val state: HomeStates by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state.initViewModel(this.requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        setOnApplyWindowInsetsListener()
        intoLayout()
        intoClickListener()
        return binding.root
    }

    private fun intoLayout() {
        // 设置主题颜色
        lifecycleScope.launch {
            model.colorPalette.collect {
                binding.root.background = ColorDrawable(it.appBarsAndItemBackgroundColor)
                binding.time.setTextColor(it.secondTextColor)
                binding.lastTime.setTextColor(it.secondTextColor)

                binding.lastTimeSub.apply {
                    setTextColor(it.whiteColor)
                    this.background = shapeDrawable {
                        shape = GradientDrawable.RECTANGLE
                        solidColor = it.mainThemeColor
                        cornerRadius = 4.dp
                    }
                }


                binding.qqcleanerIcon.setImageResource(
                    if (it == LightColorPalette)
                        R.drawable.ic_home_qqcleaner
                    else
                        R.drawable.ic_home_qqcleaner_dark
                )

                binding.mShadowLayout.apply {
                    setLayoutBackground(it.pageBackgroundColor)
                    setShadowColor(it.rippleColor)
                }

                binding.titleSetup.setTextColor(it.secondTextColor)
                binding.setupLayout.setBackgroundColor(it.appBarsAndItemBackgroundColor)
                binding.autoCleaner.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setSwitchColor(false, it != LightColorPalette)
                }
                binding.autoCleanerText.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setTipTextColor(it.itemRightTextColor)
                }

                binding.configChevrItem.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }

                binding.titleMore.setTextColor(it.secondTextColor)
                binding.moreLayout.setBackgroundColor(it.appBarsAndItemBackgroundColor)
                binding.themeChevrItem.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }
                binding.aboutChevrItem.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }

                // todo 这个最后应该使用自定 ViewGroup 完成
                binding.cleanerBtn.setLayoutBackground(it.mainThemeColor)
                binding.cleanerBtn.setLayoutBackgroundTrue(it.whiteColor)
                binding.cleanerBtn.setShadowColor(it.sixtyThreePercentThemeColor)

            }
        }

        // 设置按钮状态
        lifecycleScope.launch {
            state.autoCleanerState.collect {
                binding.autoCleaner.setSwitchChecked(it)
            }
        }

        lifecycleScope.launch {
            state.autoCleanerTimeState.collect {
                binding.autoCleanerText.apply {
                    setTipText(if (it != 24) it.toString() else defaultText)
                }
            }
        }
    }

    private fun intoClickListener() {
        // 接受按钮当前状态
        binding.autoCleaner.setSwitchListener {
            state.viewModelScope.launch {
                state.setAutoCleaner(this@HomeFragment.requireContext(), it)
            }
        }

        binding.autoCleanerText.setOnClickListener {
            TimeDialog(model, state.autoCleanerTimeState.value).show(parentFragmentManager, "")
        }
        // 配置按钮
        binding.configChevrItem.setOnClickListener {
            navigatePage(R.id.action_homeFragment_to_configFragment)
        }
        binding.themeChevrItem.setOnClickListener {
            ThemeDialog(model).show(parentFragmentManager, "")
        }
        // 关于页面
        binding.aboutChevrItem.setOnClickListener {
            navigatePage(R.id.action_homeFragment_to_aboutFragment)
        }


    }

    @Suppress("DEPRECATION")
    private fun setOnApplyWindowInsetsListener() {
        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            // 设置顶栏边距
            binding.topBar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else insets.systemWindowInsetTop
            }
            // 设置按钮边距
            binding.cleanerBtn.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else if (checkDeviceHasNavigationBar()) getNavigationBarHeight() else 0) + 24.dpInt
            }
            insets
        }
    }

    /**
     * 存储持久化状态
     */
    class HomeStates : StateHolder() {
        // 是否自动清理
        lateinit var autoCleanerState: StateFlow<Boolean>

        // 自动清理的间隔时间
        lateinit var autoCleanerTimeState: StateFlow<Int>
        fun initViewModel(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                autoCleanerTimeState = context.dataStore.data.map { preferences ->
                    preferences[AUTO_CLEANER_TIME] ?: 24
                }.stateIn(this)

                autoCleanerState = context.dataStore.data.map { preferences ->
                    preferences[IS_AUTO_CLEANER] ?: false
                }.stateIn(this)
            }
        }

        fun setAutoCleanerTime(context: Context, time: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                context.editData(AUTO_CLEANER_TIME, time)
            }
        }

        fun setAutoCleaner(context: Context, boolean: Boolean) {
            viewModelScope.launch(Dispatchers.IO) {
                context.editData(IS_AUTO_CLEANER, boolean)
            }
        }

    }

}