package arvem.skykid

import arvem.skykid.item.RegisterItems
import net.fabricmc.api.ModInitializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import org.slf4j.LoggerFactory


object Skykid : ModInitializer {
	const val MODID = "skykid"
	const val MODNAME = "Sky Kid"

    private val logger = LoggerFactory.getLogger(MODNAME)

	override fun onInitialize() {
		logger.info("When kingdom come...")
		RegisterItems.init()
		RegisterSounds.init()
	}
}