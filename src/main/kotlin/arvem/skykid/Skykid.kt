package arvem.skykid

import arvem.skykid.registries.RegisterItems
import arvem.skykid.registries.RegisterSounds
import net.fabricmc.api.ModInitializer
import net.minecraft.registry.RegistryKeys.BLOCK
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory


object Skykid : ModInitializer {
	const val MODID = "skykid"
	const val MODNAME = "Sky Kid"
	val prime_sources = TagKey.of(BLOCK, Identifier(MODID, "prime_sources"))
	private val logger = LoggerFactory.getLogger(MODNAME)

	override fun onInitialize() {
		RegisterItems.init()
		RegisterSounds.init()
		logger.info("When kingdom come...")
	}
}

