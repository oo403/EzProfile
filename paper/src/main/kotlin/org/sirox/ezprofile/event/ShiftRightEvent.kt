package org.sirox.ezprofile.event

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.inventory.GeneralInventory

class ShiftRightEvent(private val plugin: EzProfile) : Listener{

    private val inventory = GeneralInventory(plugin)

    @EventHandler
    fun onShiftRight(event: PlayerInteractAtEntityEvent) {
        if (event.rightClicked is Player && !event.rightClicked.isDead && event.player.isSneaking && plugin.configs.config.shift) {
            val sender = event.player
            val target = event.rightClicked as Player

            inventory.openInventory(sender, target)
        }
    }
}