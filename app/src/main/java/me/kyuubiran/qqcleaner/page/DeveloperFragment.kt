package me.kyuubiran.qqcleaner.page

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.databinding.DeveloperFragmentBinding

class DeveloperFragment : BaseFragment() {

    private val binding get() = _binding!! as DeveloperFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeveloperFragmentBinding.inflate(inflater, container, false)
        intoLayout()

        binding.devOrg.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(requireContext(), Uri.parse("https://github.com/KitsunePie"))
        }
        binding.developerKyuubiran.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(requireContext(), Uri.parse("https://github.com/KyuubiRan"))
        }
        binding.developerKetal.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://github.com/keta1"))
        }
        binding.developerNextalone.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://github.com/NextAlone"))
        }

        binding.developerAgoines.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://github.com/Agoines"))
        }

        binding.developerMaitungtm.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://github.com/Lagrio"))
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

                binding.devOrg.apply {
                    setRippleColor(it.rippleColor, it.appBarsAndItemBackgroundColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }

                binding.devList.setBackgroundColor(it.appBarsAndItemBackgroundColor)

                binding.developerKyuubiran.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }


                binding.developerKetal.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }

                binding.developerNextalone.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }

                binding.developerAgoines.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }

                binding.developerMaitungtm.apply {
                    setRippleColor(it.rippleColor, it.appBarsAndItemBackgroundColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }

            }
        }
    }
}