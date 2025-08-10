import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
    id("signing")
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
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

//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.lifecycle.runtime.compose)

    debugImplementation(libs.androidx.ui.tooling)
}

group = "io.github.zahid4kh"
version = libraryVersion

val secretPropsFile = rootProject.file("local.properties")
val secretProps = Properties()
if (secretPropsFile.exists()) {
    secretProps.load(FileInputStream(secretPropsFile))
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "io.github.zahid4kh"
            artifactId = "randomuistuff"
            version = libraryVersion

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Random UI Components")
                description.set("A collection of custom UI components for Android Jetpack Compose applications.")
                url.set("https://github.com/zahid4kh/UI-Components-For-Compose")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("zahid4kh")
                        name.set("Zahid Khalilov")
                        email.set("halilzahid@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:github.com/zahid4kh/UI-Components-For-Compose.git")
                    developerConnection.set("scm:git:ssh://github.com/zahid4kh/UI-Components-For-Compose.git")
                    url.set("https://github.com/zahid4kh/UI-Components-For-Compose/tree/main")
                }
            }
        }
    }
    repositories {
        maven {
            name = "CentralPortal"
            url = uri("https://central.sonatype.com/api/v1/publisher/deployments/upload/")
            credentials {
                username = secretProps.getProperty("sonatype.username", "")
                password = secretProps.getProperty("sonatype.password", "")
            }
        }
    }
}

signing {
    val isJitPackBuild = System.getenv("JITPACK") == "true"

    if (!isJitPackBuild) {
        val signingKeyFile = rootProject.file("private_key.gpg")
        val signingPassword = secretProps.getProperty("signing.password", "")

        if (signingKeyFile.exists() && signingPassword.isNotEmpty()) {
            useInMemoryPgpKeys(signingKeyFile.readText(), signingPassword)
            sign(publishing.publications["release"])
        } else {
            logger.warn("Signing not configured - missing signing.key or signing.password")
        }
    }
}

tasks.register<Zip>("createBundle") {
    dependsOn("clean", "assemble", "publishToMavenLocal", "generateChecksums")

    archiveFileName.set("randomuistuff-${libraryVersion}-bundle.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))

    from(file("${System.getProperty("user.home")}/.m2/repository")) {
        include("io/github/zahid4kh/randomuistuff/${libraryVersion}/**")
    }

    doLast {
        println("====================================")
        println("Bundle created at: ${archiveFile.get().asFile.absolutePath}")
        println("====================================")
    }
}


tasks.register("generateChecksums") {
    dependsOn("publishToMavenLocal", "signReleasePublication")

    doLast {
        Thread.sleep(1000)

        val repoDir = file("${System.getProperty("user.home")}/.m2/repository/io/github/zahid4kh/randomuistuff/${libraryVersion}")
        println("Generating checksums in directory: ${repoDir.absolutePath}")

        repoDir.listFiles()?.forEach { file ->
            if (file.isFile && !file.name.endsWith(".md5") && !file.name.endsWith(".sha1")) {
                println("Generating checksums for: ${file.name}")

                val md5File = File(file.absolutePath + ".md5")
                md5File.writeText(generateMD5(file))

                val sha1File = File(file.absolutePath + ".sha1")
                sha1File.writeText(generateSHA1(file))
            }
        }
    }
}

fun generateMD5(file: File): String {
    val md = MessageDigest.getInstance("MD5")
    file.inputStream().use { input ->
        val buffer = ByteArray(8192)
        var read: Int
        while (input.read(buffer).also { read = it } > 0) {
            md.update(buffer, 0, read)
        }
    }
    return md.digest().joinToString("") { "%02x".format(it) }
}

fun generateSHA1(file: File): String {
    val md = MessageDigest.getInstance("SHA-1")
    file.inputStream().use { input ->
        val buffer = ByteArray(8192)
        var read: Int
        while (input.read(buffer).also { read = it } > 0) {
            md.update(buffer, 0, read)
        }
    }
    return md.digest().joinToString("") { "%02x".format(it) }
}