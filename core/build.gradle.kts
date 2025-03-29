plugins {
    alias(libs.plugins.kotlin.android)
    id("com.android.library")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://dummyjson.com/\"")
            buildConfigField("String", "CERTIFICATE", "sha256/X3v3kN0IWM02QFIWfYdXyh9yxMBP5T6kEKpQhR7R4X0=")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField ("String", "BASE_URL", "\"https://dummyjson.com/\"")
            buildConfigField("String", "CERTIFICATE", "\"sha256/X3v3kN0IWM02QFIWfYdXyh9yxMBP5T6kEKpQhR7R4X0=\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures{
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        targetSdk = 35
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Koin
    api(libs.koin.core)
    api (libs.koin.android)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    debugImplementation(libs.leakcanary.android)
    api(libs.android.database.sqlcipher)
    api(libs.androidx.sqlite.ktx)

    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

}
