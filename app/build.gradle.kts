plugins {
    alias(libs.plugins.android.application)         // Android
    alias(libs.plugins.kotlin.android)              // Kotlin
    alias(libs.plugins.kotlin.parcelize)            // parcelize
    alias(libs.plugins.hilt.android)                // 应用 Hilt 核心插件
    alias(libs.plugins.hilt.plugin)                 // 应用 Hilt KAPT/KSAP 插件
    kotlin("kapt")                                  // 启用 Kotlin 注解处理（KAPT）
//    alias(libs.plugins.ksp)                       // Ksp
    /***
    a. 必须保持此顺序，插件之间有相关依赖关系
    b. 关于alias和id导入方式
        可以使用alias别名导入，具体导入变量更具libs.version.toml来定义
        另外还可以通过id导入，导入方式分为两种
        1. id("org.jetbrains.kotlin.kapt") 直接采用包名方式
        2. id ("kotlin-kapt") 别名方式，一样取决于libs.versions.toml中的定义
     */

    /***
     * 从 Hilt 2.44 开始支持 KSP，只需替换依赖：
     */

    /***
     * 在 Gradle 项目中，alias(libs.plugins.hilt.plugin)
     * 不需要在根项目的 build.gradle.kts 中声明（即不需要写 apply false），
     * 而 alias(libs.plugins.hilt.android) 需要声明，这是由 Hilt 插件的特殊设计 和 Gradle 插件管理机制 决定的。
     *
     * 核心区别：
     * com.google.dagger.hilt.android（主插件）是 功能插件（提供 Hilt 的依赖注入能力）。
     * dagger.hilt.android.plugin 是 辅助插件，仅用于配置注解处理器（KAPT/KSP），不直接提供新功能。
     *
     * 事实：只有需要统一版本或多模块共享的插件才需声明（如 com.android.application、hilt.android）。
     * 工具插件（如 hilt.plugin、ksp）通常直接应用即可。
     */
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
        //noinspection DataBindingWithoutKapt
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
//    implementation (libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.lifecycle.livedata.core.ktx)
//    implementation(libs.androidx.lifecycle.livedata.ktx)
//    implementation(libs.androidx.lifecycle.livedata)
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.lifecycle.livedata.ktx)  // 包含Transformations
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel支持

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
    kapt(libs.hilt.compiler)

//    implementation(libs.androidx.hilt.lifecycle.viewmodel)
//    kapt(libs.androidx.hilt.compiler)

}

afterEvaluate {
    println("已加载插件：${project.plugins.map { it.javaClass.simpleName }}")
}


// Hilt 专用配置 (必须放在文件底部)
kapt {
    correctErrorTypes = true
}