package me.kyuubiran.qqcleaner.page

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.fragment.NavHostFragment
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.AboutFragmentBinding

class AboutFragment : BaseFragment() {
    private val binding get() = _binding!! as AboutFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AboutFragmentBinding.inflate(inflater, container, false)
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
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_aboutFragment_to_developerFragment)
        }
        return binding.root
    }
}