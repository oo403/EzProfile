package org.sirox.ezprofile.util

import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages
import net.kyori.adventure.platform.AudienceProvider
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.command.ProfileCommand
import org.sirox.ezprofile.command.ReloadCommand
import org.sirox.ezprofile.handler.PermissionHandler
import org.sirox.ezprofile.handler.UsageHandler

class CommandUtil(private val plugin: EzProfile) {

    lateinit var liteCommands: LiteCommands<CommandSender>
    lateinit var audienceProvider: AudienceProvider
    lateinit var miniMessage: MiniMessage

    fun initializeCommands() {
        this.audienceProvider = BukkitAudiences.create(plugin)
        this.miniMessage = MiniMessage.miniMessage()

        this.liteCommands = LiteBukkitFactory.builder("ezprofile", plugin)

            // HANDLERS
            .missingPermission(PermissionHandler(plugin))
            .invalidUsage(UsageHandler(plugin))

            // MINIMESSAGE SUPPORT
            .extension(LiteAdventurePlatformExtension(audienceProvider)) { opt ->
                opt.miniMessage(true)
                opt.legacyColor(true)
                opt.colorizeArgument(true)
                opt.serializer(miniMessage)
            }

            // MESSAGES
            .message(LiteBukkitMessages.PLAYER_NOT_FOUND) { it ->
                plugin.configs.messageConfig.playerNotFound
                    .replace("<player>", it)
            }

            // COMMANDS
            .commands(
                ReloadCommand(plugin),
                ProfileCommand(plugin)
            )
            .build()
    }

    fun uninitializeCommands() {
        if(this::liteCommands.isInitialized) {
            liteCommands.unregister()
        }
    }

}