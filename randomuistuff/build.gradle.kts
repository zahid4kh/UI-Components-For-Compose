import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish") version "0.34.0"
}

val libraryVersion = project.findProperty("LIBRARY_VERSION") as String? ?: "1.0.0"

android {
    namespace = "zahid4kh.randomuistuff"
    compileSdk = 36

    defaultConfig {
        minSdk = 31
        buildConfigField("String", "VERSION_NAME", "\"$libraryVersion\"")
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin{
        kotlinOptions {
            compilerOptions{
                jvmTarget = JvmTarget.JVM_17
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("io.github.zahid4kh", "randomuistuff", libraryVersion)

    pom {
        name.set("Random UI Components")
        description.set("A collection of custom UI components for Android Jetpack Compose applications.")
        inceptionYear.set("2025")
        url.set("https://github.com/zahid4kh/UI-Components-For-Compose")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("zahid4kh")
                name.set("Zahid Khalilov")
                url.set("https://github.com/zahid4kh")
            }
        }

        scm {
            url.set("https://github.com/zahid4kh/UI-Components-For-Compose")
            connection.set("scm:git:git://github.com/zahid4kh/UI-Components-For-Compose.git")
            developerConnection.set("scm:git:ssh://git@github.com/zahid4kh/UI-Components-For-Compose.git")
        }
    }
}