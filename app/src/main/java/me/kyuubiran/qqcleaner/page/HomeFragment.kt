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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.HomeFragmentBinding
import me.kyuubiran.qqcleaner.uitls.dp

class HomeFragment : BaseFragment() {

    private val binding get() = _binding!! as HomeFragmentBinding
    private val model: MainActivityStates by activityViewModels()

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

        // 配置按钮
        binding.configChevrItem.setOnClickListener {
            val navController = findNavController(this)
            val bundle = Bundle()
            bundle.putString("subFragment", "config")
            navController.navigate(R.id.action_homeFragment_to_moduleFragment, bundle)
        }

        // 关于页面
        binding.aboutChevrItem.setOnClickListener {
            val navController = findNavController(this)
            val bundle = Bundle()
            bundle.putString("subFragment", "about")
            navController.navigate(R.id.action_homeFragment_to_moduleFragment, bundle)
        }

        return binding.root
    }
    @Suppress("DEPRECATION")
    private fun setOnApplyWindowInsetsListener(){
        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            binding.topBar.updateLayoutParams {
                (this as RelativeLayout.LayoutParams).topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else insets.systemWindowInsetTop
            }
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

    private class HomeStates() : StateHolder() {
        val cleanerStateFlow = MutableStateFlow(false)
    }

}