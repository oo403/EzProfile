package org.sirox.ezprofile.command

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.optional.OptionalArg
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.inventory.VanillaInventory

@Command(name = "profile")
class ProfileCommand(private val plugin: EzProfile) {

    private val vanillaInventory = VanillaInventory()

    @Execute
    fun executeProfile(@Context sender: CommandSender, @OptionalArg player: Player?) {
        if (player == null) {
            vanillaInventory.openInventory(sender as Player)
        } else {
            vanillaInventory.openInventory(player)
        }
    }

}
