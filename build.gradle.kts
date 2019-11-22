import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }

    val kotlinVersion: String by project
    val bintray: String by project
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

allprojects {
    repositories {
        jcenter()
    }
}

subprojects {
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
        plugin("maven-publish")
    }

    val artifactPublish: String by project
    val artifactGroupId: String by project

    version = artifactPublish
    group = artifactGroupId

}
