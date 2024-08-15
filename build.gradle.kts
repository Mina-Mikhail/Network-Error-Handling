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

import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.plugin) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.navigation.safeargs) apply false
  alias(libs.plugins.ktlint) apply false
  alias(libs.plugins.ksp) apply false
}

buildscript {
  apply("config.gradle.kts")
}

subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  configure<KtlintExtension> {
    debug.set(true)
    android.set(true)
    ignoreFailures.set(false)

    reporters {
      reporter(ReporterType.PLAIN)
      reporter(ReporterType.CHECKSTYLE)
    }

    kotlinScriptAdditionalPaths {
      include(fileTree("scripts/"))
    }

    filter {
      exclude("**/generated/**")
      include("**/kotlin/**")
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.layout.buildDirectory)
}