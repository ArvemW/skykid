package arvem.skykid.registries

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

object RegisterSounds {
    private val CAPE_FLAP = Identifier("skykid:player.flap")
    private var CAPE_FLAP_EVENT: SoundEvent = SoundEvent.of(CAPE_FLAP)
    private val CAPE_BOOST = Identifier("skykid:player.boost")
    private var CAPE_BOOST_EVENT: SoundEvent = SoundEvent.of(CAPE_BOOST)
    private val CAPE_WHOOSH = Identifier("skykid:player.whoosh")
    private var CAPE_WHOOSH_EVENT: SoundEvent = SoundEvent.of(CAPE_WHOOSH)
    private val SHOUT_DEFAULT = Identifier("skykid:player.default")
    private var SHOUT_DEFAULT_EVENT: SoundEvent = SoundEvent.of(SHOUT_DEFAULT)
    private val SHOUT_BIRD = Identifier("skykid:player.bird")
    private var SHOUT_BIRD_EVENT: SoundEvent = SoundEvent.of(SHOUT_BIRD)
    private val SHOUT_CRAB = Identifier("skykid:player.crab")
    private var SHOUT_CRAB_EVENT: SoundEvent = SoundEvent.of(SHOUT_CRAB)
    private val SHOUT_GHOST = Identifier("skykid:player.ghost")
    private var SHOUT_GHOST_EVENT: SoundEvent = SoundEvent.of(SHOUT_GHOST)
    private val SHOUT_WHALE = Identifier("skykid:player.whale")
    private var SHOUT_WHALE_EVENT: SoundEvent = SoundEvent.of(SHOUT_WHALE)
    private val SHOUT_JELLY = Identifier("skykid:player.jelly")
    private var SHOUT_JELLY_EVENT: SoundEvent = SoundEvent.of(SHOUT_JELLY)
    private val SHOUT_MANTA = Identifier("skykid:player.manta")
    private var SHOUT_MANTA_EVENT: SoundEvent = SoundEvent.of(SHOUT_MANTA)
    private val BEAT_SLOW = Identifier("skykid:player.beatslow")
    private var BEAT_SLOW_EVENT: SoundEvent = SoundEvent.of(BEAT_SLOW)
    private val BEAT_MID = Identifier("skykid:player.beatmid")
    private var BEAT_MID_EVENT: SoundEvent = SoundEvent.of(BEAT_MID)
    private val BEAT_FAST = Identifier("skykid:player.beatfast")
    private var BEAT_FAST_EVENT: SoundEvent = SoundEvent.of(BEAT_FAST)
    fun init() {
        Registry.register(Registries.SOUND_EVENT, CAPE_FLAP, CAPE_FLAP_EVENT)
        Registry.register(Registries.SOUND_EVENT, CAPE_BOOST, CAPE_BOOST_EVENT)
        Registry.register(Registries.SOUND_EVENT, CAPE_WHOOSH, CAPE_WHOOSH_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_DEFAULT, SHOUT_DEFAULT_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_BIRD, SHOUT_BIRD_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_CRAB, SHOUT_CRAB_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_GHOST, SHOUT_GHOST_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_WHALE, SHOUT_WHALE_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_JELLY, SHOUT_JELLY_EVENT)
        Registry.register(Registries.SOUND_EVENT, SHOUT_MANTA, SHOUT_MANTA_EVENT)
        Registry.register(Registries.SOUND_EVENT, BEAT_SLOW, BEAT_SLOW_EVENT)
        Registry.register(Registries.SOUND_EVENT, BEAT_MID, BEAT_MID_EVENT)
        Registry.register(Registries.SOUND_EVENT, BEAT_FAST, BEAT_FAST_EVENT)
    }
}
