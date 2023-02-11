package me.kyuubiran.qqcleaner.page

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.adapter.ConfigAdapter
import me.kyuubiran.qqcleaner.data.Config
import me.kyuubiran.qqcleaner.databinding.ConfigFragmentBinding
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.checkDeviceHasNavigationBar
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.getNavigationBarHeight
import java.nio.charset.Charset


class ConfigFragment : BaseFragment<ConfigFragmentBinding>(ConfigFragmentBinding::inflate),
    ThemeFragmentRegistry {

    private val state: ConfigStates by viewModels()

    private lateinit var adapter: ConfigAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    override fun initColor() {
        lifecycleScope.launch {
            model.colorPalette.collect {
                binding.toolBar.setIconRippleColor(it.rippleColor)
            }
        }
    }

    override fun initDrawable() {
        lifecycleScope.launch {
            model.colorPalette.collect {
                binding.root.setBackgroundColor(it.pageBackgroundColor)
                binding.configLayout.setBackgroundColor(it.appBarsAndItemBackgroundColor)

                binding.toolBar.apply {
                    setIconRippleColor(it.rippleColor)
                    setTitleColor(it.firstTextColor)
                    setIconColor(it.firstTextColor)
                }

                binding.listEmptyText.setCompoundDrawables(
                    null,
                    AppCompatResources.getDrawable(
                        requireContext(),
                        if (it == LightColorPalette)
                            R.drawable.ic_list_empty
                        else
                            R.drawable.ic_list_empty_dark
                    )!!.apply {
                        setBounds(0, 0, 96.dpInt, 96.dpInt)
                    },
                    null,
                    null
                )

                binding.addConfigBtn.setLayoutBackground(it.mainThemeColor)
                binding.addConfigBtn.setLayoutBackgroundTrue(it.whiteColor)
                binding.addConfigBtn.setShadowColor(it.sixtyThreePercentThemeColor)
            }
        }

    }

    override fun initLayout() {

        val configRecyclerView = binding.configRecyclerView


        for (i in 1..100) {
            val itemBean = state.getConfig(requireContext())
            if (itemBean != null) {
                state.configList.add(itemBean)
            }
        }
        adapter = ConfigAdapter(dataSet = state.configList, model = model)
        configRecyclerView.setHasFixedSize(true)
        configRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        configRecyclerView.adapter = adapter

        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            // 设置按钮边距
            binding.addConfigBtn.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else if (checkDeviceHasNavigationBar()) getNavigationBarHeight() else 0) + 24.dpInt
            }
            insets
        }

    }

    override fun initListener() {
        binding.addConfigBtn.setOnClickListener {
            adapter.removeConfigAt(1)

            //ConfigAddDialogFragment().showNow(parentFragmentManager, "")
            // navigatePage(R.id.action_configFragment_to_editFragment)
        }
    }


    class ConfigStates : StateHolder() {

        val configList = ArrayList<Config>()

        // 仅仅作为演示
        fun getConfig(context: Context): Config? {
            // json 解析测试
            val text = context.resources.assets.open("qq.json").readBytes()
                .toString(Charset.defaultCharset())
            return Moshi.Builder().build().adapter(Config::class.java).fromJson(text)
        }
    }
}