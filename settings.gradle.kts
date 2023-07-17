pluginManagement {
    includeBuild("build-logic")

    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "messages"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

val subProjectPrefix = rootProject.name.lowercase(java.util.Locale.ENGLISH)

sequenceOf(
    "api"
).forEach {
    include("$subProjectPrefix-$it")
    project(":$subProjectPrefix-$it").projectDir = file(it)
}
