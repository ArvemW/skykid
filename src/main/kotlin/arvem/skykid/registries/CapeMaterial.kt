package arvem.skykid.registries

import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

class CapeMaterial : ArmorMaterial {
    override fun getDurability(type: ArmorItem.Type): Int {
        return 0
    }

    override fun getProtection(type: ArmorItem.Type): Int {
        return 0
    }

    override fun getEnchantability(): Int {
        return 0
    }

    override fun getEquipSound(): SoundEvent {
        return SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA
    }

    override fun getRepairIngredient(): Ingredient {
        return Ingredient.EMPTY
    }

    override fun getName(): String {
        return "cape"
    }

    override fun getToughness(): Float {
        return 0f
    }

    override fun getKnockbackResistance(): Float {
        return 0f
    }
}