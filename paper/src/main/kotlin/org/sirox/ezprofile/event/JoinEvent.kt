package org.sirox.ezprofile.event

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.database.cache.PlayerCache
import org.sirox.ezprofile.database.data.PlayerData
import org.sirox.ezprofile.database.manager.PlayerDatabaseManager

class JoinEvent(private val plugin: EzProfile, private val database: PlayerDatabaseManager, private val cache: PlayerCache) : Listener{

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val name = event.player.name

        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            var data = database.load(uuid)

            if(data == null){
                data = PlayerData(uuid, name, "Empty")
            }

            cache.put(data)
        })
    }

}