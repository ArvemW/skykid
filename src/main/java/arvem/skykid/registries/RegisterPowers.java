package arvem.skykid.registries;

import arvem.skykid.powers.KineticImmunityPower;
import arvem.skykid.powers.LightArmorPower;
import arvem.skykid.powers.LightManagementPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public class RegisterPowers {

    public static void init () {
        register(LightManagementPower.getFactory());
        register(LightArmorPower.getFactory());
        register(KineticImmunityPower.getFactory());
    }

    private static void register (PowerFactory<?> powerFactory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
    }
}
