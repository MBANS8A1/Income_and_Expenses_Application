import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}



configurations.all{
    exclude(group = "com.intellij",module ="annotations")
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

    packaging{
        resources{
            excludes += ("/META-INF/{AL2.0,LGPL2.1}")
        }
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
    implementation (libs.accompanist.systemuicontroller)

    // Extended Icons
    implementation (libs.androidx.material.icons.extended)

    // Navigation Compose
    val navVersion = "2.9.2" // Use the latest version
    implementation(libs.androidx.navigation.compose)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    val roomVersion = "2.7.2"
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")
    //implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    //implementation(libs.androidx.room.compiler)
    testImplementation (libs.room.testing)
    androidTestImplementation (libs.room.testing)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    //implementation("com.google.dagger:hilt-android-compiler:2.56.2")
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation (libs.androidx.hilt.navigation.compose)


}


