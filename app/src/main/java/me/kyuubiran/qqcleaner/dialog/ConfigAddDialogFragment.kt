package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.viewModels
import me.kyuubiran.qqcleaner.databinding.ConfigAddDialogBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class ConfigAddDialogFragment : EditDialogFragment(), ThemeFragmentRegistry {

    private val state: ConfigStates by viewModels()

    lateinit var binding: ConfigAddDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ConfigAddDialogBinding.inflate(layoutInflater)
        layout = binding.root
        initFragment()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun initColor() {

    }

    override fun initListener() {

    }

    class ConfigStates : StateHolder() {

    }


}