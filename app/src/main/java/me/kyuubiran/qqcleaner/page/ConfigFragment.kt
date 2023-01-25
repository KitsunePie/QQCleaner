package me.kyuubiran.qqcleaner.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ConfigFragmentBinding
import me.kyuubiran.qqcleaner.databinding.ConfigItemBinding

class ConfigFragment : BaseFragment() {
    private val binding get() = _binding!! as ConfigFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfigFragmentBinding.inflate(inflater, container, false)
        val configRecyclerView = binding.configRecyclerView

        val configList: List<ConfigModel> = listOf(
            //ConfigModel("新配置", "我", true)
        )

        if(configList.isNotEmpty()){
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
        }else{
            this.binding.emptyLayout.visibility = View.VISIBLE
        }

        binding.addConfigBtn.setOnClickListener {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_configFragment_to_editFragment)
        }


        return binding.root
    }

    private class ConfigModel(
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