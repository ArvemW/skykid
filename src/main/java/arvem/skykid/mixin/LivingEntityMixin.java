package arvem.skykid.mixin;

import arvem.skykid.Skykid;
import arvem.skykid.powers.LightArmorPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public int hurtTime;
    @Unique
    private static final PowerTypeReference<Power> LIGHT_ARMOR = new PowerTypeReference<>(Identifier.of(Skykid.MODID, "light_armor"));

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyDamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (PowerHolderComponent.KEY.get(entity).hasPower(LIGHT_ARMOR)) {
            if (hurtTime > 0) {
                return 0;
            }

            if (source.isOf(DamageTypes.LAVA)) {
                amount = amount * 3;
            }

            LightArmorPower p = (LightArmorPower) PowerHolderComponent.KEY.get(entity).getPower(LIGHT_ARMOR);
            return p.calculateRemainingDamage(amount);
        }
        return amount;
    }

    @Inject(method = "getArmor", at = @At("RETURN"), cancellable = true)
    private void modifyArmorValue(CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (PowerHolderComponent.KEY.get(entity).hasPower(LIGHT_ARMOR)) {
            // Get the current armor value
            int currentArmor = cir.getReturnValue();

            var chestStack = entity.getEquippedStack(EquipmentSlot.CHEST);
            var legStack = entity.getEquippedStack(EquipmentSlot.LEGS);

            if (chestStack.getItem() instanceof ArmorItem chestArmor) {
                currentArmor -= chestArmor.getProtection();
            }
            if (legStack.getItem() instanceof ArmorItem legArmor) {
                currentArmor -= legArmor.getProtection();
            }

            cir.setReturnValue(currentArmor);
        }
    }
}
