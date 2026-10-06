package cc.unilock.harvestleveltooltip.util;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HLTConstants {
	public static final String MOD_ID = "harvestleveltooltip";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	protected static final Identifier WOODEN = BlockTags.INCORRECT_FOR_WOODEN_TOOL.id();
	protected static final Identifier STONE = BlockTags.INCORRECT_FOR_STONE_TOOL.id();
	protected static final Identifier IRON = BlockTags.INCORRECT_FOR_IRON_TOOL.id();
	protected static final Identifier GOLD = BlockTags.INCORRECT_FOR_GOLD_TOOL.id();
	protected static final Identifier DIAMOND = BlockTags.INCORRECT_FOR_DIAMOND_TOOL.id();
	protected static final Identifier NETHERITE = BlockTags.INCORRECT_FOR_NETHERITE_TOOL.id();

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
