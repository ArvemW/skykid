package arvem.skykid.mixin;

import arvem.skykid.Skykid;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {


    @Shadow public abstract boolean isInLava();

    @Unique
    private static final PowerTypeReference<Power> FIRE_IMMUNITY = new PowerTypeReference<>(Identifier.of(Skykid.MODID, "fire_immunity"));

    @Unique
    private static final PowerTypeReference<Power> KINETIC_IMMUNITY = new PowerTypeReference<>(Identifier.of(Skykid.MODID, "kinetic_immunity"));


    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    private void makeFullyFireImmune(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LivingEntity livingEntity){
            if(PowerHolderComponent.KEY.get(livingEntity).hasPower(FIRE_IMMUNITY) && !isInLava()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    private void modifyKineticImmunity(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LivingEntity && PowerHolderComponent.KEY.get(this).hasPower(KINETIC_IMMUNITY) && PowerHolderComponent.KEY.get(this).getPower(KINETIC_IMMUNITY).isActive() &&
                (source.isOf(DamageTypes.FALL) || source.isOf(DamageTypes.FLY_INTO_WALL))) {
            cir.setReturnValue(true);
        }
    }
}
