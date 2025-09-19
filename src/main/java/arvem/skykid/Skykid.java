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

        Power p = component.getPower(resourceType);
        if (p instanceof ResourcePower) {
            return (ResourcePower)p;
        } else if (p != null) {
            Skykid.LOGGER.error("Power provided is not a ResourcePower. Provided power is a " + p.getClass().getSimpleName() + " instead.");
        }
        return null;
    }
}