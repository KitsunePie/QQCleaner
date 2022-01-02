-repackageclasses ""
-dontusemixedcaseclassnames

-keep class me.kyuubiran.qqcleaner.HookEntry {
    <init>();
}

# 保留主类
-keep public class me.kyuubiran.qqcleaner.MainActivity

-keepattributes RuntimeVisible*Annotations

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