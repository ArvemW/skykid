package arvem.skykid.item
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

class CapeDefaultMaterial : ArmorMaterial {
    companion object {
        private val BASE_DURABILITY = intArrayOf(0, 0, 0, 0)
        val INSTANCE = CapeDefaultMaterial()
    }

    override fun getName(): String = "adamantine"

    override fun getDurability(type: ArmorItem.Type) = 0

    override fun getProtection(type: ArmorItem.Type?): Int {
        TODO("Not yet implemented")
    }

    override fun getEnchantability(): Int = 0

    override fun getEquipSound(): SoundEvent = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC

    override fun getToughness(): Float = 0f
    override fun getKnockbackResistance(): Float {
        TODO("Not yet implemented")
    }

    override fun getRepairIngredient(): Ingredient = Ingredient.ofItems(ItemConvertible {Items.AIR})
}