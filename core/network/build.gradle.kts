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

import com.android.build.api.variant.BuildConfigField

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.plugin)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
}

androidComponents {
  onVariants {
    it.buildConfigFields.apply {
      put(
        "BASE_URL",
        BuildConfigField(
          "String",
          "\"https://newsapi.org/v2/\"",
          "API Base Url"
        )
      )

      put(
        "API_TOKEN",
        BuildConfigField(
          "String",
          "\"5c203f74fdcc4265bca981fd059fee2c\"",
          "API Token"
        )
      )
    }
  }
}

android {
  compileSdk = rootProject.extra.get("compileSdk") as Int
  namespace = "com.minaMikhail.networkErrorHandling.network"

  defaultConfig {
    minSdk = rootProject.extra.get("minSdk") as Int
  }

  buildTypes {
    getByName("${rootProject.extra.get("releaseBuildType")}") {
      isMinifyEnabled = true
      consumerProguardFiles("${rootProject.extra.get("proguardFile")}")
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

  buildFeatures.buildConfig = true
}

dependencies {

  // Hilt
  implementation(libs.hilt)
  ksp(libs.hiltDaggerCompiler)

  // coroutines
  implementation(libs.coroutinesCore)
  implementation(libs.coroutinesAndroid)

  // Networking
  api(libs.bundles.networking)

  // Core Modules
  implementation(projects.core.utils)
}

// To run "ktlintFormat" before run the app
tasks.getByPath("preBuild").dependsOn("ktlintFormat")