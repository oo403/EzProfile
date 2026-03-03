package org.sirox.ezprofile.command

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import eu.okaeri.configs.exception.OkaeriException
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.sirox.ezprofile.EzProfile

@Command(name = "profile")
class ReloadCommand(private val plugin: EzProfile) {

    @Execute(name = "reload")
    @Permission("profile.admin.reload")
    fun executeReload(@Context sender: CommandSender) {
        try {
            plugin.configs.reloadConfigs()
        } catch (e: OkaeriException) {
            e.printStackTrace()

            val errorMessage: Component = MiniMessage.miniMessage().deserialize(plugin.configs.messageConfig.reloadErrorMessage)

            sender.sendMessage(errorMessage)
        }

        val successMessage: Component = MiniMessage.miniMessage().deserialize(plugin.configs.messageConfig.reloadMessage)

        sender.sendMessage(successMessage)
    }

}
