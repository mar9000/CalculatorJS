import de.itemis.mps.gradle.BuildLanguages
import de.itemis.mps.gradle.GenerateLibrariesXml
import de.itemis.mps.gradle.TestLanguages

buildscript {
    repositories {
        maven {
            url = uri("https://artifacts.itemis.cloud/repository/maven-mps/")
        }
        mavenCentral()
    }
    dependencies {
        "classpath"(group = "de.itemis.mps", name = "mps-gradle-plugin", version = "1.16.352.17eae3e")
    }
}

plugins {
    id("java")
}

group = "org.mar9000"
version = "1.0-SNAPSHOT"

repositories {
    //mavenLocal()   // for development.
    mavenCentral()
    maven {
        url = uri("https://artifacts.itemis.cloud/repository/maven-mps/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
}

val mps = configurations.create("mps")
val antLib = configurations.create("antLib")
val mpsExt = configurations.create("mpsExt")
val libs = configurations.create("libs")

val mpsVersion: String by project
val mpsHomeDir = file("$buildDir/mps")
val mpsExtDir = file("$buildDir/mps-libs")
val libsDir = file("$rootDir/libs")

dependencies {
    mps("com.jetbrains:mps:$mpsVersion")
    mpsExt("com.github.mar9000:ecmascript4mps:mps-2022.3-SNAPSHOT")
    //mpsExt("org.mar9000:ecmascript4mps:2022.3.1")   // For development.
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.register("resolveAndLockAll") {
    doFirst {
        require(gradle.startParameter.isWriteDependencyLocks)
    }
    doLast {
        configurations.filter {
            // Add any custom filtering on the configurations to be resolved
            it.isCanBeResolved
        }.forEach { it.resolve() }
    }
}

ext["itemis.mps.gradle.ant.defaultScriptArgs"] = listOf("-Dmps_home=$mpsHomeDir", "-Dextensions.home=$mpsExtDir/de.itemis.mps.extensions")
ext["itemis.mps.gradle.ant.defaultScriptClasspath"] = files(antLib.resolve())

val resolveMps = tasks.register<Copy>("resolveMps") {
    from(mps.resolve().map {zipTree(it)})
    into(mpsHomeDir)
}

val resolveLibs = tasks.register<Copy>("resolveLibs") {
    doFirst{delete(libsDir)}
    from(libs.resolve())
    into(libsDir)
}

val resolveExt = tasks.register<Copy>("resolveExt") {
    from(mpsExt.resolve().map { zipTree(it) })
    into(mpsExtDir)
}

val createProjectLib = tasks.register<GenerateLibrariesXml>("generateLibs") {
    defaults = file("projectlibraries.properties")
    setOverrides(file("projectlibraries.overrides.properties"))
    destination = file(".mps/libraries.xml")
}

val setup = tasks.register("setup") {
    dependsOn(resolveExt)
    dependsOn(createProjectLib)
    dependsOn(resolveLibs)
}

val buildScriptWrapper = tasks.register<BuildLanguages>("buildScriptWrapper") {
    script = file("$projectDir/scripts/build-wrapper.xml")
    dependsOn(resolveMps)
    dependsOn(resolveExt)
}

val buildLanguages = tasks.register<BuildLanguages>("buildLanguages") {
    script = file("$projectDir/scripts/build.xml")
    dependsOn(buildScriptWrapper)
}

val testLanguages = tasks.register<TestLanguages>("testLanguages") {
    script = file("$projectDir/scripts/build-tests.xml")
    dependsOn(buildLanguages)
}
