plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization) // Kotlinx Serialization eklentisi eklendi
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.fitness_app_compose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fitness_app_compose"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
// -----------------------------------------------------------------
    // Bölüm 1: Ağ (Network)
    // API istekleri ve JSON işlemleri için
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Bölüm 2: Bağımlılık Enjeksiyonu (Dependency Injection)
    // Hilt ile bağımlılıkların yönetimi
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.material3)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Hilt'in Compose Navigation ile entegrasyonu

    // Bölüm 3: Veritabanı (Database)
    // Lokal veri depolama için Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Coroutines ve Flow desteği için
    ksp(libs.androidx.room.compiler)

    // Bölüm 4: Güvenlik (Security)
    // Şifrelenmiş SharedPreferences ve dosyalar için
    implementation(libs.androidx.security.crypto)

    // Bölüm 5: Kullanıcı Arayüzü (UI) - Jetpack Compose
    // Modern ve deklaratif UI oluşturmak için
    implementation(platform(libs.androidx.compose.bom)) // Kütüphane sürümlerini yönetmek için
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose) // Activity ile Compose entegrasyonu

    // Bölüm 6: Navigasyon (Navigation)
    // Compose'da ekranlar arası geçiş için
    implementation(libs.androidx.navigation.compose)

    // Bölüm 7: Android Çekirdek ve Yaşam Döngüsü (Core & Lifecycle)
    // Temel Android bileşenleri ve ViewModel
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Bölüm 8: Serileştirme (Serialization)
    // Kotlin nesnelerini JSON'a dönüştürmek için
    implementation(libs.kotlinx.serialization.json)

    // Bölüm 9: Test Kütüphaneleri
    // Birim ve UI testleri için
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Testler için de BOM kullanımı
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Bölüm 10: Hata Ayıklama (Debug)
    // Sadece debug derlemelerinde kullanılan araçlar
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}
// <-- YENİ: Eksik olan yeni Kotlin derleyici ayarları (jvmTarget) eklendi.
// Bu blok, dosyanın en sonunda, android { ... } ve dependencies { ... } bloklarının DIŞINDA olmalıdır.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}