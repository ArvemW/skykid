package arvem.skykid;

import arvem.skykid.registries.RegisterItems;
import arvem.skykid.registries.RegisterPowers;
import arvem.skykid.registries.RegisterSounds;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skykid implements ModInitializer {

    public static final String MODID = "skykid";
    public static final String MODNAME = "Sky Kid";

    private static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

    @Override
    public void onInitialize() {
        RegisterItems.init();
        RegisterSounds.init();
        RegisterPowers.init();
        LOGGER.info("When kingdom come...");
    }
}