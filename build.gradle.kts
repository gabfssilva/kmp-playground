plugins {
    id("root.publication")
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.complete.kotlin).apply(false)
    alias(libs.plugins.kotestMultiplatform).apply(false)
    alias(libs.plugins.kotlinNativeCocoapods).apply(false)
}
