package org.sirox.ezprofile.handler

import dev.rollczi.litecommands.handler.result.ResultHandlerChain
import dev.rollczi.litecommands.invalidusage.InvalidUsage
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler
import dev.rollczi.litecommands.invocation.Invocation
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sirox.ezprofile.EzProfile

class UsageHandler(private val plugin: EzProfile) : InvalidUsageHandler<CommandSender> {

    override fun handle(
        invocation: Invocation<CommandSender>,
        result: InvalidUsage<CommandSender>,
        chain: ResultHandlerChain<CommandSender>
    ) {
        val player: Player = invocation.sender() as Player

        val rawMessage: String = plugin.configs.messageConfig.invalidUsageMessage
        val message: Component = MiniMessage.miniMessage().deserialize(rawMessage)

        player.sendMessage(message)
    }

}