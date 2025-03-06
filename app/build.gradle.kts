plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.foodexpress_2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodexpress_2"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation ("com.github.bumptech.glide:glide:4.14.2")

    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("androidx.viewpager2:viewpager2:1.1.0")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation ("com.github.ivbaranov:materialfavoritebutton:0.1.4")
    implementation ("androidx.palette:palette:1.0.0")
}