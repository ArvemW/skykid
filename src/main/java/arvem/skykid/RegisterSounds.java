package arvem.skykid;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class RegisterSounds {
    public static final Identifier CAPE_FLAP = new Identifier("skykid:player.flap");
    public static SoundEvent CAPEFLAP = SoundEvent.of(CAPE_FLAP);
    public static final Identifier CAPE_BOOST = new Identifier("skykid:player.boost");
    public static SoundEvent CAPEBOOST = SoundEvent.of(CAPE_BOOST);
    public static final Identifier CAPE_WHOOSH = new Identifier("skykid:player.whoosh");
    public static SoundEvent CAPEWHOOSH = SoundEvent.of(CAPE_WHOOSH);
    public static final Identifier SHOUT_DEFAULT = new Identifier("skykid:player.default");
    public static SoundEvent SHOUTDEFAULT = SoundEvent.of(SHOUT_DEFAULT);
    public static final Identifier SHOUT_BIRD = new Identifier("skykid:player.bird");
    public static SoundEvent SHOUTBIRD = SoundEvent.of(SHOUT_BIRD);
    public static final Identifier SHOUT_CRAB = new Identifier("skykid:player.crab");
    public static SoundEvent SHOUTCRAB = SoundEvent.of(SHOUT_CRAB);
    public static final Identifier SHOUT_GHOST = new Identifier("skykid:player.ghost");
    public static SoundEvent SHOUTGHOST = SoundEvent.of(SHOUT_GHOST);
    public static final Identifier SHOUT_WHALE = new Identifier("skykid:player.whale");
    public static SoundEvent SHOUTWHALE = SoundEvent.of(SHOUT_WHALE);
    public static final Identifier SHOUT_JELLY = new Identifier("skykid:player.jelly");
    public static SoundEvent SHOUTJELLY = SoundEvent.of(SHOUT_JELLY);
    public static final Identifier SHOUT_MANTA = new Identifier("skykid:player.manta");
    public static SoundEvent SHOUTMANTA = SoundEvent.of(SHOUT_MANTA);
    public static final Identifier BEAT_SLOW = new Identifier("skykid:player.beatslow");
    public static SoundEvent BEATSLOW = SoundEvent.of(BEAT_SLOW);
    public static final Identifier BEAT_MID = new Identifier("skykid:player.beatmid");
    public static SoundEvent BEATMID = SoundEvent.of(BEAT_MID);
    public static final Identifier BEAT_FAST = new Identifier("skykid:player.beatfast");
    public static SoundEvent BEATFAST = SoundEvent.of(BEAT_FAST);

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, CAPE_FLAP, CAPEFLAP);
        Registry.register(Registries.SOUND_EVENT, CAPE_BOOST, CAPEBOOST);
        Registry.register(Registries.SOUND_EVENT, CAPE_WHOOSH, CAPEWHOOSH);
        Registry.register(Registries.SOUND_EVENT, SHOUT_DEFAULT, SHOUTDEFAULT);
        Registry.register(Registries.SOUND_EVENT, SHOUT_BIRD, SHOUTBIRD);
        Registry.register(Registries.SOUND_EVENT, SHOUT_CRAB, SHOUTCRAB);
        Registry.register(Registries.SOUND_EVENT, SHOUT_GHOST, SHOUTGHOST);
        Registry.register(Registries.SOUND_EVENT, SHOUT_WHALE, SHOUTWHALE);
        Registry.register(Registries.SOUND_EVENT, SHOUT_JELLY, SHOUTJELLY);
        Registry.register(Registries.SOUND_EVENT, SHOUT_MANTA, SHOUTMANTA);
        Registry.register(Registries.SOUND_EVENT, BEAT_SLOW, BEATSLOW);
        Registry.register(Registries.SOUND_EVENT, BEAT_MID, BEATMID);
        Registry.register(Registries.SOUND_EVENT, BEAT_FAST, BEATFAST);
    }
}
