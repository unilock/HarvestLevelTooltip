package cc.unilock.harvestleveltooltip.mixin;

import cc.unilock.harvestleveltooltip.util.HLTag2Tier;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@ModifyReturnValue(method = "getTooltip(Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/tooltip/TooltipType;)Ljava/util/List;", at = @At("RETURN"))
	private List<Text> getTooltip(List<Text> tooltip, Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type) {
		if (Screen.hasShiftDown()) {
			final ItemStack stack = (ItemStack) (Object) this;

			if (stack.getItem() instanceof MiningToolItem tool) {
				ToolMaterial material = tool.getMaterial();
				Identifier id = material.getInverseTag().id();
				String key = id.toTranslationKey("harvest_level");

				if (I18n.hasTranslation(key)) {
					tooltip.add(Text.translatable("harvestleveltooltip.harvest_level", I18n.translate(key)).formatted(Formatting.GRAY));
				} else {
					String tier = HLTag2Tier.INSTANCE.getTier(id);

					if (tier != null) {
						tooltip.add(Text.translatable("harvestleveltooltip.harvest_level", tier).formatted(Formatting.GRAY));
					} else {
						tooltip.add(Text.translatable("harvestleveltooltip.inverse_tag", id.toString()).formatted(Formatting.GRAY));
					}
				}
			}
		}

		return tooltip;
	}
}
