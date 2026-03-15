package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class DatabaseConfig : OkaeriConfig() {

    @Comment("Database Type")
    @Comment("H2 / MYSQL / MARIADB / POSTGRESQL")
    var type: String = "H2"

    @Comment("Database Table Prefix")
    var prefix: String = "ezprofile"

    @Comment("","Database Login Credentials")
    var database: Database = Database()

    class Database : OkaeriConfig() {
        var host: String = "localhost"
        var port: Int = 8080
        var username: String = "root"
        var password: String = "root"
        var name: String = "root"
    }

}