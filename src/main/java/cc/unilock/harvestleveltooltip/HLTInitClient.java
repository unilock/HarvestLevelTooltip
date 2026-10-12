package cc.unilock.harvestleveltooltip;

import cc.unilock.harvestleveltooltip.networking.TagTiersPayload;
import cc.unilock.harvestleveltooltip.util.HLTConstants;
import cc.unilock.harvestleveltooltip.util.HLTag2Tier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class HLTInitClient implements ClientModInitializer {
	private static final Identifier HLT_PHASE = HLTConstants.id("hlt");

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(TagTiersPayload.ID, (pkt, ctx) -> {
			HLTag2Tier.INSTANCE.load(pkt.tagTiers());
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			HLTag2Tier.INSTANCE.clear();
		});

		ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, HLT_PHASE);
		ItemTooltipCallback.EVENT.register(HLT_PHASE, (stack, tooltipContext, tooltipType, lines) -> {
			if (stack.getItem() instanceof MiningToolItem tool) {
				ToolMaterial material = tool.getMaterial();
				Identifier id = material.getInverseTag().id();
				String key = id.toTranslationKey("harvest_level");

				if (I18n.hasTranslation(key)) {
					lines.add(Text.translatable("harvestleveltooltip.harvest_level", I18n.translate(key)).formatted(Formatting.GRAY));
				} else {
					String tier = HLTag2Tier.INSTANCE.getTier(id);

					if (tier != null) {
						lines.add(Text.translatable("harvestleveltooltip.harvest_level", tier).formatted(Formatting.GRAY));
					} else {
						lines.add(Text.translatable("harvestleveltooltip.inverse_tag", id.toString()).formatted(Formatting.GRAY));
					}
				}
			}
		});
	}
}
