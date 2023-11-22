import codegen.arguments.ArgumentsGenerator

plugins {
    id ("messages.common-conventions")
}

dependencies {
    api(projects.messagesApi)
    compileOnly(libs.adventure.minimessage)

    testImplementation(projects.messagesTestSharedClasses)
    testImplementation(libs.adventure.minimessage)
}

tasks {
    create("generateMiniMessageArgumentsClass") {
        group = "codegen"
        doLast {
            val srcDir = project.layout.projectDirectory.dir("src/main/java")

            ArgumentsGenerator.Context(
                    packageName = "com.github.siroshun09.messages.minimessage.arg",
                    className = "Arg#N",
                    numberOfArguments = 10,
                    messageBaseClassName = "MiniMessageBase",
                    replacementBaseClassName = "TagResolverBase",
                    returnStatement = "return MiniMessageBase.withTagResolverBase(#T);",
                    additionalImports = sequenceOf(
                            "com.github.siroshun09.messages.minimessage.base.MiniMessageBase",
                            "com.github.siroshun09.messages.minimessage.base.TagResolverBase"
                    )
            ).create().gen(srcDir)
        }
    }
}
