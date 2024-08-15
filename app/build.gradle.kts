/*
 * Copyright 2024 Mina Mikhail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.plugin)
  alias(libs.plugins.hilt)
  alias(libs.plugins.navigation.safeargs)
  alias(libs.plugins.ksp)
}

android {
  compileSdk = rootProject.extra.get("compileSdk") as Int
  namespace = "com.minaMikhail.networkErrorHandling"

  defaultConfig {
    applicationId = rootProject.extra["applicationId"] as String
    minSdk = rootProject.extra.get("minSdk") as Int
    targetSdk = rootProject.extra.get("compileSdk") as Int
    versionCode = rootProject.extra.get("versionCode") as Int
    versionName = rootProject.extra["versionName"] as String

    testInstrumentationRunner = rootProject.extra["testRunner"] as String
  }

  buildTypes {
    getByName("${rootProject.extra.get("releaseBuildType")}") {
      proguardFiles(
        getDefaultProguardFile("proguard-android.txt"),
        "${rootProject.extra.get("proguardFile")}"
      )

      isMinifyEnabled = true
      isShrinkResources = true
      isDebuggable = false
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  lint {
    quiet = true
    abortOnError = false
    warningsAsErrors = true
    disable += "Instantiatable"
  }

  kotlinOptions.jvmTarget = "1.8"

  viewBinding.isEnabled = true

  hilt.enableAggregatingTask = true
}

dependencies {

  // Hilt
  implementation(libs.hilt)
  ksp(libs.hiltDaggerCompiler)

  // Core Modules
  implementation(projects.core.ui)

  // Features
  implementation(projects.featureHome)

  // Testing
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  testImplementation(libs.kotlinFixture)
}

// To run "ktlintFormat" before run the app
tasks.getByPath("preBuild").dependsOn("ktlintFormat")