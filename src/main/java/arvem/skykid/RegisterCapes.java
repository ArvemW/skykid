package arvem.skykid;

import arvem.skykid.item.RegisterItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;

import static arvem.skykid.item.RegisterItems.WINGED_LIGHT;

public class RegisterCapes {
    public static final ArmorMaterial CAPE_MATERIAL = new CapeMaterial();
    public static final Item CAPE_DEFAULT = new ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxCount(1));
    public static final Item CAPE_BLACK = new ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxCount(1));
    public static final Item CAPE_WHITE = new ArmorItem(CAPE_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof().maxCount(1));

    public static final RegistryKey<ItemGroup> SKY_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Skykid.MODID, "sky_group"));


    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, SKY_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(WINGED_LIGHT))
                .displayName(Text.translatable("Sky Origins"))
                .build());
    }
    public static void register() {
        registerItemGroup();
        Registry.register(Registries.ITEM, new Identifier("skykid", "cape_default"), CAPE_DEFAULT);
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP).register(entries -> entries.add(CAPE_DEFAULT));
        Registry.register(Registries.ITEM, new Identifier("skykid", "cape_black"), CAPE_BLACK);
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP).register(entries -> entries.add(CAPE_BLACK));
        Registry.register(Registries.ITEM, new Identifier("skykid", "cape_white"), CAPE_WHITE);
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP).register(entries -> entries.add(CAPE_WHITE));
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP).register(entries -> entries.add(WINGED_LIGHT));

    }
}
