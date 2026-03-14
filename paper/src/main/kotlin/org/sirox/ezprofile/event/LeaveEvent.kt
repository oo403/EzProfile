package org.sirox.ezprofile.event

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.database.cache.PlayerCache
import org.sirox.ezprofile.database.manager.PlayerDatabaseManager

class LeaveEvent(private val plugin: EzProfile, private val database: PlayerDatabaseManager, private val cache: PlayerCache) : Listener {

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId

        val data = cache.remove(uuid) ?: return

        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            database.save(data)
        })
    }

}