package org.sirox.ezprofile.inventory

import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.sirox.ezprofile.EzProfile

class GeneralInventory(private val plugin: EzProfile) {

    fun openInventory(player: Player) {

        val playerName: String = player.name
        var title: String = plugin.configs.inventoryConfig.title
            .replace("<player>", playerName)

        title = PlaceholderAPI.setPlaceholders(player, title)

        val componentTitle: Component = MiniMessage.miniMessage().deserialize(title)

        var openSound: Sound
        var closeSound: Sound

        val inventory = Gui.gui()
            .rows(5)
            .title(componentTitle)
            .create()

        if (plugin.configs.inventoryConfig.sounds.get("open") != null) {
            openSound = Sound.sound(Key.key(plugin.configs.inventoryConfig.sounds.get("open")?.soundName!!), Source.PLAYER, 1.0f, 1.0f)

            if (plugin.configs.inventoryConfig.sounds.get("open")?.enabled!!) {
                inventory.setOpenGuiAction { it ->
                    val player = it.player

                    player.playSound(openSound)
                }
            }
        }

        if (plugin.configs.inventoryConfig.sounds.get("close") != null) {
            closeSound = Sound.sound(Key.key(plugin.configs.inventoryConfig.sounds.get("close")?.soundName!!), Source.PLAYER, 1.0f, 1.0f)

            if (plugin.configs.inventoryConfig.sounds.get("close")?.enabled!!) {
                inventory.setCloseGuiAction { it ->
                    val player = it.player

                    player.playSound(closeSound)
                }
            }
        }

        plugin.configs.inventoryConfig.items.forEach{ (key, guiItem) ->

            val amount = guiItem.amount

            val material = Material.matchMaterial(guiItem.material)
                ?: run {
                    plugin.logger.info("Invalid material ${guiItem.material} in item $key")
                    return@forEach
                }

            val itemStack = ItemStack(material, amount)

            val itemMeta = itemStack.itemMeta

            if (guiItem.name.isNotEmpty()) {
                var rawName = guiItem.name

                rawName = PlaceholderAPI.setPlaceholders(player, rawName)

                val name: Component = MiniMessage.miniMessage().deserialize(rawName)

                itemMeta.displayName(name)
            }

            if (guiItem.lore.isNotEmpty()) {
                itemMeta.lore(guiItem.lore.stream()
                    .map{
                        line -> PlaceholderAPI.setPlaceholders(player, line)
                    }
                    .map {
                        line -> MiniMessage.miniMessage().deserialize(line)
                    }
                    .toList())
            }

            if (guiItem.itemModel.isNotEmpty()) {
                val key: List<String> = guiItem.itemModel.split(":").map { it.trim() }

                val namespacedKey: NamespacedKey = NamespacedKey(key[0], key[1])

                itemMeta.itemModel = namespacedKey
            }

            if (guiItem.hideTooltip) {
                itemMeta.isHideTooltip = true
            }

            itemStack.itemMeta = itemMeta

            val item = GuiItem(itemStack)

            inventory.setDefaultClickAction { event ->
                event.isCancelled = true
            }

            inventory.setItem(guiItem.slot, item)
        }

        inventory.open(player)
    }

}