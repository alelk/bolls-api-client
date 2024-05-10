plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.serialization)
}

group = "io.github.alelk.bolls-api-client"
version = "0.0.1-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {

  testImplementation(libs.kotest.runner.junit5)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.property)
}

tasks.test {
  useJUnitPlatform()
}