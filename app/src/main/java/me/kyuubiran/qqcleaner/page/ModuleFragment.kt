package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ModuleFragmentBinding


class ModuleFragment : BaseFragment() {
    val name = this.javaClass.name
    private val binding get() = _binding!! as ModuleFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModuleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            // 虽然不知道为什么，但是一定需要加上这行，不然顶栏无法显示
            Navigation.findNavController(this.requireActivity(), binding.fragment.id)
            binding.fragment.getFragment<AboutFragment>().findNavController().apply {
                // 判断需要前往的页面是什么
                val graph = navInflater.inflate(R.navigation.module_nav_graph).apply {
                    when (it.getString("subFragment", "none").toString()) {
                        "about" -> setStartDestination(R.id.aboutFragment)
                        "config" -> setStartDestination(R.id.configFragment)
                        else -> throw RuntimeException("$name's arguments subFragment is null")
                    }
                }
                setGraph(graph, Bundle())
            }
        }
    }
}