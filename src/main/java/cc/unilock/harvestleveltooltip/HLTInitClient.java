package cc.unilock.harvestleveltooltip;

import cc.unilock.harvestleveltooltip.networking.TagTiersPayload;
import cc.unilock.harvestleveltooltip.util.HLTag2Tier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class HLTInitClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(TagTiersPayload.ID, (pkt, ctx) -> {
			HLTag2Tier.INSTANCE.load(pkt.tagTiers());
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			HLTag2Tier.INSTANCE.clear();
		});
	}
}
