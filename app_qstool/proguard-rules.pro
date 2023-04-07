#----------------------基本指令区-----------------
# 设置混淆的压缩比率 0 ~ 7
-optimizationpasses 7
# 混淆后类名都为小写   Aa aA
-dontusemixedcaseclassnames
# 不优化输入的类文件
-dontoptimize
#不混淆泛型
-keepattributes Signature
#保持反射不被混淆
-keepattributes EnclosingMethod
#保持异常不被混淆
-keepattributes Exceptions
#避免混淆注解类
-dontwarn android.annotation
-keepattributes *Annotation*
#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#避免混淆内部类
-keepattributes InnerClasses
# 混淆时不记录日志
-verbose
# 保留代码行号，方便异常信息的追踪
-keepattributes SourceFile,LineNumberTable
# 混淆采用的算法.
-optimizations !code/simplification/arithmetic,!field/,!class/merging/,!code/allocation/variable
# seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt
# usage.txt文件列出从apk中删除的代码
-printusage unused.txt
# mapping文件列出混淆前后的映射
-printmapping mapping.txt
# 忽略警告 不加的话某些情况下打包会报错中断
-ignorewarnings


#表示不混淆IInterface实现类，
-keep class * implements android.os.IInterface { *; }

#表示不混淆Parcelable实现类中的CREATOR字段，
#毫无疑问，CREATOR字段是绝对不能改变的，包括大小写都不能变，不然整个Parcelable工作机制都会失败。
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}

# 不混淆Serializable和它的实现子类、其成员变量
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
}

# serializer
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.baimsg.**$$serializer { *; }
-keepclassmembers class com.baimsg.** {
    *** Companion;
}
-keepclasseswithmembers class com.baimsg.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep @kotlinx.serialization.Serializer public class *

-keep class kotlin.Metadata { *; }

# end serializer

# web
-keepattributes JavascriptInterface

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# 避免混淆所有native的方法,涉及到C、C++
-keepclasseswithmembernames class * {
    native <methods>;
}

# 避免混淆枚举类
-keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
}


# project
