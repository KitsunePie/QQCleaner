package me.kyuubiran.qqcleaner.data

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.forEach
import com.github.kyuubiran.ezxhelper.utils.getStringOrDefault
import com.github.kyuubiran.ezxhelper.utils.toArray
import me.kyuubiran.qqcleaner.util.PathUtil
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class CleanData(jsonObject: JSONObject) {
    val name: String = jsonObject.getStringOrDefault("name", "一个没有名字的配置文件")
    val author = jsonObject.getStringOrDefault("author", "无名氏")
    val fileMap: HashMap<String, Array<File>> = hashMapOf()

    init {
        val content = jsonObject.getJSONArray("content")
        //遍历 content
        content.forEach<JSONObject> {
            try {
                //获取路径列表
                val pathList = it.getJSONArray("path").toArray<String>().also { arr ->
                    arr.forEachIndexed { index, s ->
                        //格式化路径
                        arr[index] = PathUtil.format(s)
                    }
                }
                //创建file列表
                val fileList = ArrayList<File>().run {
                    pathList.forEach { s ->
                        add(File(s))
                    }
                    toTypedArray()
                }
                //添加到fileMap中
                fileMap[it.getString("title")] = fileList
            } catch (je: JSONException) {
                Log.e(je)
            }
        }
    }

    constructor(json: String) : this(JSONObject(json))
    constructor(jsonFile: File) : this(jsonFile.readText())
}