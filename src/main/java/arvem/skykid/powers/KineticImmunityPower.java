package arvem.skykid.powers;


import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class KineticImmunityPower extends Power {

    public KineticImmunityPower(PowerType<?> type, LivingEntity entity, SerializableData.Instance data) {
        super(type, entity);
        setTicking(true);
        this.addCondition(data.get("condition"));
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
                new Identifier("skykid", "kinetic_immunity"),
                new SerializableData().add("condition", ApoliDataTypes.ENTITY_CONDITION, null),
                data -> (type, player) -> new KineticImmunityPower(type, player, data)
        ).allowCondition();
    }
}