package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class DatabaseConfig : OkaeriConfig() {

    @Comment("Database Type")
    @Comment("H2 / MYSQL / MARIADB / POSTGRESQL")
    val type: String = "H2"

    @Comment("","Database Login Credentials")
    val database: Database = Database()

    class Database : OkaeriConfig() {
        val host: String = "localhost"
        val port: Int = 8080
        val username: String = "root"
        val password: String = "root"
        val name: String = "root"
    }

}