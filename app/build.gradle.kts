plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 33  // '=' 연산자 사용

    defaultConfig {
        applicationId = "com.example.weatherapp"  // Kotlin DSL 스타일로 '=' 사용
        minSdk = 16  // '=' 연산자 사용
        targetSdk = 33  // '=' 연산자 사용
        versionCode = 1
        versionName = "1.0"
    }

    namespace = "com.example.weatherapp"  // Kotlin DSL에서 '=' 연산자로 네임스페이스 정의

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
