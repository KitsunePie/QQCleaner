package me.kyuubiran.qqcleaner.page

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates


open class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    protected val model: MainActivityStates by activityViewModels()

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigatePage(@IdRes resId: Int) {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(resId)
    }

    fun openUrlInBrowser(url: String) {
        CustomTabsIntent.Builder().build()
            .launchUrl(requireContext(), Uri.parse(url))
    }


    open class StateHolder : ViewModel()
}