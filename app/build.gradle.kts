plugins {
    // 必须保持此顺序
    alias(libs.plugins.android.application)         // Android 插件
    alias(libs.plugins.kotlin.android)              // Kotlin 插件 (提供 kapt)
    alias(libs.plugins.hilt.android)                // Hilt 插件
    alias(libs.plugins.kotlin.kapt)                 // Kapt插件
    alias(libs.plugins.ksp)                         // Ksp插件
    alias(libs.plugins.kotlin.parcelize)            // parcelize插件
//    id ("kotlin-kapt")
//    alias(libs.plugins.compose.compiler)          // compose 插件

//    id("kotlin-kapt")  // 启用 Kotlin 注解处理器
//    id("com.google.dagger.hilt.android")  // Hilt 插件

}

android {
    namespace = "com.example.kotlinsample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kotlinsample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":model"))

    // databind
    implementation (libs.androidx.databinding.runtime)

    // lifecycle
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.livedata)

    // retrofit
    implementation (libs.squareup.retrofit2)
    implementation (libs.retrofit2.converter.moshi)
    implementation (libs.moshi)
    implementation (libs.logging.interceptor)

    // timber
    implementation (libs.timber)

    // compose
    implementation (libs.androidx.constraintlayout.compose)
    implementation (libs.androidx.recyclerview)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

//    implementation(libs.androidx.hilt.lifecycle.viewmodel)
//    kapt(libs.androidx.hilt.compiler)

}

afterEvaluate {
    println()
    println("已加载插件：${project.plugins.map { it.javaClass.simpleName }}")
}


// Hilt 专用配置 (必须放在文件底部)
kapt {
    correctErrorTypes = true
}