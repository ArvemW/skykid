package arvem.skykid.registries;

import arvem.skykid.Skykid;
import arvem.skykid.CapeMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RegisterItems {

    // Armor material for all capes
    private static final ArmorMaterial CAPE_MATERIAL = new CapeMaterial();

    // Cape items
    private static final Item CAPE_DEFAULT = createCapeItem();
    private static final Item CAPE_BLACK   = createCapeItem();
    private static final Item CAPE_WHITE   = createCapeItem();
    private static final Item CAPE_YELLOW  = createCapeItem();
    private static final Item CAPE_RED     = createCapeItem();
    private static final Item CAPE_GREEN   = createCapeItem();
    private static final Item CAPE_CYAN    = createCapeItem();
    private static final Item CAPE_BLUE    = createCapeItem();
    private static final Item CAPE_PURPLE  = createCapeItem();
    private static final Item CAPE_PINK    = createCapeItem();

    // Item group
    private static final RegistryKey<ItemGroup> SKY_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Skykid.MODID, "sky_group"));

    // Light items
    private static final Item WINGED_LIGHT   = new Item(new Item.Settings().fireproof().maxCount(1));

    public static void registerItemGroup() {
        Registry.register(
                Registries.ITEM_GROUP,
                SKY_GROUP,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(WINGED_LIGHT))
                        .displayName(Text.translatable("item_group.skykid.sky_group"))
                        .build()
        );
    }

    private static Item createCapeItem() {
        return new ArmorItem(CAPE_MATERIAL, Type.CHESTPLATE,
                new Item.Settings().fireproof().maxCount(1));
    }

    public static void init() {
        registerItemGroup();

        // Register winged_light
        Registry.register(Registries.ITEM,
                Identifier.of(Skykid.MODID, "winged_light"),
                WINGED_LIGHT);
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
                .register(entries -> entries.add(WINGED_LIGHT));

        // Register all capes
        registerCape("cape_default", CAPE_DEFAULT);
        registerCape("cape_black",   CAPE_BLACK);
        registerCape("cape_white",   CAPE_WHITE);
        registerCape("cape_yellow",  CAPE_YELLOW);
        registerCape("cape_red",     CAPE_RED);
        registerCape("cape_green",   CAPE_GREEN);
        registerCape("cape_cyan",    CAPE_CYAN);
        registerCape("cape_blue",    CAPE_BLUE);
        registerCape("cape_purple",  CAPE_PURPLE);
        registerCape("cape_pink",    CAPE_PINK);
    }

    private static void registerCape(String name, Item capeItem) {
        Identifier id = Identifier.of(Skykid.MODID, name);
        Registry.register(Registries.ITEM, id, capeItem);
        ItemGroupEvents.modifyEntriesEvent(SKY_GROUP)
                .register(entries -> entries.add(capeItem));
    }
}
