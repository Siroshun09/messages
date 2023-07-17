plugins {
    `java-library`
    id("messages.publication")
}

repositories {
    mavenCentral()
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

dependencies {
    compileOnly(libs.annotations)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    compileOnly(libs.adventure.serializer.legacy)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.adventure.api)
    testImplementation(libs.adventure.minimessage)
    testImplementation(libs.adventure.serializer.legacy)
}

val javaVersion = JavaVersion.VERSION_17
val charset = Charsets.UTF_8

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks {
    compileJava {
        options.encoding = charset.name()
        options.release.set(javaVersion.ordinal + 1)
    }

    javadoc {
        val opts = options as StandardJavadocDocletOptions

        opts.encoding = Charsets.UTF_8.name()
        opts.addStringOption("Xdoclint:none", "-quiet")
        opts.links(
            "https://jd.advntr.dev/api/${libs.versions.adventure.get()}/",
            "https://javadoc.io/doc/org.jetbrains/annotations/${libs.versions.annotations.get()}/",
        )
    }

    test {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
