package org.sirox.ezprofile.util

import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import org.bukkit.command.CommandSender
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.command.ReloadCommand
import org.sirox.ezprofile.handler.PermissionHandler
import org.sirox.ezprofile.handler.UsageHandler

class CommandUtil(private val plugin: EzProfile) {

    lateinit var liteCommands: LiteCommands<CommandSender>

    fun initializeCommands() {
        this.liteCommands = LiteBukkitFactory.builder("ezprofile", plugin)
            .missingPermission(PermissionHandler(plugin))
            .invalidUsage(UsageHandler(plugin))
            .commands(
                ReloadCommand(plugin)
            )
            .build()
    }

    fun uninitializeCommands() {
        if(this::liteCommands.isInitialized) {
            liteCommands.unregister()
        }
    }

}