# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/mauri/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class retrofit.** { *; }
-dontwarn sun.misc.Unsafe
-dontwarn retrofit.appengine.UrlFetchClient
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn retrofit.**
-dontwarn okio.**
-dontwarn rx.**
-dontwarn sun.misc.**
-dontwarn retrofit2.Platform$Java8
-dontwarn okhttp3.**

-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

# ALSO REMEMBER KEEPING YOUR MODEL CLASSES
-keep class com.mauri.android.flickrexample.models.** { *; }
