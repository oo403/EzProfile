package org.sirox.ezprofile.inventory

import com.destroystokyo.paper.profile.PlayerProfile
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import io.papermc.paper.registry.data.dialog.body.DialogBody.item
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.profile.PlayerTextures
import org.sirox.ezprofile.EzProfile
import java.net.URL
import java.util.UUID

class GeneralInventory(private val plugin: EzProfile) {

    fun openInventory(sender: Player, player: Player) {

        val playerName: String = player.name
        var title: String = plugin.configs.inventoryConfig.title
            .replace("<player>", playerName)

        title = PlaceholderAPI.setPlaceholders(player, title)

        val componentTitle: Component = MiniMessage.miniMessage().deserialize(title)
        val rows: Int = plugin.configs.inventoryConfig.rows

        if (rows !in 1..<7) {
            plugin.logger.warn("Invalid inventory rows, use number from 1 to 6")
            return
        }

        val inventory = Gui.gui()
            .rows(rows)
            .title(componentTitle)
            .create()

        /**
         * SOME SHIT TO ADD SOUNDS TO GUI
         */

        if (plugin.configs.inventoryConfig.sounds["open"] != null) {
            val openSound: Sound = Sound.sound(Key.key(plugin.configs.inventoryConfig.sounds["open"]?.soundName!!), Source.PLAYER, 1.0f, 1.0f)

            if (plugin.configs.inventoryConfig.sounds["open"]?.enabled!!) {
                inventory.setOpenGuiAction {
                    sender.playSound(openSound)
                }
            }
        }

        if (plugin.configs.inventoryConfig.sounds["close"] != null) {
            val closeSound: Sound = Sound.sound(Key.key(plugin.configs.inventoryConfig.sounds["close"]?.soundName!!), Source.PLAYER, 1.0f, 1.0f)

            if (plugin.configs.inventoryConfig.sounds["close"]?.enabled!!) {
                inventory.setCloseGuiAction {
                    sender.playSound(closeSound)
                }
            }
        }

        /**
         * SOME SHIT TO ADD ITEMS TO GUI SLOTS
         */

        plugin.configs.inventoryConfig.items.forEach{ (key, guiItem) ->

            val amount = guiItem.amount

            val material: Material = Material.matchMaterial(guiItem.material) ?: run {
                when (guiItem.material.lowercase()) {
                    "<helmet>" -> player.inventory.helmet?.type ?: Material.AIR
                    "<chestplate>" -> player.inventory.chestplate?.type ?: Material.AIR
                    "<leggings>" -> player.inventory.leggings?.type ?: Material.AIR
                    "<boots>" -> player.inventory.boots?.type ?: Material.AIR
                    "<mainhand>" -> player.inventory.itemInMainHand.type
                    "<offhand>" -> player.inventory.itemInOffHand.type
                    else -> {
                        plugin.logger.info("Invalid material ${guiItem.material} in item $key")
                        Material.AIR
                    }
                }
            }

            val itemStack = ItemStack(material, amount)

            var itemMeta = itemStack.itemMeta

            if (itemMeta != null) {
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

                    val namespacedKey = NamespacedKey(key[0], key[1])

                    itemMeta.itemModel = namespacedKey
                }

                if (guiItem.hideTooltip) {
                    itemMeta.isHideTooltip = true
                }

                if (material == Material.PLAYER_HEAD) {
                    itemMeta = itemMeta as SkullMeta

                    if (guiItem.skinUrl.isNotEmpty()) {
                        val profile: PlayerProfile = Bukkit.createProfile(UUID.randomUUID())
                        val texture: PlayerTextures = profile.textures

                        texture.skin = URL("http://textures.minecraft.net/texture/87a3987c5cdb35aba9ae6f2e23489aa96e080e93fac435dc640f737b5ca401d3")
                        profile.setTextures(texture)

                        itemMeta.playerProfile = profile
                    } else {
                        val playerProfile: PlayerProfile = player.playerProfile

                        itemMeta.playerProfile = playerProfile
                    }
                }

                itemStack.itemMeta = itemMeta
            }

            val item = GuiItem(itemStack)

            inventory.setDefaultClickAction { event ->
                event.isCancelled = true
            }

            inventory.setItem(guiItem.slot, item)
        }


        /**
         * SOME SHIT TO ADD ACTIONS TO GUI SLOTS
         */

        plugin.configs.inventoryConfig.actions.forEach { (key, action) ->
            val actionName: String = action.action

            val regex: Regex = Regex("""^\[(.*?)\]\s*(.*)""")
            val match = regex.find(actionName)

            if (match != null) {
                val (tag, value) = match.destructured
                val lowerTag: String = tag.lowercase()

                value.replace("<sender>", sender.name)
                value.replace("<player>", player.name)

                PlaceholderAPI.setPlaceholders(player, value)

                when (lowerTag) {
                    "console" -> {
                        inventory.addSlotAction(action.slot) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), value)
                        }
                    }
                    "player" -> {
                        inventory.addSlotAction(action.slot) {
                            sender.performCommand(value)
                        }
                    }
                    "message" -> {
                        inventory.addSlotAction(action.slot) {
                            val message: Component = MiniMessage.miniMessage().deserialize(value)
                            sender.sendMessage(message)
                        }
                    }
                    else -> {
                        inventory.addSlotAction(action.slot) {
                            val rawMessage: String = plugin.configs.messageConfig.wrongActionMessage
                            val message: Component = MiniMessage.miniMessage().deserialize(rawMessage)
                            sender.sendMessage(message)
                        }
                    }
                }
            } else {
                plugin.logger.warn("Invalid action $actionName")
            }
        }

        inventory.open(sender)
    }

}