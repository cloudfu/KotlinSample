pluginManagement {
    repositories {
//        maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
//        maven { url = uri("https://plugins.gradle.org/m2/") }
//        maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
//        maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/jcenter") }
//        maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/google") }
//        maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin") }

        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

//    resolutionStrategy {
//        eachPlugin {
//            when (requested.id.id) {
//                "com.android.application" ->  useVersion(libs.versions.agp.get())
//                "org.jetbrains.kotlin.android" -> useVersion(libs.versions.kotlin.get())
//            }
//        }
//    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
        maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/jcenter") }
        maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/google") }
        maven { url = uri("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin") }

        google()
        mavenCentral()
    }
//    versionCatalogs {
//        create("libs") {
//            from(files("./gradle/libs.versions.toml"))
//        }
//    }
}

rootProject.name = "KotlinSample"
include(":app")
include(":model")
