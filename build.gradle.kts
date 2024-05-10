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
  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.cio)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.koilinx.json)

  testImplementation(libs.kotest.runner.junit5)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.property)
}

tasks.test {
  useJUnitPlatform()
}