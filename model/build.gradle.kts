plugins {
    // 必须保持此顺序
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)          // Kotlin 插件 (提供 kapt)
    alias(libs.plugins.kotlin.parcelize)        // parcelize
    alias(libs.plugins.hilt.android)            // 应用 Hilt 核心插件
    alias(libs.plugins.hilt.plugin)             // 应用 Hilt KAPT/KSAP 插件
    kotlin("kapt")                              // 启用 Kotlin 注解处理（KAPT）
}

android {
    namespace = "com.example.model"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
//        compose = true
        buildConfig = true
    }
}

dependencies {

    // core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt ksp编译性能更好
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
//    kapt(libs.hilt.android.compiler)

    // databinding
    implementation (libs.androidx.databinding.runtime)

    // lifecycle
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.livedata.core.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.livedata)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.squareup.retrofit2)
    implementation (libs.retrofit2.converter.moshi)
    implementation (libs.moshi)
    implementation (libs.logging.interceptor)
    implementation (libs.adapter.rxjava2)
    implementation (libs.com.google.code.gson.gson4)

    // timber
    implementation (libs.timber)

    // room
    //noinspection GradleDependency
    implementation (libs.room.runtime)
//    ksp(libs.room.compiler)
    implementation (libs.androidx.room.ktx)

}

// Hilt 专用配置 (必须放在文件底部)
kapt {
    correctErrorTypes = true
}