package arvem.skykid.powers;


import arvem.skykid.Skykid;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ResourcePower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import static arvem.skykid.Skykid.tryInitializeResource;

public class LightArmorPower extends Power {

    private static final float RESOURCE_CONSUMPTION_PER_DAMAGE = 16f;
    private static final float EPSILON = 0.0001f;

    private SerializableData.Instance DATA;

    private ResourcePower resourcePower;


    public LightArmorPower(PowerType<?> type, LivingEntity entity, SerializableData.Instance data) {
        super(type, entity);
        setTicking(true);
        if (!data.isPresent("resource")) {
            Skykid.LOGGER.error("No resource provided for LightManagementPower.");
            return;
        }
        DATA = data;
        resourcePower = tryInitializeResource(entity, data.get("resource"));
    }

    public float calculateRemainingDamage(float damageAmount) {
        if (resourcePower == null && DATA != null) {
            resourcePower = tryInitializeResource(entity, DATA.get("resource"));
            // If still null after trying to initialize, return full damage
            if (resourcePower == null) {
                return damageAmount;
            }
        }

        if (resourcePower == null || damageAmount <= 0 || entity == null) {
            return damageAmount;
        }

        float value = resourcePower.getValue();
        float damageCost = damageAmount * RESOURCE_CONSUMPTION_PER_DAMAGE;

        float remainingDamage;
        float newResourceValue;

        if (damageCost < value + EPSILON) {
            newResourceValue = value - damageCost;
            remainingDamage = 0f;
        } else {
            newResourceValue = 0;
            remainingDamage = damageAmount - (value / RESOURCE_CONSUMPTION_PER_DAMAGE);
        }

        resourcePower.setValue(Math.round(Math.max(0, newResourceValue)));
        if (!entity.getWorld().isClient && entity.isAlive()) {
            PowerHolderComponent.syncPower(entity, this.type);
        }
        return remainingDamage;
    }


    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
                new Identifier("skykid", "light_armor"),
                new SerializableData().add("resource", ApoliDataTypes.POWER_TYPE, null),
                data -> (type, player) -> new LightArmorPower(type, player, data)
        ).allowCondition();
    }
}