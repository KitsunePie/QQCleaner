package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import me.kyuubiran.qqcleaner.adapter.FileAdapter
import me.kyuubiran.qqcleaner.databinding.FileFragmentBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry

class FileFragment : BaseFragment<FileFragmentBinding>(FileFragmentBinding::inflate),
    ThemeFragmentRegistry {
    private val sortState: SortFragment.SortStates by activityViewModels()
    private lateinit var adapter: FileAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    override fun initLayout() {
        super.initLayout()
        binding.toolBar.title = sortState.configContent.title
        val fileRecyclerView = binding.fileRecyclerView
        adapter = FileAdapter(
            model = model,
            dataSet = sortState.configContent.pathList
        )
        fileRecyclerView.setHasFixedSize(true)
        fileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        fileRecyclerView.adapter = adapter


    }

    override fun initColor() {

    }

    override fun initListener() {

    }


}