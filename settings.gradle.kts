pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Moises AI Challenge"

include(":domain:music")

include(":data:itunes:music")

include(":presentation:splash")
include(":presentation:songs")
include(":presentation:song:item")
include(":presentation:album:details")
include(":presentation:theme")

include(":app")
