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
import org.sirox.ezprofile.util.TextUtil

@Command(name = "profile")
class ReloadCommand(private val plugin: EzProfile) {

    val textUtil = TextUtil(plugin)

    @Execute(name = "reload")
    @Permission("profile.admin.reload")
    fun executeReload(@Context sender: CommandSender) {
        try {
            plugin.configs.reloadConfigs()
        } catch (e: OkaeriException) {
            e.printStackTrace()

            val errorMessage: Component = textUtil.deserializeText(plugin.configs.messageConfig.reloadErrorMessage)

            sender.sendMessage(errorMessage)
        }

        val successMessage: Component = textUtil.deserializeText(plugin.configs.messageConfig.reloadMessage)

        sender.sendMessage(successMessage)
    }

}
