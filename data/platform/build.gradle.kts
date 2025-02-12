plugins {
    id("com.android.library")
    id("com.kafka.kotlin.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.base.annotations)
                api(projects.core.networking)
                implementation(projects.data.models)
                implementation(projects.data.prefs)

                implementation(libs.ktor.client.core)
            }
        }

        val jvmCommon by creating {
            dependsOn(commonMain)
        }

        val jvmMain by getting {
            dependsOn(jvmCommon)
        }

        val androidMain by getting {
            dependsOn(jvmCommon)

            dependencies {
                implementation(project.dependencies.platform(libs.google.bom))
                implementation(libs.google.auth)
            }
        }
    }
}

android {
    namespace = "com.kafka.data.platform"
}
