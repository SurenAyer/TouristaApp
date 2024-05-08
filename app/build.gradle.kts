plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.touristaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.touristaapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildFeatures {
        buildConfig = true
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
    packaging {
        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    
    implementation(libs.material.v120)
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Declare the dependency for the App Indexing library and specify a version
    // This specified library version overrides the version designated in the BoM.
    implementation(libs.firebase.appindexing)
    implementation("com.google.android.material:material:1.2.0")
    implementation("com.google.android.gms:play-services-maps:17.0.0")
    implementation ("com.google.code.gson:gson:2.8.8");
}

