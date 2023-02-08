package me.kyuubiran.qqcleaner.page

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.AboutFragmentBinding
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.navigatePage


class AboutFragment : BaseFragment() {
    private val binding get() = _binding!! as AboutFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AboutFragmentBinding.inflate(inflater, container, false)

        intoLayout()


        binding.aboutVersion.text = getVersionName()

        binding.githubItem.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(requireContext(), Uri.parse("https://github.com/KitsunePie/QQCleaner"))
        }
        binding.tgChannelItem.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(requireContext(), Uri.parse("https://t.me/QQCleaner"))
        }
        binding.tgGroupItem.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(requireContext(), Uri.parse("https://t.me/QQCleanerChat"))
        }
        binding.developerItem.setOnClickListener {
            navigatePage(R.id.action_aboutFragment_to_developerFragment)
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


                binding.aboutLogo.setImageResource(
                    if (it == LightColorPalette)
                        R.drawable.ic_home_qqcleaner
                    else
                        R.drawable.ic_home_qqcleaner_dark
                )

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
