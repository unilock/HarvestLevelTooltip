package cc.unilock.harvestleveltooltip.util;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.util.Identifier;

import static cc.unilock.harvestleveltooltip.util.HLTConstants.DIAMOND;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.GOLD;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.IRON;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.NETHERITE;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.STONE;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.WOODEN;

public class HLTag2Tier {
	public static final HLTag2Tier INSTANCE = new HLTag2Tier();

	private final Object2IntArrayMap<Identifier> tagTiers = new Object2IntArrayMap<>();

	public void clear() {
		this.tagTiers.clear();
	}

	public void load(Object2IntArrayMap<Identifier> tagTiers) {
		this.tagTiers.putAll(tagTiers);
	}

	public String getTier(Identifier id) {
		if (WOODEN.equals(id) || GOLD.equals(id)) {
			return "wooden";
		}
		if (STONE.equals(id)) {
			return "stone";
		}
		if (IRON.equals(id)) {
			return "iron";
		}
		if (DIAMOND.equals(id)) {
			return "diamond";
		}
		if (NETHERITE.equals(id)) {
			return "netherite";
		}

		return switch (this.tagTiers.getInt(id)) {
			case 1 -> "wooden";
			case 2 -> "stone";
			case 3 -> "iron";
			case 4 -> "diamond";
			case 5 -> "netherite";
			default -> null;
		};
	}
}
