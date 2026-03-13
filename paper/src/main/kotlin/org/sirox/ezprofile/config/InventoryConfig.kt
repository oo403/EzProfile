package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class InventoryConfig : OkaeriConfig() {

    @Comment("Title of the inventory")
    @Comment("<player> - playername placeholder")
    var title: String = "<player>"

    @Comment("Rows of the inventory")
    var rows: Int = 6

    @Comment("Sounds in inventory")
    var sounds: MutableMap<String, Sounds> = mutableMapOf(
        "open" to Sounds().apply {
            enabled = true
            soundName = "minecraft:block.chest.open"
        },
        "close" to Sounds().apply {
            enabled = true
            soundName = "minecraft:block.chest.close"
        }
    )

    @Comment("Items in inventory")
    var items: MutableMap<String, GuiItem> = mutableMapOf(
        "head" to GuiItem().apply {
            material = "PLAYER_HEAD"
            amount = 1
            slot = 20
            name = "<white><i:false>%player_name%"
        },
        "mainhand" to GuiItem().apply {
            material = "<mainhand>"
            amount = 1
            slot = 0
        },
        "helmet" to GuiItem().apply {
            material = "<helmet>"
            amount = 1
            slot = 9
        },
        "chestplate" to GuiItem().apply {
            material = "<chestplate>"
            amount = 1
            slot = 18
        },
        "leggings" to GuiItem().apply {
            material = "<leggings>"
            amount = 1
            slot = 27
        },
        "boots" to GuiItem().apply {
            material = "<boots>"
            amount = 1
            slot = 36
        },
        "offhand" to GuiItem().apply {
          material = "<offhand>"
          amount = 1
          slot = 45
        }

    )

    @Comment("Actions in inventory")
    var actions: MutableMap<String, GuiAction> = mutableMapOf()

    class Sounds : OkaeriConfig() {
        var enabled: Boolean = true
        var soundName: String = ""
    }

    class GuiItem : OkaeriConfig() {
        var material: String = "STONE"
        var amount: Int = 1
        var slot: Int = 1
        var lore: List<String> = emptyList()
        var name: String = ""
        var itemModel: String = ""
        var hideTooltip: Boolean = false
        @Comment("Example: http://textures.minecraft.net/texture/87a3987c5cdb35aba9ae6f2e23489aa96e080e93fac435dc640f737b5ca401d3")
        @Comment("Use empty value to use player profile head")
        var skinUrl: String = ""
    }

    class GuiAction : OkaeriConfig() {
        var slot: Int = 1
        var action: String = ""
    }

}