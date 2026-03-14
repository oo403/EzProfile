package org.sirox.ezprofile.database.manager

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.sirox.ezprofile.EzProfile
import java.io.File
import javax.sql.DataSource

class DatabaseManager(dataFolder: File, plugin: EzProfile) {

    val dataSource: DataSource

    init {
        val dbFile = File("${plugin.dataFolder}/database", "database.db")

        val dbConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:h2:file:${dbFile.absolutePath};MODE=MySQL"

            driverClassName = "org.h2.Driver"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            poolName = "h2"
        }

        dataSource = HikariDataSource(dbConfig)

        createTables()
    }

    private fun createTables() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("""
                    CREATE TABLE IF NOT EXISTS players (
                        uuid VARCHAR(36) PRIMARY KEY,
                        name VARCHAR(16),
                        likes INT,
                        dislikes INT
                    )
                """.trimIndent())
            }
        }
    }

}