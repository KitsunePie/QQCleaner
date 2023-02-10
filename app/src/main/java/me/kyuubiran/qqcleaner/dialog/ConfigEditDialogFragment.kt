package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import me.kyuubiran.qqcleaner.databinding.ConfigEditDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class ConfigEditDialogFragment : EditDialogFragment(), ThemeFragmentRegistry {

    private val state: ConfigEditStates by viewModels()

    lateinit var binding: ConfigEditDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ConfigEditDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initFragment()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun initColor() {

    }

    override fun initListener() {

    }

    class ConfigEditStates : StateHolder() {

    }


}