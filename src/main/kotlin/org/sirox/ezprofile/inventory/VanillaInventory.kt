package org.sirox.ezprofile.inventory

import dev.triumphteam.gui.guis.Gui
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class VanillaInventory {

    fun openInventory(player: Player) {

        val inventory = Gui.gui()
            .rows(5)
            .title(Component.text(player.name))
            .create()

        inventory.open(player)
    }

}