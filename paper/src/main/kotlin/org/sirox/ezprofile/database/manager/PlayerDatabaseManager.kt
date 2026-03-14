package org.sirox.ezprofile.database.manager

import org.sirox.ezprofile.database.data.PlayerData
import java.util.UUID
import javax.sql.DataSource

class PlayerDatabaseManager(private val dataSource: DataSource) {

    fun load(uuid: UUID): PlayerData? {
        dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT * FROM players WHERE uuid = ?").use { ps ->
                ps.setString(1, uuid.toString())
                ps.executeQuery().use { rs ->
                    if (rs.next()) {
                        return PlayerData(
                            uuid,
                            rs.getString("name"),
                            rs.getInt("likes"),
                            rs.getInt("dislikes")
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
                MERGE INTO players (uuid, name, likes, dislikes)
                KEY (uuid)
                VALUES(?,?,?,?)
            """.trimIndent())

            ps.setString(1, data.uuid.toString())
            ps.setString(2, data.name)
            ps.setInt(3, data.likes)
            ps.setInt(4, data.dislikes)

            ps.executeUpdate()
            connection.commit()
        }
    }

}