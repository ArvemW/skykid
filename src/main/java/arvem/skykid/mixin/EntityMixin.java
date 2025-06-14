package arvem.skykid.mixin;

import arvem.skykid.Skykid;
import arvem.skykid.powers.LightArmorPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.entity.Entity.class)
public abstract class EntityMixin {


    @Shadow public abstract boolean isInLava();

    @Unique
    private static final PowerTypeReference<Power> FIRE_IMMUNITY = new PowerTypeReference<>(Identifier.of(Skykid.MODID, "fire_immunity"));


    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    private void makeFullyFireImmune(CallbackInfoReturnable<Boolean> cir) {
        if(PowerHolderComponent.KEY.get(this).hasPower(FIRE_IMMUNITY) && !isInLava()) {
            cir.setReturnValue(true);
        }
    }
}
