package me.kyuubiran.qqcleaner.data

import com.github.kyuubiran.ezxhelper.utils.*
import me.kyuubiran.qqcleaner.util.HostAppUtil
import me.kyuubiran.qqcleaner.util.PathUtil
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class CleanData(private val jsonObject: JSONObject) {
    var name: String = ""
        set(value) {
            jsonObject.put("name", value)
            field = value
        }
        get() = jsonObject.getStringOrDefault("name", "一个没有名字的配置文件")

    var author: String = ""
        set(value) {
            jsonObject.put("author", value)
            field = value
        }
        get() = jsonObject.getStringOrDefault("author", "无名氏")

    var hostApp: String = ""
        set(value) {
            jsonObject.put("author", value)
            field = value
        }
        get() = jsonObject.getStringOrDefault("hostApp")

    var regexp: Boolean = false
        set(value) {
            jsonObject.put("regexp", value)
            field = value
        }
        get() = jsonObject.getBooleanOrDefault("regexp")

    val filePathMap: HashMap<String, Array<String>> = hashMapOf()

    val valid: Boolean
        get() = HostAppUtil.containsCurrentHostApp(hostApp)

    init {
        val content = jsonObject.getJSONArray("content")
        //遍历 content
        content.forEach<JSONObject> {
            try {
                //获取路径列表
                val pathList = it.getJSONArray("path").toArray<String>().also { arr ->
                    arr.forEachIndexed { index, s ->
                        //格式化路径
                        arr[index] = PathUtil.getFullPath(s)
                    }
                }
                //添加到fileMap中
                filePathMap[it.getString("title")] = pathList
            } catch (je: JSONException) {
                Log.e(je)
            }
        }
    }

    override fun toString(): String {
        return jsonObject.toString()
    }

    fun toFormatString(indentSpaces: Int = 2): String {
        return jsonObject.toString(indentSpaces)
    }

    constructor(json: String) : this(JSONObject(json))
    constructor(jsonFile: File) : this(jsonFile.readText())
}