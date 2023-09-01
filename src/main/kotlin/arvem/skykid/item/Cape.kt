package arvem.skykid.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.Entity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Rarity
import net.minecraft.world.World


class Cape(type: Type, settings: Settings) : ArmorItem(
    CapeDefaultMaterial.INSTANCE,
    type,
    settings.fireproof()
) {

    companion object {
        val CAPE_DEFAULT = Cape(Type.CHESTPLATE, FabricItemSettings().fireproof())
        val CAPE_BLACK = Cape(Type.CHESTPLATE, FabricItemSettings().fireproof())

        fun register() {
            linkedMapOf(
                "cape_default" to CAPE_DEFAULT,
                "cape_black" to CAPE_BLACK
            )
        }

    }

    override fun getRarity(stack: ItemStack?) = Rarity.EPIC

    override fun getItemBarColor(stack: ItemStack?) = 0x00FFC0

    override fun isDamageable() = false
}