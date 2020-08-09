package me.kyuubiran.qqcleaner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static android.widget.Toast.LENGTH_SHORT;

public class MainHook implements IXposedHookLoadPackage {
    private static final String TAG = "QQ瘦身:";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.tencent.mobileqq")) {
            hookOnCreate(lpparam);
        }
    }

    public void hookOnCreate(final XC_LoadPackage.LoadPackageParam lpparam) {
        for (Method m : XposedHelpers.findClass("com.tencent.mobileqq.activity.AboutActivity", lpparam.classLoader).getDeclaredMethods()) {
            if (m.getName().equals("doOnCreate")) {
                XposedBridge.hookMethod(m, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Class<?> FormSimpleItem = XposedHelpers.findClass("com.tencent.mobileqq.widget.FormSimpleItem", lpparam.classLoader);
                        View item = (View) Utils.iget_object_or_null(param.thisObject, "a", FormSimpleItem);
                        final Context context = (Context) item.getContext();
                        View entity = (View) Utils.new_instance(FormSimpleItem, param.thisObject, Context.class);
                        Utils.invoke_virtual(entity, "setLeftText", "一键瘦身", CharSequence.class);
                        ViewGroup vg = (ViewGroup) item.getParent();
                        vg.addView(entity, 2);
                        entity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "长按以清除缓存", LENGTH_SHORT).show();
                            }
                        });
                        entity.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                cleanCache(context);
                                return true;
                            }
                        });
                    }
                });
            }
        }
    }


    public void cleanCache(final Context context) {
        Toast.makeText(context, "正在清理缓存", LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //根目录
                    String rootFile = context.getExternalCacheDir().getParentFile().getPath();
                    String MobileQQ = rootFile + "/Tencent/MobileQQ";
                    String QQ_Images = rootFile + "/QQ_Images";
                    //需要清理的目录
                    //缓存
                    File cache = new File(rootFile + "/cache");
                    File diskcache = new File(MobileQQ + "/diskcache");
                    File ScribbleCache = new File(MobileQQ + "/Scribble/ScribbleCache");
                    //被QQ压缩过的图片
                    File photo = new File(MobileQQ + "/photo");
                    //短视频
                    File shortVideo = new File(MobileQQ + "/shortVideo");
                    //也是QQ遗留下来的图片缓存
                    File thumb = new File(MobileQQ + "/thumb");
                    //横幅广告
                    File qbosssplahAD = new File(MobileQQ + "/qbosssplahAD");
                    //貌似也是广告
                    File pddata = new File(MobileQQ + "/pddata");
                    //就是这玩意几千个图片大的一批 删就完事了 删完就小了
                    File chatpic = new File(MobileQQ + "/chatpic");
                    //编辑过的图片
                    File QQEditPic = new File(QQ_Images + "/QQEditPic");

                    //执行清理
                    Utils.deleteAllFiles(cache);
//                    XposedBridge.log("清理cache成功");
                    Utils.deleteAllFiles(diskcache);
//                    XposedBridge.log("清理diskcache成功");
                    Utils.deleteAllFiles(ScribbleCache);
//                    XposedBridge.log("清理ScribbleCache成功");
                    Utils.deleteAllFiles(photo);
//                    XposedBridge.log("清理photo成功");
                    Utils.deleteAllFiles(shortVideo);
//                    XposedBridge.log("清理shortVideo成功");
                    Utils.deleteAllFiles(thumb);
//                    XposedBridge.log("清理thumb成功");
                    Utils.deleteAllFiles(qbosssplahAD);
//                    XposedBridge.log("清理qbosssplahAD成功");
                    Utils.deleteAllFiles(pddata);
//                    XposedBridge.log("清理pddata成功");
                    Utils.deleteAllFiles(chatpic);
//                    XposedBridge.log("清理chatpic成功");
                    Utils.deleteAllFiles(QQEditPic);
//                    XposedBridge.log("清理QQEditPic成功");
                } catch (Throwable t) {
                    XposedBridge.log(TAG + t);
                }
                Activity activity = (Activity) context;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "清理完成,请重启QQ", LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
