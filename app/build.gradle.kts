plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
    id("com.google.gms.google-services")
    id("com.google.firebase.firebase-perf")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packaging {
        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")
    }
    tasks.withType<Test> {
        jvmArgs("-noverify")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.espresso.intents)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    
    implementation(libs.material.v120)
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Declare the dependency for the App Indexing library and specify a version
    // This specified library version overrides the version designated in the BoM.
    implementation(libs.firebase.appindexing)
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.google.android.gms:play-services-maps:17.0.0")
    implementation ("com.google.code.gson:gson:2.8.8");
    implementation ("com.google.maps.android:android-maps-utils:2.2.6");
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation ("com.google.firebase:firebase-messaging:22.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.google.firebase:firebase-storage")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    androidTestImplementation("org.mockito:mockito-android:3.5.15")
    testImplementation ("org.robolectric:robolectric:4.12.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-perf")

}
