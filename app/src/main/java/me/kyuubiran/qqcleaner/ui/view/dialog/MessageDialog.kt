package me.kyuubiran.qqcleaner.ui.view.dialog

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.utils.fillMaxModifier
import me.kyuubiran.qqcleaner.ui.utils.fillMaxWidthModifier

/**
 * 这个是一个消息弹窗
 * @param context 对应 context 为所需参数
 * @param titleText 标题
 * @param tipText 提示文本
 *
 */
fun messageDialog(context: Context, titleText: String = "测试", tipText: String = "内容测试啦啦啦啦") {
    BaseDialog(context = context).apply {
        this.setContent {
            Box(contentAlignment = Alignment.BottomStart) {
                Box(modifier = fillMaxModifier.background(Color(0x33202124)))
                Column(
                    modifier = fillMaxWidthModifier
                        .height(196.dp)
                        .background(
                            shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                            color = Color(0xFFFFFFFF)
                        )
                ) {
                    Text(text = titleText)
                    Text(text = tipText)
                }
            }
        }

        this.show()
    }
}