package org.sirox.ezprofile.database.manager

import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.database.data.PlayerData
import java.util.UUID
import javax.sql.DataSource

class PlayerDatabaseManager(private val plugin: EzProfile, private val dataSource: DataSource) {

    val prefix = plugin.configs.databaseConfig.prefix

    fun load(uuid: UUID): PlayerData? {
        dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT * FROM ${prefix}_players WHERE uuid = ?").use { ps ->
                ps.setString(1, uuid.toString())
                ps.executeQuery().use { rs ->
                    if (rs.next()) {
                        return PlayerData(
                            uuid,
                            rs.getString("name"),
                            rs.getString("status"),
                        )
                    }
                }
            }
        }
        return null
    }

    fun save(data: PlayerData) {
        dataSource.connection.use { connection ->
            val ps = connection.prepareStatement("""
                MERGE INTO ${prefix}_players (uuid, name, status)
                KEY (uuid)
                VALUES(?,?,?)
            """.trimIndent())

            ps.setString(1, data.uuid.toString())
            ps.setString(2, data.name)
            ps.setString(3, data.status)

            ps.executeUpdate()
            connection.commit()
        }
    }

    fun remove(uuid: UUID) {
        dataSource.connection.use { connection ->
            connection.prepareStatement("DELETE FROM ${prefix}_players WHERE uuid = ?").use { ps ->
                ps.setString(1, uuid.toString())

                ps.executeUpdate()
                connection.commit()
            }
        }
    }

}