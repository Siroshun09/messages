plugins {
    `java-library`
    `maven-publish`
    signing
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            java {
                withJavadocJar()
                withSourcesJar()
            }

            groupId = project.group.toString()
            artifactId = project.name

            from(components["java"])

            pom {
                url.set("https://github.com/Siroshun09/messages")
                licenses {
                    license {
                        name.set("APACHE LICENSE, VERSION 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        name.set("Siroshun09")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/Siroshun09/messages.git")
                    developerConnection.set("scm:git@github.com:Siroshun09/messages.git")
                    url.set("https://github.com/Siroshun09/messages")
                }

                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/Siroshun09/messages/issues")
                }

                ciManagement {
                    system.set("GitHub Actions")
                    url.set("https://github.com/Siroshun09/messages/runs")
                }
            }
        }

        repositories {
            maven {
                name = "mavenCentral"

                url = if (version.toString().endsWith("-SNAPSHOT")) {
                    uri("https://oss.sonatype.org/content/repositories/snapshots")
                } else {
                    uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                }
                credentials(PasswordCredentials::class)
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}
