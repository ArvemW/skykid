package arvem.skykid.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.networking.ModPacketsS2C;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModPacketsS2C.class)
public class ModPacketsS2CMixin {

    @Inject(method = "onPowerSync", at = @At("HEAD"), cancellable = true, remap = false)
    private static void preventNullPowerSync(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender, CallbackInfo ci) {
        if (minecraftClient.player == null) {
            ci.cancel();
            return;
        }

        try {
            int entityId = packetByteBuf.readInt();
            Identifier powerId = packetByteBuf.readIdentifier();

            Entity entity = minecraftClient.world.getEntityById(entityId);
            if (!(entity instanceof LivingEntity)) {
                ci.cancel();
                return;
            }

            PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
            Power power = component.getPower(PowerTypeRegistry.get(powerId));

            if (power == null) {
                // Safely consume the remaining NBT data without processing it
                packetByteBuf.readNbt();
                ci.cancel();
                return;
            }
        } catch (Exception e) {
            ci.cancel();
        }

        // Reset the buffer position to the start for normal processing
        packetByteBuf.readerIndex(0);
    }
}