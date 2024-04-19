import collector.JavadocAggregator

tasks {
    register<Delete>("clean") {
        group = "build"
        layout.buildDirectory.get().asFile.deleteRecursively()
    }

    create<Javadoc>(JavadocAggregator.AGGREGATE_JAVADOC_TASK_NAME) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        setDestinationDir(layout.buildDirectory.dir("docs").get().asFile)
        classpath = objects.fileCollection()
        doFirst {
            include(JavadocAggregator.includes)
            exclude(JavadocAggregator.excludes)

            val opts = options as StandardJavadocDocletOptions
            opts.docTitle("messages $version")
                .windowTitle("messages $version")
                .links(*JavadocAggregator.javadocLinks.toTypedArray())
                .bottom("<p class=\"legalCopy\">Copyright &#169;2023 <a href=\"https://github.com/Siroshun09\">Siroshun09</a>.</p>")

            opts.addStringOption("Xmaxwarns", Int.MAX_VALUE.toString())
        }
    }
}
