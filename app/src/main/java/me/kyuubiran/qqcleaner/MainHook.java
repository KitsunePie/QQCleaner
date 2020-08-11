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
                        View clean = (View) Utils.new_instance(FormSimpleItem, param.thisObject, Context.class);
                        View cleanAll = (View) Utils.new_instance(FormSimpleItem, param.thisObject, Context.class);
                        Utils.invoke_virtual(clean, "setLeftText", "一键瘦身", CharSequence.class);
                        Utils.invoke_virtual(cleanAll, "setLeftText", "彻底瘦身(彻底清理缓存)", CharSequence.class);
                        ViewGroup vg = (ViewGroup) item.getParent();
                        vg.addView(clean, 2);
                        vg.addView(cleanAll, 3);
                        clean.setOnClickListener(new View.OnClickListener() {
                            @Override
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
                    //根目录
                    String rootFile = context.getExternalCacheDir().getParentFile().getPath();
                    String Tencent = rootFile + "/Tencent";
                    String MobileQQ = Tencent + "/MobileQQ";
                    String QQ_Images = rootFile + "/QQ_Images";
                    String QQfile_recv = Tencent + "/QQfile_recv";

                    //缓存
                    File cache = new File(rootFile + "/cache");
                    File diskcache = new File(MobileQQ + "/diskcache");
                    File ScribbleCache = new File(MobileQQ + "/Scribble/ScribbleCache");
                    //被QQ压缩过的图片
                    File photo = new File(MobileQQ + "/photo");
                    //短视频
                    File shortvideo = new File(MobileQQ + "/shortvideo");
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
                    //小程序缓存
                    File mini = new File(Tencent + "/mini");

                    Utils.deleteAllFiles(cache);
                    Utils.deleteAllFiles(diskcache);
                    Utils.deleteAllFiles(ScribbleCache);
                    Utils.deleteAllFiles(photo);
                    Utils.deleteAllFiles(shortvideo);
                    Utils.deleteAllFiles(thumb);
                    Utils.deleteAllFiles(qbosssplahAD);
                    Utils.deleteAllFiles(pddata);
                    Utils.deleteAllFiles(chatpic);
                    Utils.deleteAllFiles(QQEditPic);
                    Utils.deleteAllFiles(mini);

                    if (cleanAll) {
                        //字体
                        File font_info = new File(MobileQQ + "/.font_info");
                        //嗨爆字体
                        File hiboom_font = new File(MobileQQ + "/.hiboom_font");
                        //送礼物
                        File gift = new File(MobileQQ + "/.gift");
                        //头像挂件
                        File pendant = new File(MobileQQ + "/.pendant");
                        //资料卡背景
                        File profilecard = new File(MobileQQ + "/.profilecard");
                        //表情推荐
                        File sticker_recommended_pics = new File(MobileQQ + "/.sticker_recommended_pics");
                        //进场特效
                        File enter_effects = new File(MobileQQ + "/.troop/enter_effects");
                        //戳一戳
                        File vaspoke = new File(MobileQQ + "/.vaspoke");
                        File newpoke = new File(MobileQQ + "/newpoke");
                        File poke = new File(MobileQQ + "/poke");
                        //vip图标
                        File vipicon = new File(MobileQQ + "/.vipicon");
                        //斗图相关
                        File DoutuRes = new File(MobileQQ + "/DoutuRes");
                        //QQ电话动画背景
                        File funcall = new File(MobileQQ + "/funcall");
                        //头像缓存
                        File head = new File(MobileQQ + "/head");
                        //热图?
                        File hotpic = new File(MobileQQ + "/hotpic");
                        //貌似也是表情
                        File pe = new File(MobileQQ + "/pe");
                        //暂时不知道的东西
                        File qav = new File(MobileQQ + "/qav");
                        //qq音乐
                        File qqmusic = new File(MobileQQ + "/qqmusic");
                        //接收文件缓存
                        File trooptmp = new File(QQfile_recv + "/trooptmp");
                        File tmp = new File(QQfile_recv + "/tmp");
                        File thumbnails = new File(QQfile_recv + "/thumbnails");

                        Utils.deleteAllFiles(font_info);
                        Utils.deleteAllFiles(hiboom_font);
                        Utils.deleteAllFiles(gift);
                        Utils.deleteAllFiles(pendant);
                        Utils.deleteAllFiles(profilecard);
                        Utils.deleteAllFiles(sticker_recommended_pics);
                        Utils.deleteAllFiles(enter_effects);
                        Utils.deleteAllFiles(vaspoke);
                        Utils.deleteAllFiles(newpoke);
                        Utils.deleteAllFiles(poke);
                        Utils.deleteAllFiles(vipicon);
                        Utils.deleteAllFiles(DoutuRes);
                        Utils.deleteAllFiles(funcall);
                        Utils.deleteAllFiles(head);
                        Utils.deleteAllFiles(hotpic);
                        Utils.deleteAllFiles(pe);
                        Utils.deleteAllFiles(qav);
                        Utils.deleteAllFiles(qqmusic);
                        Utils.deleteAllFiles(trooptmp);
                        Utils.deleteAllFiles(tmp);
                        Utils.deleteAllFiles(thumbnails);
                    }
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
