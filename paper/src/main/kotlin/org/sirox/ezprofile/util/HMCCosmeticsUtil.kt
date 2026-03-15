package org.sirox.ezprofile.util

import com.hibiscusmc.hmccosmetics.api.HMCCosmeticsAPI
import com.hibiscusmc.hmccosmetics.cosmetic.CosmeticSlot
import com.hibiscusmc.hmccosmetics.cosmetic.Cosmetics
import com.hibiscusmc.hmccosmetics.user.CosmeticUser
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.UUID

class HMCCosmeticsUtil {

    fun getCosmetic(uuid: UUID, name: String) : ItemStack {

        var cosmeticItem: ItemStack = ItemStack(Material.AIR)

        val user: CosmeticUser? = HMCCosmeticsAPI.getUser(uuid)
        val cosmeticSlot: CosmeticSlot? = CosmeticSlot.valueOf(name)

        if (cosmeticSlot != null && user != null) {
            cosmeticItem = user.getUserCosmeticItem(cosmeticSlot) ?: return cosmeticItem
        }

        return cosmeticItem
    }
}