package org.sirox.ezprofile.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class InventoryConfig : OkaeriConfig() {

    @Comment("Title of the inventory")
    @Comment("<player> - playername placeholder")
    var title: String = "<player>"

    @Comment("Sounds in inventory")
    var sounds: MutableMap<String, Sounds> = mutableMapOf(
        "open" to Sounds(),
        "close" to Sounds()
    )

    @Comment("Items in inventory")
    var items: MutableMap<String, GuiItem> = mutableMapOf()

    @Comment("Actions in inventory")
    var actions: MutableMap<String, GuiAction> = mutableMapOf()

    class Sounds : OkaeriConfig() {
        var enabled: Boolean = true
        var soundName: String = "minecraft:block.chest.close"
    }

    class GuiItem : OkaeriConfig() {
        var material: String = "STONE"
        var amount: Int = 1
        var slot: Int = 1
        var lore: List<String> = emptyList()
        var name: String = ""
        var itemModel: String = ""
        var hideTooltip: Boolean = false
    }

    class GuiAction : OkaeriConfig() {
        var slot: Int = 1
        var action: String = ""
        var args: List<String> = emptyList()
    }

}