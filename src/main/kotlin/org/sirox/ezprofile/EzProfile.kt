package org.sirox.ezprofile

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.sirox.ezprofile.util.CommandUtil
import org.sirox.ezprofile.util.ConfigUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EzProfile : JavaPlugin() {

    lateinit var logger: Logger
    lateinit var configs: ConfigUtil
    lateinit var commands: CommandUtil

    override fun onEnable() {
        configs = ConfigUtil(this)
        commands = CommandUtil(this)
        logger = LoggerFactory.getLogger(configs.config.pluginName)

        configs.initializeConfigs()
        commands.initializeCommands()

        if (configs.config.logging) logger.info("${configs.config.pluginName} has been enabled")
    }

    override fun onDisable() {
        commands.uninitializeCommands()

        if (configs.config.logging) logger.info("${configs.config.pluginName} has been disabled")
    }
}
