package arvem.skykid;

import arvem.skykid.registries.RegisterItems;
import arvem.skykid.registries.RegisterPowers;
import arvem.skykid.registries.RegisterSounds;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ResourcePower;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skykid implements ModInitializer {

    public static final String MODID = "skykid";
    public static final String MODNAME = "Sky Kid";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

    @Override
    public void onInitialize() {
        RegisterItems.init();
        RegisterSounds.init();
        RegisterPowers.init();
        LOGGER.info("When kingdom come...");
    }

    public static ResourcePower tryInitializeResource(LivingEntity entity, PowerType<?> resourceType) {
        if (entity == null || entity.getWorld() == null) {
            return null;
        }

        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);

        try {
            Power p = component.getPower(resourceType);
            if (p instanceof ResourcePower rp) {
                return rp;
            } else if (p != null) {
                LOGGER.error("Power provided is not a ResourcePower. Provided power is a {} instead.", p.getClass().getSimpleName());
            } else {
                // Only log this warning if we're on the server side to reduce spam
                if (!entity.getWorld().isClient && entity.age % 20 == 0) {
                    LOGGER.debug("Power is null for resource type: {}", resourceType.getIdentifier());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error initializing resource power: {}", e.getMessage());
        }

        return null;

    }
}