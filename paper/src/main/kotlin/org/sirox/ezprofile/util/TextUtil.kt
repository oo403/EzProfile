package org.sirox.ezprofile.util

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

class TextUtil {

    fun deserializeText(text: String): Component {
        val component: Component = MiniMessage.miniMessage().deserialize(text)

        return component
    }

    fun deserializeTextWithPlaceholders(text: String, player: Player): Component {

        text.replace("<player>", player.name)

        PlaceholderAPI.setPlaceholders(player, text)

        val component: Component = MiniMessage.miniMessage().deserialize(text)

        return component
    }
}