package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.drake.brv.utils.linear
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ConfigFragmentBinding
import me.kyuubiran.qqcleaner.databinding.ConfigItemBinding
import me.kyuubiran.qqcleaner.dialog.ConfigEditDialogFragment
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry
import me.kyuubiran.qqcleaner.uitls.dpInt


class ConfigFragment : BaseFragment<ConfigFragmentBinding>(ConfigFragmentBinding::inflate),
    ThemeFragmentRegistry {
    private val state: ConfigStates by viewModels()
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
            }
        }

    }

    override fun initLayout() {

        val configRecyclerView = binding.configRecyclerView

        lifecycleScope.launch {
            state.configList.collect {
                if (it.isNotEmpty()) {
                    binding.emptyLayout.visibility = View.GONE
                    binding.configLayout.visibility = View.VISIBLE
                    configRecyclerView.apply {
                        // 因为宽高不会发生变换，所以使用 setHasFixedSize 减少测绘次数
                        setHasFixedSize(true)
                        linear().setup {
                            // 数据和对应的布局
                            addType<ConfigModel>(R.layout.config_item)
                            onBind {
                                val binding = getBinding<ConfigItemBinding>()
                                binding.apply {
                                    root.setOnClickListener {
                                        ConfigEditDialogFragment().showNow(parentFragmentManager, "")
                                    }

                                    // 设置属性
                                    configName.text = getModel<ConfigModel>().title
                                    authorName.text = getModel<ConfigModel>().author
                                }
                            }
                        }
                    }.setDifferModels(it)
                } else {
                    this@ConfigFragment.binding.configLayout.visibility = View.GONE
                    this@ConfigFragment.binding.emptyLayout.visibility = View.VISIBLE
                }
            }
        }


    }

    override fun initListener() {
        binding.addConfigBtn.setOnClickListener {
            lifecycleScope.launch {
                state.configList.emit(listOf(ConfigModel("新配置", "我", true)).toMutableList())
            }

            //ConfigAddDialogFragment().showNow(parentFragmentManager, "")
            // navigatePage(R.id.action_configFragment_to_editFragment)
        }
    }

    class ConfigModel(
        val title: String,
        val author: String,
        val enable: Boolean
    )

    class ConfigStates : StateHolder() {
        val configList: MutableStateFlow<MutableList<ConfigModel>> =
            MutableStateFlow(mutableListOf())

        fun initViewModel() {

        }
//        fun getConfig(context: Context): Config? {
//            // json 解析测试
//            val text = context.resources.assets.open("qq.json").readBytes()
//                .toString(Charset.defaultCharset())
//            Log.d("Json", text)
//            return Moshi.Builder().build().adapter(Config::class.java).fromJson(text)
//        }
    }


}