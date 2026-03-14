package org.sirox.ezprofile.util

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.sirox.ezprofile.EzProfile

class TextUtil(private val plugin: EzProfile) {

    fun deserializeText(text: String): Component {
        val component: Component = MiniMessage.miniMessage().deserialize(text)

        return component
    }

    fun deserializeTextWithPlaceholders(text: String, player: Player): Component {

        val playerCache = plugin.cache.get(player.uniqueId)

        val replacedText: String = text
            .replace("<player>", player.name)
            .replace("<likes>", playerCache?.likes.toString())
            .replace("<dislikes>", playerCache?.dislikes.toString())
        PlaceholderAPI.setPlaceholders(player, replacedText)

        val component: Component = MiniMessage.miniMessage().deserialize(replacedText)

        return component
    }
}