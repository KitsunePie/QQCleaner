package me.kyuubiran.qqcleaner.data

import com.github.kyuubiran.ezxhelper.utils.*
import me.kyuubiran.qqcleaner.util.HostAppUtil
import org.json.JSONObject
import java.io.File

class CleanData(private val jsonObject: JSONObject) {
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

//        // 正则表达式
//        var regexp: Boolean
//            set(value) {
//                jsonObject.put("regexp", value)
//            }
//            get() = jsonObject.getBooleanOrDefault("regexp")

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
            jsonObject.put("author", value)
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

    fun toFormatString(indentSpaces: Int = 2): String {
        return jsonObject.toString(indentSpaces)
    }

    fun export() {
        val f = File("/storage/emulated/0/Download/${this.title}.json")
        if (!f.exists()) f.createNewFile()
        f.writeText(this.toFormatString())
    }

    constructor(json: String) : this(JSONObject(json))
    constructor(jsonFile: File) : this(jsonFile.readText())
}