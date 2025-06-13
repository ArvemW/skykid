package arvem.skykid.powers;


import arvem.skykid.Skykid;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.ResourcePower;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static net.minecraft.registry.Registries.ITEM;

public class LightManagementPower extends Power {
    private static final int RADIUS = 2;
    private static final int UPDATE_INTERVAL = 5;

    private final Map<LightSourceType, Integer> lightSourceCache = new ConcurrentHashMap<>();
    private long lastCacheUpdate = 0;
    private static final long CACHE_DURATION = 20;
    private long lastUpdateTick = 0;
    private int lastCalculatedGain = 0;


    public LightManagementPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
        setTicking(false);
    }


    @Override
    public void tick() {
        if (!entity.getWorld().isClient) {
            int gainAmount = calculateLightGain();
            if (gainAmount > 0) {
                // TODO: execute command from server /resource change entity skykid:light_resource gainAmount
                Objects.requireNonNull(entity.getWorld().getServer()).getCommandManager().executeWithPrefix(
                        entity.getWorld().getServer().getCommandSource().withSilent(),
                        String.format("resource change " + entity.getUuidAsString() + " skykid:light_resource %d", gainAmount)
                );
            }
        }
    }

    private int calculateLightGain() {
        long currentTick = entity.getWorld().getTime();

        // Only update every 5 ticks
        if (currentTick - lastUpdateTick < UPDATE_INTERVAL) {
            return 0;
        }
        lastUpdateTick = currentTick;

        int blockLightGain = 0;
        // Check for lit candle cakes first (highest priority)
        if (hasNearbyBlock(block ->
                block.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "candle_cakes")))
                        && block.get(Properties.LIT))) {
            blockLightGain = 40;
        }
        else {
            blockLightGain = calculateBlockLightGain();
        }

        // Calculate item-based light gain
        int itemLightGain = calculateItemLightGain();

        // Combine both sources
        lastCalculatedGain = blockLightGain + itemLightGain;
        return lastCalculatedGain;
    }

    private int calculateBlockLightGain() {
        int primeCount = countNearbyLightSources(LightSourceType.PRIME);
        int averageCount = countNearbyLightSources(LightSourceType.AVERAGE);
        int weakCount = countNearbyLightSources(LightSourceType.WEAK);

        record LightValue(int gain, int count) {}
        LightValue[] values = {
                new LightValue(3, primeCount),
                new LightValue(2, averageCount),
                new LightValue(1, weakCount)
        };

        Arrays.sort(values, (a, b) -> Integer.compare(b.gain, a.gain));

        int totalGain = 0;
        int blocksUsed = 0;

        for (LightValue value : values) {
            int remaining = Math.min(3 - blocksUsed, value.count);
            if (remaining > 0) {
                totalGain += remaining * value.gain;
                blocksUsed += remaining;
            }
            if (blocksUsed >= 3) break;
        }

        return totalGain;
    }

    private int calculateItemLightGain() {
        int gainFromItems = 0;

        ItemStack mainHand = entity.getMainHandStack();
        gainFromItems += getItemLightGain(mainHand);

        ItemStack offHand = entity.getOffHandStack();
        gainFromItems += getItemLightGain(offHand);

        return gainFromItems;
    }

    private int getItemLightGain(ItemStack stack) {
        if (stack.isEmpty()) return 0;

        // Top tier light sources (4 gain)
        if (stack.isOf(ITEM.get(Identifier.of(Skykid.MODID, "winged_light"))) ||
                stack.isOf(ITEM.get(Identifier.of(Skykid.MODID, "ascended_light")))) {
            return 4;
        }

        // Prime sources (3 gain)
        if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(Skykid.MODID, "prime_sources")))) {
            return 3;
        }

        // Average sources (2 gain)
        if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(Skykid.MODID, "average_sources")))) {
            return 2;
        }

        // Weak sources (1 gain)
        if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(Skykid.MODID, "weak_sources")))) {
            return 1;
        }

        return 0;
    }

    private boolean hasNearbyBlock(Predicate<BlockState> predicate) {
        BlockPos entityPos = entity.getBlockPos();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    BlockPos checkPos = entityPos.add(x, y, z);
                    BlockState state = entity.getWorld().getBlockState(checkPos);
                    if (predicate.test(state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private int countNearbyLightSources(LightSourceType type) {
        long currentTime = entity.getWorld().getTime();

        if (currentTime - lastCacheUpdate < CACHE_DURATION) {
            return lightSourceCache.getOrDefault(type, 0);
        }

        lightSourceCache.clear();
        lastCacheUpdate = currentTime;

        BlockPos entityPos = entity.getBlockPos();
        int count = 0;

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    BlockPos checkPos = entityPos.add(x, y, z);
                    BlockState state = entity.getWorld().getBlockState(checkPos);
                    int lightLevel = state.getLuminance();

                    if (isLightSourceType(state, lightLevel, type)) {
                        count++;
                    }
                }
            }
        }

        lightSourceCache.put(type, count);
        return count;
    }

    private boolean isLightSourceType(BlockState state, int lightLevel, LightSourceType type) {
        switch (type) {
            case PRIME:
                // Prime sources: Glowstone, Sea Lantern, etc.
                return lightLevel >= 15;
            case AVERAGE:
                // Average sources: Torches, Lanterns, etc.
                return lightLevel >= 12 && lightLevel < 15;
            case WEAK:
                // Weak sources: Redstone Torch, Soul Lantern, etc.
                return lightLevel >= 7 && lightLevel < 12;
            default:
                return false;
        }
    }

    private enum LightSourceType {
        PRIME, AVERAGE, WEAK
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
                new Identifier("skykid", "light_management"),
                new SerializableData().add("resource", ApoliDataTypes.POWER_TYPE),
                data -> (type, player) -> new LightManagementPower(type, player)
        ).allowCondition();
    }


}
