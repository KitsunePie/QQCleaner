package me.kyuubiran.qqcleaner.page

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.AboutFragmentBinding
import me.kyuubiran.qqcleaner.uitls.navigatePage


class AboutFragment : BaseFragment() {
    private val binding get() = _binding!! as AboutFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AboutFragmentBinding.inflate(inflater, container, false)

        binding.aboutVersion.text = getVersionName()

        binding.githubItem.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://github.com/KitsunePie/QQCleaner"))
        }
        binding.tgChannel.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://t.me/QQCleaner"))
        }
        binding.tgGroup.setOnClickListener {
            CustomTabsIntent.Builder().build()
                .launchUrl(inflater.context, Uri.parse("https://t.me/QQCleanerChat"))
        }
        binding.developerItem.setOnClickListener {
            navigatePage(R.id.action_aboutFragment_to_developerFragment)
        }
        return binding.root
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
