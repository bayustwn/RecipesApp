# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

#-keep class com.example.core.data.model.RecipesResponse {*;}
#-keep class com.example.core.data.model.RecipesResponse {*;}

#KOIN
-keep class com.example.core.di.** { *; }
-keep class org.koin.** { *; }

#RETROFIT
-keep interface com.example.core.data.remote.api.ApiService { *; }
-keep class retrofit2.** { *; }

#MODEL
-keep class com.example.core.domain.model.** { *; }
-keep class com.example.core.data.model.** { *; }

#GSON
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }

#VIEWMODEl
-keep class com.example.core.presentation.viewmodel.FavoriteViewModel { *; }
-keep class com.example.core.presentation.viewmodel.RecipesViewModel { *; }
-keep class com.example.core.presentation.state.RecipesUiState { *; }
-keep class com.example.core.presentation.state.RecipesUiState$* { *; }

