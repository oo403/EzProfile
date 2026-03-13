package org.sirox.ezprofile.handler

import dev.rollczi.litecommands.handler.result.ResultHandlerChain
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.permission.MissingPermissions
import dev.rollczi.litecommands.permission.MissingPermissionsHandler
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.util.TextUtil

class PermissionHandler(private val plugin: EzProfile) : MissingPermissionsHandler<CommandSender> {

    val textUtil = TextUtil()

    override fun handle(
        invocation: Invocation<CommandSender>,
        missingPermissions: MissingPermissions,
        chain: ResultHandlerChain<CommandSender>
    ) {
        val player: Player = invocation.sender() as Player
        val permission: List<String> = missingPermissions.permissions

        val rawMessage: String = plugin.configs.messageConfig.missingPermissions
            .replace("<permissions>", permission.toString())

        val message: Component = textUtil.deserializeText(rawMessage)

        player.sendMessage(message)
    }

}