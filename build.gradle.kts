import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ktlintGradle)
}

//region Ktlint configuration
configure<KtlintExtension> {
    configure()
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ktlintGradle.get().pluginId)

    configure<KtlintExtension> {
        configure()
    }
}

fun KtlintExtension.configure() {
    version.set("1.6.0")
    android.set(true)
}
//endregion
