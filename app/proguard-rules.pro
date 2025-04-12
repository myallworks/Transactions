# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Retrofit does reflection on generic parameters
-keepattributes Signature
-keepattributes *Annotation*

# Keep Retrofit and OkHttp classes
-keep class com.squareup.okhttp.** { *; }
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }

# Keep our API response models
-keep class com.example.task1apitransactions.models.** { *; }

# For encrypted shared preferences
-keep class androidx.security.** { *; }
# Keep all model classes
-keep class com.example.task1apitransactions.models.** { *; }

# Keep all serialized fields
-keepclassmembers class * implements java.io.Serializable {
    *;
}

# Disable optimization temporarily
-dontoptimize