plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.legalist.aylindesign"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.legalist.aylindesign"
        minSdk = 27
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {

        viewBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data"))
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
//navgation
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")

    //animation
    implementation ("com.airbnb.android:lottie:5.0.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Volley api request
    implementation ("com.android.volley:volley:1.2.1")
    // Koin Dependency injection
    implementation ("io.insert-koin:koin-android:3.2.0")
}