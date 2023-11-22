plugins {
    alias(libs.plugins.aggregate.javadoc)
    alias(libs.plugins.javadoc.links)
}

repositories {
    mavenCentral()
}

dependencies {
    javadoc(projects.messagesApi)
    javadoc(projects.messagesLegacyFormat)
    javadoc(projects.messagesMinimessage)

    javadocClasspath(libs.annotations)
    javadocClasspath(libs.adventure.api)
    javadocClasspath(libs.adventure.minimessage)
    javadocClasspath(libs.adventure.serializer.legacy)
}

tasks {
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions)
                .docTitle("Messages $version")
                .windowTitle("Messages $version")
                .bottom("<p class=\"legalCopy\">Copyright &#169;2023 <a href=\"https://github.com/Siroshun09\">Siroshun09</a>.</p>")
    }
}
