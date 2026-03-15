package org.sirox.ezprofile

import org.bukkit.plugin.java.JavaPlugin
import org.sirox.ezprofile.database.cache.PlayerCache
import org.sirox.ezprofile.database.manager.DatabaseManager
import org.sirox.ezprofile.database.manager.PlayerDatabaseManager
import org.sirox.ezprofile.util.CommandUtil
import org.sirox.ezprofile.util.ConfigUtil
import org.sirox.ezprofile.util.EventUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EzProfile : JavaPlugin() {

    lateinit var logger: Logger
    lateinit var configs: ConfigUtil
    lateinit var commands: CommandUtil
    lateinit var events: EventUtil
    lateinit var database: DatabaseManager
    lateinit var playerDatabase: PlayerDatabaseManager
    lateinit var cache: PlayerCache

    override fun onEnable() {
        configs = ConfigUtil(this)
        commands = CommandUtil(this)

        database = DatabaseManager(this.dataFolder, this)
        playerDatabase = PlayerDatabaseManager(database.dataSource)
        cache = PlayerCache()

        events = EventUtil(this, playerDatabase, cache)
        logger = LoggerFactory.getLogger(configs.config.pluginName)

        configs.initializeConfigs()
        commands.initializeCommands()
        events.registerEvents()

        if (configs.config.logging) logger.info("${configs.config.pluginName} has been enabled")
    }

    override fun onDisable() {
        commands.uninitializeCommands()

        if (configs.config.logging) logger.info("${configs.config.pluginName} has been disabled")
    }
}
