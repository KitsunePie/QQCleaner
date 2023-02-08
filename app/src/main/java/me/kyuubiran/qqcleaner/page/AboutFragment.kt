package me.kyuubiran.qqcleaner.page

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.AboutFragmentBinding
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class AboutFragment : BaseFragment<AboutFragmentBinding>(AboutFragmentBinding::inflate),
    ThemeFragmentRegistry {

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


                binding.aboutText.setTextColor(it.firstTextColor)

                binding.aboutVersion.setTextColor(it.secondTextColor)
                binding.githubItem.apply {
                    setRippleColor(it.rippleColor, it.appBarsAndItemBackgroundColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }

                binding.tgBackground.setBackgroundColor(it.appBarsAndItemBackgroundColor)

                binding.tgChannelItem.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }

                binding.tgGroupItem.apply {
                    setRippleColor(it.rippleColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }

                binding.developerItem.apply {
                    setRippleColor(it.rippleColor, it.appBarsAndItemBackgroundColor)
                    setTextColor(it.secondTextColor)
                    setIconColor(it.itemRightIconColor)
                }
            }
        }
    }

    override fun initDrawable() {
        lifecycleScope.launch {
            model.colorPalette.collect {
                binding.aboutLogo.setImageResource(
                    if (it == LightColorPalette)
                        R.drawable.ic_home_qqcleaner
                    else
                        R.drawable.ic_home_qqcleaner_dark
                )
            }
        }
    }

    override fun initLayout() {
        binding.aboutVersion.text = getVersionName()
    }

    override fun initListener() {
        binding.githubItem.setOnClickListener {
            openUrlInBrowser("https://github.com/KitsunePie/QQCleaner")
        }
        binding.tgChannelItem.setOnClickListener {
            openUrlInBrowser("https://t.me/QQCleaner")
        }
        binding.tgGroupItem.setOnClickListener {
            openUrlInBrowser("https://t.me/QQCleanerChat")
        }
        binding.developerItem.setOnClickListener {
            navigatePage(R.id.action_aboutFragment_to_developerFragment)
        }
    }

    /**
     * 获得 versionName
     * @return
     */
    @Suppress("DEPRECATION")
    private fun getVersionName(): String {
        var versionName = ""
        try {
            val packageName = this.requireContext().packageName
            this.requireContext().packageManager.apply {
                versionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0L))
                } else {
                    getPackageInfo(packageName, 0)
                }.versionName
            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }
}
