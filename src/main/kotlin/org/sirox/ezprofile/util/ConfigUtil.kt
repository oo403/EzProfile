package org.sirox.ezprofile.util

import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit
import org.sirox.ezprofile.EzProfile
import org.sirox.ezprofile.config.Config
import org.sirox.ezprofile.config.InventoryConfig
import org.sirox.ezprofile.config.MessageConfig
import java.io.File

class ConfigUtil(private val plugin: EzProfile) {

    val configFile: File = File(plugin.dataFolder, "config.yml")

    var config: Config = ConfigManager.create(Config::class.java) { it ->
        it.configure { opt ->
            opt.configurer(YamlBukkitConfigurer(), SerdesBukkit())
            opt.bindFile(configFile)
            opt.resolvePlaceholders()
        }
    }

    val inventoryConfigFile: File = File(plugin.dataFolder, "inventories.yml")

    var inventoryConfig: InventoryConfig = ConfigManager.create(InventoryConfig::class.java) { it ->
        it.configure { opt ->
            opt.configurer(YamlBukkitConfigurer(), SerdesBukkit())
            opt.bindFile(inventoryConfigFile)
            opt.resolvePlaceholders()
        }
    }

    val messageConfigFile: File = File(plugin.dataFolder, "messages.yml")

    var messageConfig: MessageConfig = ConfigManager.create(MessageConfig::class.java) { it ->
        it.configure { opt ->
            opt.configurer(YamlBukkitConfigurer(), SerdesBukkit())
            opt.bindFile(messageConfigFile)
            opt.resolvePlaceholders()
        }
    }

    fun initializeConfigs() {
        listOf(config, inventoryConfig, messageConfig).forEach{ it ->
            it.saveDefaults()
            it.load(true)
        }
    }

    fun reloadConfigs() {
        listOf(config, inventoryConfig, messageConfig).forEach{ it ->
            it.load(true)
        }
    }

}