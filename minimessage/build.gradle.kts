plugins {
    id ("messages.common-conventions")
}

dependencies {
    api(projects.messagesApi)
    compileOnly(libs.adventure.minimessage)

    testImplementation(projects.messagesTestSharedClasses)
    testImplementation(libs.adventure.minimessage)
}
