package arvem.skykid.mixin;

import arvem.skykid.Skykid;
import arvem.skykid.powers.LightArmorPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private static final PowerTypeReference<Power> LIGHT_ARMOR = new PowerTypeReference<>(Identifier.of(Skykid.MODID, "light_armor"));

    @Unique
    private static final PowerTypeReference<Power> KINETIC_IMMUNITY = new PowerTypeReference<>(Identifier.of(Skykid.MODID, "kinetic_immunity"));

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyDamage(float amount) {
        if (PowerHolderComponent.KEY.get(this).hasPower(LIGHT_ARMOR)) {
            LightArmorPower p = (LightArmorPower) PowerHolderComponent.KEY.get(this).getPower(LIGHT_ARMOR);
            return p.calculateRemainingDamage(amount);
        }
        return amount;
    }

    @Inject(method = "computeFallDamage", at = @At("HEAD"), cancellable = true)
    private void preventFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        if (PowerHolderComponent.KEY.get(this).hasPower(KINETIC_IMMUNITY)) {
            cir.setReturnValue(0);
            cir.cancel();
        }
    }
}
