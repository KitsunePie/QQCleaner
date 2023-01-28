package me.kyuubiran.qqcleaner.page

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.HomeFragmentBinding
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.navigatePage

class HomeFragment : BaseFragment() {

    private val binding get() = _binding!! as HomeFragmentBinding

    private val model: MainActivityStates by activityViewModels()

    private val state: HomeStates by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            model.theme.collect {
                binding.root.background = ColorDrawable(it.appBarsAndItemBackgroundColor)
                binding.time.setTextColor(it.secondTextColor)
                binding.lastTime.setTextColor(it.secondTextColor)

                binding.lastTimeSub.apply {
                    val background = GradientDrawable()
                    background.setColor(it.mainThemeColor)
                    background.cornerRadius = 4.dp
                    setTextColor(it.whiteColor)
                    setBackground(background)
                }

                binding.cleanerBtn.setLayoutBackground(it.mainThemeColor)
                binding.cleanerBtn.setShadowColor(it.sixtyThreePercentThemeColor)
            }
        }

        // 设置按钮状态
        lifecycleScope.launch {
            state.cleanerStateFlow.collect {
                binding.autoCleaner.setSwitchChecked(it)
            }
        }
    }

    private fun intoClickListener() {
        // 接受按钮当前状态
        binding.autoCleaner.setSwitchListener {
            state.viewModelScope.launch {
                state.cleanerStateFlow.emit(it)
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else insets.systemWindowInsetBottom
            }
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class HomeStates : StateHolder() {
        val cleanerStateFlow = MutableStateFlow(false)
    }

}