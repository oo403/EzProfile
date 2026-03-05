package org.sirox.ezprofile.util

import org.bukkit.Bukkit
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.event.ShiftRightEvent

class EventUtil(private val plugin: EzProfile) {

    fun registerEvents() {
        listOf(ShiftRightEvent(plugin)).forEach { it ->
            Bukkit.getPluginManager().registerEvents(it, plugin)
        }
    }

}