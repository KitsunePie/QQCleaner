package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import me.kyuubiran.qqcleaner.databinding.SortEditDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class SortEditDialogFragment : EditDialogFragment(), ThemeFragmentRegistry {

    private val state: SortStates by viewModels()

    lateinit var binding: SortEditDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = SortEditDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initFragment()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun initColor() {

    }

    override fun initListener() {

    }

    class SortStates : StateHolder() {

    }



}