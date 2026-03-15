package org.sirox.ezprofile.database.manager

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.Bukkit
import org.sirox.ezprofile.EzProfile
import java.io.File
import javax.sql.DataSource

class DatabaseManager(dataFolder: File, plugin: EzProfile) {

    val dataSource: DataSource
    val prefix = plugin.configs.databaseConfig.prefix

    init {
        val type = plugin.configs.databaseConfig.type

        val host = plugin.configs.databaseConfig.database.host
        val port = plugin.configs.databaseConfig.database.port
        val name = plugin.configs.databaseConfig.database.name

        val dbConfig = HikariConfig().apply {
            when (type.lowercase()) {
                "h2" -> {
                    val dbFile = File("${plugin.dataFolder}/database", "database.db")

                    jdbcUrl = "jdbc:h2:file:${dbFile.absolutePath};MODE=MySQL"
                    driverClassName = "org.h2.Driver"
                }

                "mysql" -> {
                    jdbcUrl = "jdbc:mysql://${host}:${port}/${name}"
                    driverClassName = "com.mysql.cj.jdbc.Driver"

                    username = plugin.configs.databaseConfig.database.username
                    password = plugin.configs.databaseConfig.database.password
                }

                "mariadb" -> {
                    jdbcUrl = "jdbc:mariadb://${host}:${port}/${name}"
                    driverClassName = "com.mariadb.jdbc.Driver"

                    username = plugin.configs.databaseConfig.database.username
                    password = plugin.configs.databaseConfig.database.password
                }

                "postgresql" -> {
                    jdbcUrl = "jdbc:postgresql://${host}:${port}/${name}"
                    driverClassName = "org.postgresql.Driver"

                    username = plugin.configs.databaseConfig.database.username
                    password = plugin.configs.databaseConfig.database.password
                }

                else -> {
                    plugin.logger.warn("Unsupported database type: $type. Disabling plugin...")
                    Bukkit.getPluginManager().disablePlugin(plugin)
                }
            }

            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            poolName = "EzProfilePool"
        }

        dataSource = HikariDataSource(dbConfig)

        createTables()
    }

    private fun createTables() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("""
                    CREATE TABLE IF NOT EXISTS ${prefix}_players (
                        uuid VARCHAR(36) PRIMARY KEY,
                        name VARCHAR(16),
                        status VARCHAR(255)
                    )
                """.trimIndent())
            }
        }
    }

}