plugins {
    id("com.android.library")
}
// 必须在 android 上面
apply("../publish-mavencentral.gradle")

android {
    namespace = "com.loper7.miit_rule_checker"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("top.canyie.pine:core:0.2.6")
}