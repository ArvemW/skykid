package arvem.skykid.registries

import arvem.skykid.Skykid
import com.mojang.logging.LogUtils
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.item.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object RegisterItems {
    private val LOGGER = LogUtils.getLogger()
    private val CAPE_MATERIAL: ArmorMaterial = CapeMaterial()
    private val CAPE_DEFAULT: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_BLACK: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_WHITE: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_YELLOW: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_RED: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_GREEN: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_CYAN: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_BLUE: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_PURPLE: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))
    private val CAPE_PINK: Item = ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, Item.Settings().fireproof().maxCount(1))

    private val SKY_GROUP: RegistryKey<ItemGroup> = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier(Skykid.MODID, "sky_group"))
    private val WINGED_LIGHT: Item = Item(Item.Settings().fireproof().maxCount(1))
    private val ASCENDED_LIGHT: Item = Item(Item.Settings().fireproof().maxCount(1))

    fun registerItemGroup() {
        Registry.register(
            Registries.ITEM_GROUP, SKY_GROUP, FabricItemGroup.builder()
                .icon { ItemStack(WINGED_LIGHT) }
                .displayName(Text.translatable("Sky Origins"))
                .build())
    }

    fun init() {
        registerItemGroup()
        Registry.register(Registries.ITEM, Identifier("skykid", "winged_light"), WINGED_LIGHT)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    WINGED_LIGHT
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "ascended_light"), ASCENDED_LIGHT)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    ASCENDED_LIGHT
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_default"), CAPE_DEFAULT)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_DEFAULT
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_black"), CAPE_BLACK)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_BLACK
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_white"), CAPE_WHITE)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_WHITE
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_yellow"), CAPE_YELLOW)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_YELLOW
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_red"), CAPE_RED)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_RED
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_green"), CAPE_GREEN)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_GREEN
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_cyan"), CAPE_CYAN)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_CYAN
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_blue"), CAPE_BLUE)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_BLUE
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_purple"), CAPE_PURPLE)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_PURPLE
                )
            })
        Registry.register(Registries.ITEM, Identifier("skykid", "cape_pink"), CAPE_PINK)
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
            .register(ModifyEntries { entries: FabricItemGroupEntries ->
                entries.add(
                    CAPE_PINK
                )
            })
    }
}