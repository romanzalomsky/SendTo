plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.zalomsky.sendto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zalomsky.sendto"
        minSdk = 24
        targetSdk = 33
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
    kapt{
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")

    //ViewModel
    implementation ("androidx.compose.runtime:runtime-livedata:1.0.0-beta01")

    //Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.0")

    //Paging
    implementation("androidx.paging:paging-compose:3.2.0")
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")

    //Room
    kapt ("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.room:room-runtime:2.5.1")
    implementation ("androidx.room:room-ktx:2.5.1")
    testImplementation ("androidx.room:room-testing:2.5.1")
    annotationProcessor ("androidx.room:room-compiler:2.5.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    //Firebase

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    
    // For Card view
    implementation ("androidx.cardview:cardview:1.0.0")

    // Chart and graph library
    implementation ("com.github.blackfizz:eazegraph:1.2.5l@aar")
    implementation ("com.nineoldandroids:library:2.4.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}