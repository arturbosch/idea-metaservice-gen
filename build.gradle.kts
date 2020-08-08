group = "io.abosch.idea.metaservice.gen"
version = "1.0.0"

repositories {
    jcenter()
    maven { setUrl("http://dl.bintray.com/jetbrains/intellij-plugin-service") }
}

plugins {
    id("org.jetbrains.intellij") version "0.4.21"
    id("com.github.ben-manes.versions") version "0.28.0"
    kotlin("jvm").version("1.3.72")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<org.jetbrains.intellij.tasks.PublishTask> {
    setToken(System.getenv("DETEKT_INTELLIJ_PLUGINS_TOKEN"))
}

intellij {
    version = "2019.3"
    setPlugins("IntelliLang", "Kotlin", "com.intellij.java")
    updateSinceUntilBuild = false
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      First release.<br>
    """)
}
