// build.gradle.kts generated by Skip for SkipModel
dependencies {
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    testImplementation(testLibs.json)
    testImplementation(testLibs.robolectric)
    testImplementation(testLibs.androidx.test.core)
    testImplementation(testLibs.androidx.test.rules)
    testImplementation(testLibs.androidx.test.ext.junit)
    androidTestImplementation(testLibs.androidx.test.core)
    androidTestImplementation(testLibs.androidx.test.rules)
    androidTestImplementation(testLibs.androidx.test.ext.junit)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.runtime)
    api(project(":SkipFoundation"))
    api(project(":SkipLib"))
    testImplementation(project(":SkipUnit"))
    androidTestImplementation(project(":SkipUnit"))
}

tasks.withType<Test>().configureEach {
    systemProperties.put("robolectric.logging", "stdout")
    systemProperties.put("robolectric.graphicsMode", "NATIVE")
    testLogging {
        this.showStandardStreams = true
    }
}

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    id("maven-publish")
}

kotlin {
}

android {
    namespace = group as String
    compileSdk = libs.versions.android.sdk.compile.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.sdk.min.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    publishing {
        multipleVariants {
            allVariants()
            withSourcesJar()
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions {
        suppressWarnings = true
    }
}

publishing {
    publications {
        register<MavenPublication>("default") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            afterEvaluate { from(components["default"]) }
        }
    }
}
