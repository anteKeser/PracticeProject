import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.composeHotReload)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

kotlin {
  androidTarget { compilerOptions { jvmTarget.set(JvmTarget.JVM_11) } }

  listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  jvm()

  sourceSets {
    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)

      implementation(libs.ktor.client.okhttp)
    }
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
      implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")

      implementation(libs.koin.compose)
      implementation(libs.koin.core)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.koin.compose.viewmodel.navigation)

      api(libs.koin.annotations)

      implementation(libs.bundles.ktor)

      implementation(libs.jetbrains.compose.navigation)

      implementation(libs.coil.compose)
      implementation(libs.coil.network)
    }
    commonTest.dependencies { implementation(libs.kotlin.test) }
    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutinesSwing)

      implementation(libs.ktor.client.okhttp)
    }
    nativeMain.dependencies { implementation(libs.ktor.client.darwin) }
  }

  sourceSets.named("commonMain").configure {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
  }

  sourceSets.named("androidMain").configure {
    kotlin.srcDir("build/generated/ksp/android/androidDebug/kotlin")
  }

  sourceSets.named("jvmMain").configure { kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin") }

  sourceSets.named("iosArm64Main").configure {
    kotlin.srcDir("build/generated/ksp/iosArm64/iosArm64Main/kotlin")
  }

  sourceSets.named("iosSimulatorArm64Main").configure {
    kotlin.srcDir("build/generated/ksp/iosSimulatorArm64/iosSimulatorArm64Main/kotlin")
  }
}

ksp {
  arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
  arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
  add("kspCommonMainMetadata", libs.koin.ksp.compiler)
  add("kspAndroid", libs.koin.ksp.compiler)
  add("kspIosArm64", libs.koin.ksp.compiler)
  add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
  add("kspJvm", libs.koin.ksp.compiler)
}

// Ensure KSP runs before compilation for all targets
tasks.configureEach {
  if (name == "compileKotlinMetadata" || name == "compileCommonMainKotlinMetadata") {
    dependsOn("kspCommonMainKotlinMetadata")
  }
  if (name.startsWith("compile") && name.contains("Kotlin") && !name.contains("Test")) {
    dependsOn("kspCommonMainKotlinMetadata")
  }
  // Make platform-specific KSP tasks depend on common metadata KSP
  if (name.startsWith("ksp") && name != "kspCommonMainKotlinMetadata" && !name.contains("Test")) {
    dependsOn("kspCommonMainKotlinMetadata")
  }
}

android {
  namespace = "com.example.apiproject"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.example.apiproject"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"
  }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
  buildTypes { getByName("release") { isMinifyEnabled = false } }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies { debugImplementation(compose.uiTooling) }

compose.desktop {
  application {
    mainClass = "com.example.apiproject.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "com.example.apiproject"
      packageVersion = "1.0.0"
    }
  }
}
