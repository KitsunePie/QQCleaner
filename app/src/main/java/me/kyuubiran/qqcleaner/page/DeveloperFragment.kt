package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }
}