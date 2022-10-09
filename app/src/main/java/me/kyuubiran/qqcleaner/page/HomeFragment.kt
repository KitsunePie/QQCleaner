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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.databinding.HomeFragmentBinding
import me.kyuubiran.qqcleaner.theme.DarkColorPalette
import me.kyuubiran.qqcleaner.uitls.dp

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val model: MainActivityStates by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.topBar.setOnApplyWindowInsetsListener { view, insets ->
            view.updateLayoutParams {
                (this as RelativeLayout.LayoutParams).topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else insets.systemWindowInsetTop
            }

            insets
        }

        binding.cleanerBtn.setOnApplyWindowInsetsListener { view, insets ->
            view.updateLayoutParams {
                (this as RelativeLayout.LayoutParams).bottomMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else insets.systemWindowInsetBottom
            }
            insets
        }

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
        binding.cleanerBtn.setOnClickListener {
            model.theme.tryEmit(DarkColorPalette)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    public class HomeStates() : StateHolder() {
        val cleanerStateFlow = MutableStateFlow(false)
    }

}