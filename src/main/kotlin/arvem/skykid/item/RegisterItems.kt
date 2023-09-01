package arvem.skykid.item

import arvem.skykid.Skykid.MODID
import com.mojang.logging.LogUtils
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object RegisterItems {
    private val LOGGER = LogUtils.getLogger()
    private val ITEMS = LinkedHashMap<Identifier, Item>()

    @JvmField
    val GROUP: ItemGroup = FabricItemGroup.builder()
        .displayName(Text.translatable("itemGroup.skykid.skykid_group"))
        .icon { ItemStack(WINGED_LIGHT) }
        .entries { _, entries ->
            entries.add(WINGED_LIGHT)
        }
        .build()

    @JvmField
    val WINGED_LIGHT: Item = add("winged_light", Item(FabricItemSettings().maxCount(1)))

    fun init() {
        for ((key, value) in ITEMS) {
            Registry.register(Registries.ITEM, key, value)
        }
        Registry.register(Registries.ITEM_GROUP, Identifier(MODID, "skykid_group"), GROUP)
    }

    private fun <I : Item> add(name: String, item: I): I {
        if (!ITEMS.containsKey(Identifier(MODID, name))) {
            ITEMS[Identifier(MODID, name)] = item
        } else {
            LOGGER.error("Item with same Identifier was registered! ($name)")
        }
        return item
    }
}