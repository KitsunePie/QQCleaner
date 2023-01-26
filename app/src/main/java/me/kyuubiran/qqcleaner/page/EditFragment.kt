package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.EditFragmentBinding
import me.kyuubiran.qqcleaner.uitls.navigatePage

class EditFragment : BaseFragment() {

    private val binding get() = _binding!! as EditFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditFragmentBinding.inflate(inflater, container, false)
        binding.addSortBtn.setOnClickListener {
            navigatePage(R.id.action_editFragment_to_fileFragment)

        }
        return binding.root
    }
}