plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.goeco_amazon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.goeco_amazon"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.annotations)
    debugImplementation(libs.chuck.debug)
    releaseImplementation(libs.chuck.release)
    implementation(libs.glide)
    implementation(libs.picasso)
    implementation(libs.lottie)

    implementation(libs.play.services.location)
    implementation(libs.androidx.localbroadcastmanager)






}