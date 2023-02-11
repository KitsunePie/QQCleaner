package me.kyuubiran.qqcleaner.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import group.infotech.drawable.dsl.shapeDrawable
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.data.Config
import me.kyuubiran.qqcleaner.databinding.ConfigItemBinding
import me.kyuubiran.qqcleaner.dialog.ConfigEditDialogFragment
import me.kyuubiran.qqcleaner.page.ConfigFragment
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

class ConfigAdapter(
    private val model: MainActivityStates,
    private val configState: ConfigFragment.ConfigStates
) : RecyclerView.Adapter<ConfigAdapter.ViewHolder>() {

    class ViewHolder(
        private val itemBinding: ConfigItemBinding,
        private val model: MainActivityStates,
        private val configState: ConfigFragment.ConfigStates
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(itemBean: Config) {
            itemBinding.configName.text = itemBean.title
            itemBinding.authorName.text = itemBean.author

            itemBinding.layout.setOnClickListener {
                configState.selectConfig = itemBean
                ConfigEditDialogFragment.newInstance(
                    itemBean.title
                ).showNow(
                    (itemBinding.root.context as FragmentActivity).supportFragmentManager,
                    ""
                )
            }
            model.viewModelScope.launch {
                model.colorPalette.collect { colors ->
                    itemBinding.switchImg.setChecked(
                        itemBean.enable,
                        isWhite = false,
                        isDark = colors != LightColorPalette,
                        hasAnim = false
                    )

                    itemBinding.configName.setTextColor(colors.firstTextColor)
                    itemBinding.authorName.setTextColor(colors.thirdTextColor)
                    itemBinding.layout.background = rippleDrawable(
                        colors.rippleColor,
                        null,
                        shapeDrawable {

                            shape = GradientDrawable.RECTANGLE
                            cornerRadii = floatArrayOf(
                                10.dp(itemBinding.root.context),
                                10.dp(itemBinding.root.context),
                                0f,
                                0f,
                                0f,
                                0f,
                                10.dp(itemBinding.root.context),
                                10.dp(itemBinding.root.context)
                            )
                            setColor(Color.WHITE)
                        }
                    )
                    itemBinding.divider.setBackgroundColor(colors.switchLineColor)

                    itemBinding.switchImg.setOnClickListener {
                        itemBean.enable = !itemBean.enable
                        itemBinding.switchImg.setChecked(
                            itemBean.enable,
                            isWhite = false,
                            isDark = colors != LightColorPalette,
                            hasAnim = true
                        )
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ConfigItemBinding.inflate(from(parent.context), parent, false)

        return ViewHolder(itemBinding, model, configState)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val itemBean = configState.configList[position]
        viewHolder.bind(itemBean)
    }

    //  添加数据
    fun addConfig(value: Config) {
        configState.configList.add(value)
        notifyItemInserted(configState.configList.size)
    }

    fun removeConfigAt(position: Int) {
        configState.configList.removeAt(position)
        //删除动画
        notifyItemRemoved(position);
    }

    fun removeConfig(value: Config) {
        configState.configList.remove(value)
        //删除动画
        notifyItemRemoved(configState.configList.indexOf(value))
    }

    override fun getItemCount() = configState.configList.size
}