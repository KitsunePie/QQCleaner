package me.kyuubiran.qqcleaner.data

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.*
import me.kyuubiran.qqcleaner.util.CleanManager
import me.kyuubiran.qqcleaner.util.CleanManager.getConfigDir
import me.kyuubiran.qqcleaner.util.HostAppUtil
import org.json.JSONObject
import java.io.File


class CleanData(private val jsonObject: JSONObject) {
    private var file: File? = null

    class PathData(private val jsonObject: JSONObject) {
        // 标题
        var title: String
            set(value) {
                jsonObject.put("title", value)
            }
            get() = jsonObject.getStringOrDefault("name", "一个没有名字的配置文件")

        // 是否启用
        var enable: Boolean
            set(value) {
                jsonObject.put("enable", value)
            }
            get() = jsonObject.getBooleanOrDefault("enable", false)

        // 路径
        val pathList = jsonObject.getJSONArrayOrEmpty("path").toArrayList<String>()

        // 添加路径
        fun addPath(path: String) {
            pathList.add(path)
        }

        //删除路径
        fun removePath(idx: Int) {
            try {
                pathList.removeAt(idx)
            } catch (e: Exception) {
                Log.e(e)
            }
        }

        //删除路径
        fun removePath(string: String) {
            pathList.remove(string)
        }

        override fun toString(): String {
            return jsonObject.toString()
        }

        fun toFormatString(indentSpaces: Int = 2): String {
            return jsonObject.toString(indentSpaces)
        }
    }

    // 配置文件标题
    var title: String
        set(value) {
            jsonObject.put("title", value)
        }
        get() = jsonObject.getStringOrDefault("title", "一个没有名字的配置文件")

    // 作者
    var author: String
        set(value) {
            jsonObject.put("author", value)
        }
        get() = jsonObject.getStringOrDefault("author", "无名氏")

    // 是否启用
    var enable: Boolean
        set(value) {
            jsonObject.put("enable", value)
        }
        get() = jsonObject.getBooleanOrDefault("enable", false)

    // 宿主类型
    var hostApp: String
        set(value) {
            jsonObject.put("hostApp", value)
        }
        get() = jsonObject.getStringOrDefault("hostApp")

    // 内容
    val content = jsonObject.getJSONArrayOrEmpty("content").run {
        arrayListOf<PathData>().apply {
            for (i in 0 until this@run.length()) {
                add(PathData(this@run.getJSONObject(i)))
            }
        }
    }

    fun addContent(pathData: PathData) {
        content.add(pathData)
    }

    fun removeContent(idx: Int) {
        content.removeAt(idx)
    }

    fun removeContent(pathData: PathData) {
        content.remove(pathData)
    }

    // 是否有效
    val valid: Boolean
        get() = HostAppUtil.containsCurrentHostApp(hostApp)

    override fun toString(): String {
        return jsonObject.toString()
    }

    /**
     * 格式化的JSONString
     * @param indentSpaces 缩进
     */
    fun toFormatString(indentSpaces: Int = 2): String {
        return jsonObject.toString(indentSpaces)
    }

    /**
     * 复制到剪切板
     */
    fun copyToClipboard() {
        (appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
            setPrimaryClip(ClipData.newPlainText(title, toFormatString()))
        }
    }

    /**
     * 导出配置文件到下载目录
     */
    fun export() {
        val f = File("/storage/emulated/0/Download/${this.title}.json")
        if (!f.exists()) f.createNewFile()
        f.writeText(this.toFormatString())
    }

    /**
     * 将配置文件推至队列执行
     */
    fun pushToExecutionQueue(showToast: Boolean = true) {
        CleanManager.execute(this, showToast)
    }

    /**
     * 保存配置文件 一般在返回的时候调用
     */
    @Synchronized
    fun save() {
        file?.let {
            if (!it.exists()) it.createNewFile()
            it.writeText(toFormatString())
            return
        }
        file = File("${getConfigDir().path}/${title}.json").apply {
            if (!exists()) createNewFile()
            writeText(toFormatString())
        }
    }

    /**
     * 删除配置文件
     */
    @Synchronized
    fun delete() {
        file?.let {
            if (it.exists()) it.delete()
        }
    }

    companion object {
        @JvmStatic
        fun fromJson(jsonString: String): CleanData {
            return CleanData(JSONObject(jsonString))
        }

        @JvmStatic
        fun fromClipboard(alsoSave: Boolean = true): CleanData? {
            (appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                primaryClip?.let { clipData ->
                    if (clipData.itemCount > 0) {
                        clipData.getItemAt(0).text.run {
                            return fromJson(this.toString()).also {
                                if (alsoSave) it.save()
                            }
                        }
                    }
                }
            }
            return null
        }
    }

    constructor(jsonFile: File) : this(JSONObject(jsonFile.readText())) {
        file = jsonFile
    }
}