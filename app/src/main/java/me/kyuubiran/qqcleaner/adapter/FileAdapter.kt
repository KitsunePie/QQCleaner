package me.kyuubiran.qqcleaner.adapter

import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.data.Path
import me.kyuubiran.qqcleaner.widget.FileItem

class FileAdapter(
    private val model: MainActivity.MainActivityStates,
    private val dataSet: List<Path>,
) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {
    class ViewHolder(
        private val model: MainActivity.MainActivityStates,
        private val fileItem: FileItem
    ) : RecyclerView.ViewHolder(fileItem) {
        fun setPath(path: Path) {
            fileItem.text = path.suffix
            fileItem.setOnClickListener {

            }
            model.viewModelScope.launch {
                model.colorPalette.collect { colors ->
                    fileItem.apply {
                        setRippleColor(colors.rippleColor)
                        setTextColor(colors.secondTextColor)
                        setDrawableColor(colors.secondTextColor)
                    }
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val fileItem = FileItem(parent.context, null).apply {
            this.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        }
        return ViewHolder(model, fileItem)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setPath(dataSet[position])
    }

}