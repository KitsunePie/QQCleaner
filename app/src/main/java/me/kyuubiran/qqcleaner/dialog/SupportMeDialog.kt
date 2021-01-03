package me.kyuubiran.qqcleaner.dialog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.utils.loge
import me.kyuubiran.qqcleaner.utils.qqContext
import me.kyuubiran.qqcleaner.utils.showToastBySystem
import java.lang.Exception

//捐赠相关dialog
object SupportMeDialog {

    fun showSupportMeDialog(context: Context) {
        val arr = ArrayList<CharSequence>()
        arr.add("支付宝")
        arr.add("微信")
        AlertDialog.Builder(context)
            .setTitle("请选择扶贫方式")
            .setItems(arrayOf("支付宝", "微信")) { _, index ->
                when (index) {
                    0 -> gotoAliPay(context)
                    1 -> gotoWeChat(context)
                }
            }
            .create()
            .show()
    }

    private fun gotoAliPay(context: Context) {
        try {
            qqContext?.showToastBySystem("感谢资瓷~")
            val urlCode = "fkx10658svai9rgm46mk9de"
            val intent = Intent.parseUri(
                "intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end".replace(
                    "{urlCode}",
                    urlCode
                ), 0
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            loge(e)
        }
    }

    private fun gotoWeChat(context: Context) {
        qqContext?.showToastBySystem("感谢资瓷~")
        AlertDialog.Builder(context)
            .setView(R.layout.wechat_pay_image)
            .setTitle("使用微信扫一扫")
            .create()
            .show()
    }
}