package org.sirox.ezprofile.command

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.optional.OptionalArg
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.inventory.GeneralInventory

@Command(name = "profile")
class ProfileCommand(private val plugin: EzProfile) {

    private val inventory = GeneralInventory(plugin)

    @Execute
    fun executeProfile(@Context sender: CommandSender, @OptionalArg player: Player?) {
        if (player == null) {
            inventory.openInventory(sender as Player, sender)
        } else {
            inventory.openInventory(sender as Player, player)
        }
    }

}
