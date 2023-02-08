package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.databinding.DeveloperFragmentBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class DeveloperFragment :
    BaseFragment<DeveloperFragmentBinding>(DeveloperFragmentBinding::inflate),
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
                    setRippleColor(it.rippleColor)
                    setTextColor(it.thirdTextColor)
                    setIconColor(it.itemRightIconColor)
                    setNameColor(it.secondTextColor)
                }

            }
        }
    }

    override fun initListener() {
        binding.devOrg.setOnClickListener {
            openUrlInBrowser("https://github.com/KitsunePie")
        }
        binding.developerKyuubiran.setOnClickListener {
            openUrlInBrowser("https://github.com/KyuubiRan")
        }
        binding.developerKetal.setOnClickListener {
            openUrlInBrowser("https://github.com/keta1")
        }
        binding.developerNextalone.setOnClickListener {
            openUrlInBrowser("https://github.com/NextAlone")
        }

        binding.developerAgoines.setOnClickListener {
            openUrlInBrowser("https://github.com/Agoines")
        }

        binding.developerMaitungtm.setOnClickListener {
            openUrlInBrowser("https://github.com/Lagrio")
        }
    }
}