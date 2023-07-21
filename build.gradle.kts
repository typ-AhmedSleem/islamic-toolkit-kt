import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

allprojects {

    group = "com.typ.islamictkt"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

}