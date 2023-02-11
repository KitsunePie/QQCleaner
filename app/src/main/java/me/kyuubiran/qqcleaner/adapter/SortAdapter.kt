package me.kyuubiran.qqcleaner.adapter

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.data.ConfigContent
import me.kyuubiran.qqcleaner.dialog.SortEditDialogFragment
import me.kyuubiran.qqcleaner.page.SortFragment
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.widget.SwitchItem

class SortAdapter(
    private val model: MainActivity.MainActivityStates,
    private val dataSet: List<ConfigContent>,
    private val sortState: SortFragment.SortStates
) : RecyclerView.Adapter<SortAdapter.ViewHolder>() {
    class ViewHolder(
        private val model: MainActivity.MainActivityStates,
        private val switchItem: SwitchItem,
        private val sortState: SortFragment.SortStates
    ) : RecyclerView.ViewHolder(switchItem) {
        fun setConfigContent(content: ConfigContent) {
            switchItem.text = content.title
            switchItem.setSwitchChecked(content.enable)

            switchItem.setSwitchListener {
                content.enable = it
            }
            switchItem.setOnLongClickListener {
                sortState.configContent = content

                SortEditDialogFragment.newInstance(
                    content.title
                ).showNow(
                    (switchItem.context as FragmentActivity).supportFragmentManager,
                    ""
                )
                false
            }

            model.viewModelScope.launch {
                model.colorPalette.collect { colors ->
                    switchItem.apply {
                        setRippleColor(colors.rippleColor)
                        setTextColor(colors.secondTextColor)
                        setSwitchColor(false, colors != LightColorPalette)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val switchItem = SwitchItem(parent.context, null).apply {
            this.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        }
        return ViewHolder(model, switchItem, sortState)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setConfigContent(dataSet[position])
    }

}