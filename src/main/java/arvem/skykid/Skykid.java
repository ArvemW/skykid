package arvem.skykid;

import arvem.skykid.registries.RegisterItems;
import arvem.skykid.registries.RegisterSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skykid implements ModInitializer {

    public static final String MODID = "skykid";
    public static final String MODNAME = "Sky Kid";

    public static final TagKey<Block> PRIME_SOURCES_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "prime_sources"));

    public static final TagKey<Item> PRIME_SOURCES_ITEMS =
            TagKey.of(RegistryKeys.ITEM, Identifier.of("skykid", "prime_sources"));

    public static final TagKey<Block> AVERAGE_SOURCES_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "average_sources"));
    
    public static final TagKey<Item> AVERAGE_SOURCES_ITEMS =
            TagKey.of(RegistryKeys.ITEM, Identifier.of("skykid", "average_sources"));

    public static final TagKey<Block> WEAK_SOURCES_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "weak_sources"));
    
    public static final TagKey<Item> WEAK_SOURCES_ITEMS =
            TagKey.of(RegistryKeys.ITEM, Identifier.of("skykid", "weak_sources"));

    public static final TagKey<Block> CANDLES_BLOCKS =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "candles"));

    public static final TagKey<Item> CANDLES_ITEMS =
            TagKey.of(RegistryKeys.ITEM, Identifier.of("skykid", "candles"));

    private static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

    @Override
    public void onInitialize() {
        RegisterItems.init();
        RegisterSounds.init();
        LOGGER.info("When kingdom come...");
    }
}