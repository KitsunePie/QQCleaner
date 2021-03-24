package me.kyuubiran.qqcleaner.dialog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.loadClass
import com.github.kyuubiran.ezxhelper.utils.showToast
import me.kyuubiran.qqcleaner.R

//捐赠相关dialog
object SupportMeDialog {

    fun showSupportMeDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("请选择扶贫方式")
            .setItems(arrayOf("QQ", "支付宝", "微信")) { _, index ->
                appContext.showToast("感谢资瓷~")
                when (index) {
                    0 -> gotoQQPay(context)
                    1 -> gotoAliPay(context)
                    2 -> gotoWeChat(context)
                }
            }
            .create()
            .show()
    }

    private fun gotoQQPay(context: Context) {
        try {
            val qq = "623055567"
            val nickname = "芜狐"
            val intent = Intent()
            intent.run {
                setClass(context, try {
                    loadClass("com.tencent.mobileqq.activity.qwallet.TransactionActivity")
                } catch (t: Throwable) {
                    loadClass("com.tencent.mobileqq.qwallet.transaction.impl.TransactionActivity")
                })
                putExtra("come_from", 5)
                putExtra("fling_action_key", 2)
                putExtra("fromJump", true)
                putExtra("preAct", "TenpayJumpActivity")
                putExtra("leftViewText", "返回")
                putExtra("fling_code_key", 96049325)
                putExtra("preAct_time", System.currentTimeMillis())
                putExtra(
                    "extra_data",
                    "{\"targetUin\":\"$qq\",\"targetNickname\":\"$nickname\",\"trans_fee\":\"\",\"sign\":\"\",\"source\":\"1\",\"desc\":\"\"}"
                )
                putExtra("app_info", "appid#20000001|bargainor_id#1000026901|channel#wallet")
                putExtra("callbackSn", "0")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(e)
        }

    }

    private fun gotoAliPay(context: Context) {
        try {
            val urlCode = "fkx10658svai9rgm46mk9de"
            val intent = Intent.parseUri(
                "intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end".replace(
                    "{urlCode}",
                    urlCode
                ), 0
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(e)
        }
    }

    private fun gotoWeChat(context: Context) {
        AlertDialog.Builder(context)
            .setView(R.layout.wechat_pay_image)
            .setTitle("使用微信扫一扫")
            .create()
            .show()
    }
}