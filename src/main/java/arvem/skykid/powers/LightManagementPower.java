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
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static arvem.skykid.Skykid.tryInitializeResource;
import static net.minecraft.registry.Registries.ITEM;

public class LightManagementPower extends Power {
    private static final int RADIUS = 3;
    private static final int UPDATE_INTERVAL = 5;

    private final Map<LightSourceType, Integer> lightSourceCache = new ConcurrentHashMap<>();
    private long lastCacheUpdate = 0;
    private static final long CACHE_DURATION = 10;
    private SerializableData.Instance DATA;
    private long lastUpdateTick = 0;

    private ResourcePower resourcePower;


    public LightManagementPower(PowerType<?> type, LivingEntity entity, SerializableData.Instance data) {
        super(type, entity);
        setTicking(true);
        if (!data.isPresent("resource")) {
            Skykid.LOGGER.error("No resource provided for LightManagementPower.");
            return;
        }
        DATA = data;
        resourcePower = tryInitializeResource(entity, data.get("resource"));
    }


    @Override
    public void tick() {
        if (resourcePower == null && !entity.getWorld().isClient) {
            tryInitializeResource(entity, DATA.get("resource"));
            return;
        }

        if (!entity.getWorld().isClient && resourcePower != null) {
            int gainAmount = calculateLightGain();
            if (gainAmount > 0) {
                Skykid.LOGGER.debug(String.valueOf(((resourcePower.getValue() + gainAmount))));
                resourcePower.setValue(resourcePower.getValue() + gainAmount);
                PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
                component.sync();
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

        int blockLightGain;
        // Check for lit candle cakes first (highest priority)
        if (hasNearbyBlock(block ->
                block.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "candle_cakes")))
                        && block.get(Properties.LIT))) {
            blockLightGain = 40;
        }
        else {
            blockLightGain = calculateBlockLightGain();
        }

        int itemLightGain = calculateItemLightGain();

        return blockLightGain + itemLightGain;
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
        int maxSources = 3;
        // TODO: Allow ascended skykid to be able to use 4 block sources at one time
        for (LightValue value : values) {
            int remaining = Math.min(maxSources - blocksUsed, value.count);
            if (remaining > 0) {
                totalGain += remaining * value.gain;
                blocksUsed += remaining;
            }
            if (blocksUsed >= maxSources) break;
        }

        return totalGain;
    }

    private int calculateItemLightGain() {
        ItemStack mainHand = entity.getMainHandStack();
        ItemStack offHand = entity.getOffHandStack();

        return Math.max(getItemLightGain(mainHand), getItemLightGain(offHand));
    }

    private int getItemLightGain(ItemStack stack) {
        if (stack.isEmpty()) return 0;

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

        if (currentTime - lastCacheUpdate >= CACHE_DURATION) {
            lightSourceCache.clear();
            lightSourceCache.putAll(scanNearbyLightSources());
            lastCacheUpdate = currentTime;
        }

        return lightSourceCache.getOrDefault(type, 0);

    }

    private Map<LightSourceType, Integer> scanNearbyLightSources() {
        Map<LightSourceType, Integer> counts = new EnumMap<>(LightSourceType.class);
        BlockPos entityPos = entity.getBlockPos();
        int radiusSquared = RADIUS * RADIUS;

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    if (x*x + y*y + z*z > radiusSquared) {
                        continue;
                    }

                    BlockPos checkPos = entityPos.add(x, y, z);
                    BlockState state = entity.getWorld().getBlockState(checkPos);
                    for (LightSourceType type : LightSourceType.values()) {
                        if (isValidLightSource(state, type)) {
                            counts.merge(type, 1, Integer::sum);
                        }
                    }
                }
            }
        }
        return counts;
    }


    private boolean isValidLightSource(BlockState state, LightSourceType type) {
        boolean valid = false;
        switch (type) {
            case PRIME:
                // Prime sources: Glowstone, Sea Lantern, etc.
                if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "candles")))){
                    // 4 candles that are lit
                    valid = state.get(Properties.LIT) && state.get(Properties.CANDLES) >= 4;
                }
                else if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "prime_sources")))){
                    valid = true;
                }
                break;
            case AVERAGE:
                // Average sources: Torches, Lanterns, etc.
                if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "candles")))){
                    // 2 or 3 candles that are lit
                    valid = state.get(Properties.LIT) && (state.get(Properties.CANDLES) == 2 || state.get(Properties.CANDLES) == 3);
                }
                else if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "average_sources")))){
                    valid = true;
                }
                break;
            case WEAK:
                // Weak sources: Redstone Torch, Soul Lantern, etc.
                if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "candles")))){
                    // 1 candle that is lit
                    valid = state.get(Properties.LIT) && state.get(Properties.CANDLES) <= 1;
                }
                else if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of("skykid", "weak_sources")))){
                    valid = true;
                }
                break;
            default:
                break;
        }
        return valid;
    }

    private enum LightSourceType {
        PRIME, AVERAGE, WEAK
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
                new Identifier("skykid", "light_management"),
                new SerializableData().add("resource", ApoliDataTypes.POWER_TYPE, null),
                data -> (type, player) -> new LightManagementPower(type, player, data)
        ).allowCondition();
    }
}