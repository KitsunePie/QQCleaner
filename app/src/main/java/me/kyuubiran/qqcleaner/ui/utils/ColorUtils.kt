package me.kyuubiran.qqcleaner.ui.utils

object ColorUtils {

    /**
     * 进行颜色混合的 api
     * @param color1 颜色1
     * @param color1 颜色2
     * @param ratio 混合比例（两个颜色总和为1）
     * @return 返回对应的颜色
     */
    fun mixColor(color1: Int, color2: Int, ratio: Float): Int {
        val inverse = 1 - ratio
        val a = (color1 ushr 24) * inverse + (color2 ushr 24) * ratio
        val r = (color1 shr 16 and 0xFF) * inverse + (color2 shr 16 and 0xFF) * ratio
        val g = (color1 shr 8 and 0xFF) * inverse + (color2 shr 8 and 0xFF) * ratio
        val b = (color1 and 0xFF) * inverse + (color2 and 0xFF) * ratio
        return a.toInt() shl 24 or (r.toInt() shl 16) or (g.toInt() shl 8) or b.toInt()
    }
}