-repackageclasses ""
-dontusemixedcaseclassnames

-keep class * implements de.robv.android.xposed.IXposedHookLoadPackage {
    public void *(de.robv.android.xposed.callbacks.XC_LoadPackage$LoadPackageParam);
}

-keep class * implements de.robv.android.xposed.IXposedHookInitPackageResources {
    public void *(de.robv.android.xposed.callbacks.XC_InitPackageResources$InitPackageResourcesParam);
}

# 保留主类
-keep public class me.kyuubiran.qqcleaner.MainActivity

-keepattributes RuntimeVisible*Annotations

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void check*(...);
    public static void throw*(...);
}

# 去除 DebugMetadataKt() 注释
-assumenosideeffects class kotlin.coroutines.jvm.internal.BaseContinuationImpl {
  java.lang.StackTraceElement getStackTraceElement() return null;
}
-assumenosideeffects public final class kotlin.coroutines.jvm.internal.DebugMetadataKt {
   private static final kotlin.coroutines.jvm.internal.DebugMetadata getDebugMetadataAnnotation(kotlin.coroutines.jvm.internal.BaseContinuationImpl) return null;
}

-allowaccessmodification
-overloadaggressively