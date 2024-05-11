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
    "api",
    "legacy-format",
    "minimessage"
).forEach {
    include("$subProjectPrefix-$it")
    project(":$subProjectPrefix-$it").projectDir = file(it)
}

include("$subProjectPrefix-test-shared-classes")
project(":$subProjectPrefix-test-shared-classes").projectDir = file("test-shared-classes")
