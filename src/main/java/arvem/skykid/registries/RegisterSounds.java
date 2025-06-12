package arvem.skykid.registries;

import arvem.skykid.Skykid;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class RegisterSounds {
    // Identifiers
    private static final Identifier CAPE_FLAP_ID       = Identifier.of(Skykid.MODID, "player.flap");
    private static final Identifier CAPE_BOOST_ID      = Identifier.of(Skykid.MODID, "player.boost");
    private static final Identifier CAPE_WHOOSH_ID     = Identifier.of(Skykid.MODID, "player.whoosh");
    private static final Identifier SHOUT_DEFAULT_ID   = Identifier.of(Skykid.MODID, "player.default");
    private static final Identifier SHOUT_BIRD_ID      = Identifier.of(Skykid.MODID, "player.bird");
    private static final Identifier SHOUT_CRAB_ID      = Identifier.of(Skykid.MODID, "player.crab");
    private static final Identifier SHOUT_GHOST_ID     = Identifier.of(Skykid.MODID, "player.ghost");
    private static final Identifier SHOUT_WHALE_ID     = Identifier.of(Skykid.MODID, "player.whale");
    private static final Identifier SHOUT_JELLY_ID     = Identifier.of(Skykid.MODID, "player.jelly");
    private static final Identifier SHOUT_MANTA_ID     = Identifier.of(Skykid.MODID, "player.manta");
    private static final Identifier BEAT_SLOW_ID       = Identifier.of(Skykid.MODID, "player.beatslow");
    private static final Identifier BEAT_MID_ID        = Identifier.of(Skykid.MODID, "player.beatmid");
    private static final Identifier BEAT_FAST_ID       = Identifier.of(Skykid.MODID, "player.beatfast");

    // SoundEvent instances
    public static final SoundEvent CAPE_FLAP_EVENT     = SoundEvent.of(CAPE_FLAP_ID);
    public static final SoundEvent CAPE_BOOST_EVENT    = SoundEvent.of(CAPE_BOOST_ID);
    public static final SoundEvent CAPE_WHOOSH_EVENT   = SoundEvent.of(CAPE_WHOOSH_ID);
    public static final SoundEvent SHOUT_DEFAULT_EVENT = SoundEvent.of(SHOUT_DEFAULT_ID);
    public static final SoundEvent SHOUT_BIRD_EVENT    = SoundEvent.of(SHOUT_BIRD_ID);
    public static final SoundEvent SHOUT_CRAB_EVENT    = SoundEvent.of(SHOUT_CRAB_ID);
    public static final SoundEvent SHOUT_GHOST_EVENT   = SoundEvent.of(SHOUT_GHOST_ID);
    public static final SoundEvent SHOUT_WHALE_EVENT   = SoundEvent.of(SHOUT_WHALE_ID);
    public static final SoundEvent SHOUT_JELLY_EVENT   = SoundEvent.of(SHOUT_JELLY_ID);
    public static final SoundEvent SHOUT_MANTA_EVENT   = SoundEvent.of(SHOUT_MANTA_ID);
    public static final SoundEvent BEAT_SLOW_EVENT     = SoundEvent.of(BEAT_SLOW_ID);
    public static final SoundEvent BEAT_MID_EVENT      = SoundEvent.of(BEAT_MID_ID);
    public static final SoundEvent BEAT_FAST_EVENT     = SoundEvent.of(BEAT_FAST_ID);

    public static void init() {
        // Single-line registrations via helper
        register(CAPE_FLAP_ID,     CAPE_FLAP_EVENT);
        register(CAPE_BOOST_ID,    CAPE_BOOST_EVENT);
        register(CAPE_WHOOSH_ID,   CAPE_WHOOSH_EVENT);
        register(SHOUT_DEFAULT_ID, SHOUT_DEFAULT_EVENT);
        register(SHOUT_BIRD_ID,    SHOUT_BIRD_EVENT);
        register(SHOUT_CRAB_ID,    SHOUT_CRAB_EVENT);
        register(SHOUT_GHOST_ID,   SHOUT_GHOST_EVENT);
        register(SHOUT_WHALE_ID,   SHOUT_WHALE_EVENT);
        register(SHOUT_JELLY_ID,   SHOUT_JELLY_EVENT);
        register(SHOUT_MANTA_ID,   SHOUT_MANTA_EVENT);
        register(BEAT_SLOW_ID,     BEAT_SLOW_EVENT);
        register(BEAT_MID_ID,      BEAT_MID_EVENT);
        register(BEAT_FAST_ID,     BEAT_FAST_EVENT);
    }

    private static void register(Identifier id, SoundEvent event) {
        Registry.register(Registries.SOUND_EVENT, id, event);
    }
}
