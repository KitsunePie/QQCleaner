package me.kyuubiran.qqcleaner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
                        View clean = (View) Utils.new_instance(FormSimpleItem, param.thisObject, Context.class);
                        View cleanAll = (View) Utils.new_instance(FormSimpleItem, param.thisObject, Context.class);
                        Utils.invoke_virtual(clean, "setLeftText", "一键瘦身", CharSequence.class);
                        Utils.invoke_virtual(cleanAll, "setLeftText", "彻底瘦身(彻底清理缓存)", CharSequence.class);
                        ViewGroup vg = (ViewGroup) item.getParent();
                        vg.addView(clean, 2);
                        vg.addView(cleanAll, 3);
                        clean.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(context, "长按以清除缓存", LENGTH_SHORT).show();
                            }
                        });
                        clean.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                cleanCache(context, false);
                                return true;
                            }
                        });
                        cleanAll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "长按清理所有缓存(某些缓存会在重启QQ后重新加载)", LENGTH_SHORT).show();
                            }
                        });
                        cleanAll.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                cleanCache(context, true);
                                return true;
                            }
                        });

                    }
                });
            }
        }
    }

    public void cleanCache(final Context context, final boolean cleanAll) {
        Toast.makeText(context, "正在清理缓存", LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<File> filesDir = new ArrayList<>();
                    //根目录
                    String rootDataDir = context.getExternalCacheDir().getParentFile().getPath();
                    String rootDir = context.getObbDir().getParentFile().getParentFile().getParentFile().getPath();
                    String rootTencentDir = rootDir + "/tencent";
                    String TencentDir = rootDataDir + "/Tencent";
                    String MobileQQ = TencentDir + "/MobileQQ";
                    String QQ_Images = rootDataDir + "/QQ_Images";
                    String QQfile_recv = TencentDir + "/QQfile_recv";

                    //缓存
                    filesDir.add(new File(rootDataDir + "/cache"));
                    filesDir.add(new File(MobileQQ + "/diskcache"));
                    filesDir.add(new File(MobileQQ + "/Scribble/ScribbleCache"));
                    //被QQ压缩过的图片
                    filesDir.add(new File(MobileQQ + "/photo"));
                    //短视频
                    filesDir.add(new File(MobileQQ + "/shortvideo"));
                    //也是QQ遗留下来的图片缓存
                    filesDir.add(new File(MobileQQ + "/thumb"));
                    //横幅广告
                    filesDir.add(new File(MobileQQ + "/qbosssplahAD"));
                    //貌似也是广告
                    filesDir.add(new File(MobileQQ + "/pddata"));
                    //就是这玩意几千个图片大的一批 删就完事了 删完就小了
                    filesDir.add(new File(MobileQQ + "/chatpic"));
                    //编辑过的图片
                    filesDir.add(new File(QQ_Images + "/QQEditPic"));
                    //小程序缓存
                    filesDir.add(new File(TencentDir + "/mini"));
                    //网页登录缓存
                    filesDir.add(new File(rootTencentDir + "/msflogs/com/tencent/mobileqq"));
                    //名片缓存
                    filesDir.add(new File((MobileQQ + "/.apollo")));

                    if (cleanAll) {
                        //字体
                        filesDir.add(new File(MobileQQ + "/.font_info"));
                        //嗨爆字体
                        filesDir.add(new File(MobileQQ + "/.hiboom_font"));
                        //送礼物
                        filesDir.add(new File(MobileQQ + "/.gift"));
                        //头像挂件
                        filesDir.add(new File(MobileQQ + "/.pendant"));
                        //资料卡背景
                        filesDir.add(new File(MobileQQ + "/.profilecard"));
                        //表情推荐
                        filesDir.add(new File(MobileQQ + "/.sticker_recommended_pics"));
                        //进场特效
                        filesDir.add(new File(MobileQQ + "/.troop/enter_effects"));
                        //戳一戳
                        filesDir.add(new File(MobileQQ + "/.vaspoke"));
                        filesDir.add(new File(MobileQQ + "/newpoke"));
                        filesDir.add(new File(MobileQQ + "/poke"));
                        //vip图标
                        filesDir.add(new File(MobileQQ + "/.vipicon"));
                        //斗图相关
                        filesDir.add(new File(MobileQQ + "/DoutuRes"));
                        //QQ电话动画背景
                        filesDir.add(new File(MobileQQ + "/funcall"));
                        //头像缓存
                        filesDir.add(new File(MobileQQ + "/head"));
                        //热图?
                        filesDir.add(new File(MobileQQ + "/hotpic"));
                        //貌似也是表情
                        filesDir.add(new File(MobileQQ + "/pe"));
                        //暂时不知道的东西
                        filesDir.add(new File(MobileQQ + "/qav"));
                        //qq音乐
                        filesDir.add(new File(MobileQQ + "/qqmusic"));
                        //接收文件缓存
                        filesDir.add(new File(QQfile_recv + "/trooptmp"));
                        filesDir.add(new File(QQfile_recv + "/tmp"));
                        filesDir.add(new File(QQfile_recv + "/thumbnails"));
                    }
                    //执行删除
                    for (File f : filesDir) {
                        Utils.deleteAllFiles(f);
                    }
                } catch (Throwable t) {
                    XposedBridge.log(TAG + t);
                }
                Activity activity = (Activity) context;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "清理完成,请重启QQ", LENGTH_SHORT).show();
                        XposedBridge.log(TAG + "清理完成");
                    }
                });
            }
        }).start();
    }
}
