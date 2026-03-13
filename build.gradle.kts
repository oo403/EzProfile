allprojects {
    group = "org.sirox"
    version = "1.0"
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc-repo"
        }
        maven("https://repo.extendedclip.com/releases/")

    }

}