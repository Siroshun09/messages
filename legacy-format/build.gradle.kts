plugins {
    id ("messages.common-conventions")
}

dependencies {
    api(projects.messagesApi)
    compileOnly(libs.adventure.serializer.legacy)

    testImplementation(projects.messagesTestSharedClasses)
    testImplementation(libs.adventure.serializer.legacy)
}
