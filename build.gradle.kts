// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.symbol.processing.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply(false)
    alias(libs.plugins.safeargs.kotlin) apply(false)
    alias(libs.plugins.daggerHiltAndroid) apply(false)
    alias(libs.plugins.ksp) apply(false)
}