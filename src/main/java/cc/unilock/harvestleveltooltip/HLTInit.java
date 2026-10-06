package cc.unilock.harvestleveltooltip;

import cc.unilock.harvestleveltooltip.networking.TagTiersPayload;
import cc.unilock.harvestleveltooltip.util.HLTConstants;
import cc.unilock.harvestleveltooltip.util.HLTagProcessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class HLTInit implements ModInitializer {
	@Override
	public void onInitialize() {
		HLTConstants.LOGGER.info("Harvest Level: <random entry here>");

		PayloadTypeRegistry.playS2C().register(TagTiersPayload.ID, TagTiersPayload.CODEC);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			if (ServerPlayNetworking.canSend(handler.getPlayer(), TagTiersPayload.ID)) {
				ServerPlayNetworking.send(handler.getPlayer(), new TagTiersPayload(HLTagProcessor.INSTANCE.getTagTiers()));
			}
		});
	}
}
