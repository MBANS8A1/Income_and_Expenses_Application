plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.income_and_expenses_application"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.income_and_expenses_application"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Accompanist
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.5-beta")

    // Extended Icons
    implementation ("androidx.compose.material:material-icons-extended")

    // Navigation Compose
    val navVersion = "2.9.2" // Use the latest version
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    val roomVersion = "2.5.1"
    implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-compiler:$roomVersion")
    testImplementation ("androidx.room:room-testing:$roomVersion")
    androidTestImplementation ("androidx.room:room-testing:$roomVersion")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    implementation("com.google.dagger:hilt-android-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")


}