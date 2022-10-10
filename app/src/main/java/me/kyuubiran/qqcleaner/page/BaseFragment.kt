package me.kyuubiran.qqcleaner.page

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import me.kyuubiran.qqcleaner.databinding.HomeFragmentBinding

open class BaseFragment: Fragment() {
    protected var _binding: HomeFragmentBinding? = null
    protected val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    protected open class StateHolder: ViewModel()
}