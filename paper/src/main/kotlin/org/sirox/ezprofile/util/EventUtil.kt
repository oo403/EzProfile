package org.sirox.ezprofile.util

import org.bukkit.Bukkit
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.database.cache.PlayerCache
import org.sirox.ezprofile.database.manager.PlayerDatabaseManager
import org.sirox.ezprofile.event.JoinEvent
import org.sirox.ezprofile.event.LeaveEvent
import org.sirox.ezprofile.event.ShiftRightEvent

class EventUtil(private val plugin: EzProfile, private val database: PlayerDatabaseManager, private val cache: PlayerCache) {

    fun registerEvents() {
        listOf(ShiftRightEvent(plugin),
            JoinEvent(plugin, database, cache),
            LeaveEvent(plugin, database, cache)
        ).forEach { it ->
            Bukkit.getPluginManager().registerEvents(it, plugin)
        }
    }

}