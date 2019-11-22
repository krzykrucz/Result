import com.jfrog.bintray.gradle.BintrayExtension
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
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:$bintray")
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
        plugin("com.jfrog.bintray")
        plugin("jacoco")
    }

    val artifactPublish: String by project
    val artifactGroupId: String by project

    version = artifactPublish
    group = artifactGroupId

    // bintray
    configure<BintrayExtension> {
        user = findProperty("BINTRAY_USER") as? String
        key = findProperty("BINTRAY_KEY") as? String
        setPublications(project.name)
        publish = true
        pkg.apply {
            repo = "maven"
            name = "Result"
            desc = "The modelling for success/failure of operations in Kotlin"
            userOrg = "kittinunf"
            websiteUrl = "https://github.com/kittinunf/Result"
            vcsUrl = "https://github.com/kittinunf/Result"
            setLicenses("MIT")
            version.apply {
                name = artifactPublish
            }
        }
    }

    // jacoco
    configure<JacocoPluginExtension> {
        toolVersion = extra.get("jacoco") as String
    }

    tasks.withType<JacocoReport> {
        reports {
            html.isEnabled = true
            xml.isEnabled = true
            csv.isEnabled = false
        }
    }
}
