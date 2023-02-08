package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ConfigFragmentBinding
import me.kyuubiran.qqcleaner.databinding.ConfigItemBinding
import me.kyuubiran.qqcleaner.theme.ThemeFragmentRegistry


class ConfigFragment : BaseFragment<ConfigFragmentBinding>(ConfigFragmentBinding::inflate),
    ThemeFragmentRegistry {
    private val configList: List<ConfigModel> = listOf(
        //ConfigModel("新配置", "我", true)
    )

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

    }

    override fun initLayout() {
        if (configList.isNotEmpty()) {
            val configRecyclerView = binding.configRecyclerView
            this.binding.configLayout.visibility = View.VISIBLE
            configRecyclerView.apply {
                // 因为宽高不会发生变换，所以使用 setHasFixedSize 减少测绘次数
                setHasFixedSize(true)
                linear().setup {
                    // 数据和对应的布局
                    addType<ConfigModel>(R.layout.config_item)
                    onBind {
                        val binding = getBinding<ConfigItemBinding>()
                        binding.apply {
                            // 设置属性
                            configName.text = getModel<ConfigModel>().title
                            authorName.text = getModel<ConfigModel>().author
                        }

                    }
                }
                models = configList
            }
        } else {
            this.binding.emptyLayout.visibility = View.VISIBLE
        }
    }

    override fun initListener() {
        binding.addConfigBtn.setOnClickListener {
            navigatePage(R.id.action_configFragment_to_editFragment)
        }
    }

    class ConfigModel(
        val title: String,
        val author: String,
        val enable: Boolean
    )

    private class ConfigStates() : StateHolder() {
//        fun getConfig(context: Context): Config? {
//            // json 解析测试
//            val text = context.resources.assets.open("qq.json").readBytes()
//                .toString(Charset.defaultCharset())
//            Log.d("Json", text)
//            return Moshi.Builder().build().adapter(Config::class.java).fromJson(text)
//        }
    }


}