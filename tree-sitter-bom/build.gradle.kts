plugins {
    `java-library`
    `maven-publish`
    signing
}

dependencies {
    constraints {
        project.rootProject.subprojects.filterNot(project::equals).forEach { subproject ->
            api(subproject)
        }
    }
}

publishing {
    // tree-sitter POM to copy base values
    val base = project.rootProject.subprojects.filter { p -> p.name == "tree-sitter" }[0]
        .publishing.publications["maven"] as MavenPublication

    publications {
        create<MavenPublication>("maven") {
            groupId = base.groupId
            artifactId = project.name
            version = base.version
            pom {
                description = base.pom.description.get() + " (BOM)"
                packaging = "pom" // Create Maven BOM
                url = base.pom.url
                licenses {
                    license {
                        name = "MIT"
                    }
                }
                developers {
                    developer {
                        id = "bonede"
                        name = "Wang Liang"
                        email = "bonede@qq.com"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/bonede/tree-sitter-ng.git"
                    developerConnection = "scm:git:https://github.com/bonede/tree-sitter-ng.git"
                    url = "https://github.com/bonede/tree-sitter-ng"
                }
            }
            from(components["java"])
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
