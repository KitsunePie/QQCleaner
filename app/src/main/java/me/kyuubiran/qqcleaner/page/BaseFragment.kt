package me.kyuubiran.qqcleaner.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import me.kyuubiran.qqcleaner.MainActivity

open class BaseFragment : Fragment() {
    protected var _binding: ViewBinding? = null

    protected val model: MainActivity.MainActivityStates by activityViewModels()
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open class StateHolder : ViewModel()
}