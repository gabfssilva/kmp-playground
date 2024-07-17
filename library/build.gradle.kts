import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotestMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("module.publication")
    alias(libs.plugins.testLogger)
    alias(libs.plugins.kotlinNativeCocoapods)
}

kotlin {
    applyDefaultHierarchyTemplate()

    iosArm64()
    iosSimulatorArm64()
   
    linuxArm64() 
    
    js(IR) {
        browser()
        nodejs()
    }

    macosArm64 {
        val main by compilations.getting {
            cinterops {
                val base64 by creating {
                    defFile(project.file("src/nativeInterop/cinterop/base64.def"))
                    compilerOpts("-Isrc/nativeInterop/cinterop")
                    linkerOpts("-Lsrc/nativeInterop/cinterop/base64/src/", "-lcencode", "-lcdecode")
                }
            }
        }
    }

//    cocoapods {
//        ios.deploymentTarget = "13.5"
//        osx.deploymentTarget = "10.0"
//        tvos.deploymentTarget = "13.4"
//        watchos.deploymentTarget = "6.2"
//
//        pod("UUIDSwift")
//    }

    jvm {
        compilations.all {
            kotlinOptions { jvmTarget = "17" }
        }
    }

    androidTarget {
        publishLibraryVariants("release")

        compilations.all { kotlinOptions { jvmTarget = "1.8" } }
    }


    sourceSets {

        jsMain {
            dependencies {
                implementation(npm("is-sorted", "1.0.5"))
            }
        }

        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.framework.datatest)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}

configure<TestLoggerExtension> {
    theme = ThemeType.MOCHA
    showExceptions = true
    showStackTraces = true
    showFullStackTraces = false
    showCauses = true
    slowThreshold = 10000
    showSummary = true
    showSimpleNames = false
    showPassed = true
    showSkipped = true
    showFailed = true
    showStandardStreams = false
    showPassedStandardStreams = true
    showSkippedStandardStreams = true
    showFailedStandardStreams = true
    logLevel = LogLevel.LIFECYCLE
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        languageVersion = "1.9"
        apiVersion = "1.9"
    }
}

android {
    namespace = "org.jetbrains.kotlinx.multiplatform.library.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    testOptions { unitTests.all { it.useJUnitPlatform() } }
}
