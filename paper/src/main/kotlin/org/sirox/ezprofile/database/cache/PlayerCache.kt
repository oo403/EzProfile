package org.sirox.ezprofile.database.cache

import org.sirox.ezprofile.database.data.PlayerData
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class PlayerCache {

    private val playerCache = ConcurrentHashMap<UUID, PlayerData>()

    fun get(uuid: UUID) = playerCache[uuid]

    fun put(data: PlayerData) {
        playerCache[data.uuid] = data
    }

    fun remove(uuid: UUID) = playerCache.remove(uuid)

    fun all() = playerCache.values
}