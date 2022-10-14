package me.kyuubiran.qqcleaner.page

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

open class BaseFragment: Fragment() {
    protected var _binding: ViewBinding? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    protected open class StateHolder: ViewModel()
}