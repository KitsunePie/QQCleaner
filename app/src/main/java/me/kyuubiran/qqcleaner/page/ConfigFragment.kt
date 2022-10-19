package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.kyuubiran.qqcleaner.databinding.ConfigFragmentBinding

class ConfigFragment: BaseFragment() {
    private val binding get() = _binding!! as ConfigFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfigFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}