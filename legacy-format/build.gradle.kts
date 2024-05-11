import codegen.arguments.ArgumentsGenerator

plugins {
    id("messages.common-conventions")
    id("messages.publication")
}

dependencies {
    api(projects.messagesApi)
    compileOnly(libs.adventure.serializer.legacy)

    testImplementation(projects.messagesTestSharedClasses)
    testImplementation(libs.adventure.serializer.legacy)
}

afterEvaluate {
    collector.JavadocAggregator.addProject(this)
}

tasks.javadoc {
    (options as StandardJavadocDocletOptions).links("https://jd.advntr.dev/text-serializer-legacy/${libs.versions.adventure.get()}/")
}

tasks {
    create("generateLegacyFormatArgumentsClass") {
        group = "codegen"
        doLast {
            val srcDir = project.layout.projectDirectory.dir("src/main/java")

            ArgumentsGenerator.Context(
                    packageName = "com.github.siroshun09.messages.legacyformat.arg",
                    className = "Arg#N",
                    numberOfArguments = 10,
                    messageBaseClassName = "LegacyFormatMessageBase",
                    replacementBaseClassName = "ReplacementBase",
                    returnStatement = "return LegacyFormatMessageBase.withReplacementBase(#T);",
                    additionalImports = sequenceOf(
                            "com.github.siroshun09.messages.legacyformat.base.LegacyFormatMessageBase",
                            "com.github.siroshun09.messages.legacyformat.base.ReplacementBase",
                    )
            ).create().gen(srcDir)
        }
    }
}
