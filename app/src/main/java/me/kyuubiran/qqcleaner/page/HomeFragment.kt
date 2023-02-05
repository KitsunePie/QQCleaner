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
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.HomeFragmentBinding
import me.kyuubiran.qqcleaner.dialog.ThemeDialog
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.AUTO_CLEANER_TIME
import me.kyuubiran.qqcleaner.uitls.IS_AUTO_CLEANER
import me.kyuubiran.qqcleaner.uitls.dataStore
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.editData
import me.kyuubiran.qqcleaner.uitls.navigatePage

class HomeFragment : BaseFragment() {

    private val binding get() = _binding!! as HomeFragmentBinding

    private val model: MainActivityStates by activityViewModels()

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
        ThemeDialog(model).show(parentFragmentManager, "")
        return binding.root
    }

    private fun intoLayout() {
        // 设置主题颜色
        lifecycleScope.launch {
            model.theme.collect {
                binding.root.background = ColorDrawable(it.appBarsAndItemBackgroundColor)
                binding.time.setTextColor(it.secondTextColor)
                binding.lastTime.setTextColor(it.secondTextColor)

                binding.lastTimeSub.apply {
                    setTextColor(it.whiteColor)
                    this.background = GradientDrawable().apply {
                        setColor(it.mainThemeColor)
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
                    setTextColor(it.secondTextColor)
                    setSwitchColor(false, it != LightColorPalette)
                }
                binding.autoCleanerText.apply {
                    setTextColor(it.secondTextColor)
                    setTipTextColor(it.itemRightTextColor)
                }
                binding.configChevrItem.apply {
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }

                binding.titleMore.setTextColor(it.secondTextColor)
                binding.moreLayout.setBackgroundColor(it.appBarsAndItemBackgroundColor)
                binding.themeChevrItem.apply {
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }
                binding.aboutChevrItem.apply {
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
    }

    private fun intoClickListener() {
        // 接受按钮当前状态
        binding.autoCleaner.setSwitchListener {
            state.viewModelScope.launch {
                state.setAutoCleaner(this@HomeFragment.requireContext(), it)
            }
        }

        // 配置按钮
        binding.configChevrItem.setOnClickListener {
            navigatePage(R.id.action_homeFragment_to_configFragment)
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
            binding.topBar.updateLayoutParams {
                (this as RelativeLayout.LayoutParams).topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else insets.systemWindowInsetTop
            }
            // 设置按钮边距
            binding.cleanerBtn.updateLayoutParams {
                (this as RelativeLayout.LayoutParams).bottomMargin =
                    ((if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else insets.systemWindowInsetBottom) + 24.dp(requireContext())).toInt()
            }
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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