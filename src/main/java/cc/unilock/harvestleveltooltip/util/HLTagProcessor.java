package cc.unilock.harvestleveltooltip.util;

import cc.unilock.harvestleveltooltip.mixin.TagEntryAccessor;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cc.unilock.harvestleveltooltip.util.HLTConstants.DIAMOND;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.GOLD;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.IRON;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.NETHERITE;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.STONE;
import static cc.unilock.harvestleveltooltip.util.HLTConstants.WOODEN;

public class HLTagProcessor {
	public static final HLTagProcessor INSTANCE = new HLTagProcessor();

	private static final int PREFIX = "tags/block/".length();
	private static final int SUFFIX = ".json".length();

	private final Multimap<Identifier, TagEntry> tagLookup = MultimapBuilder.hashKeys().hashSetValues().build();
	private final Object2IntArrayMap<Identifier> tagTiers = new Object2IntArrayMap<>();

	public void clear() {
		this.tagLookup.clear();
		this.tagTiers.clear();
	}

	public Object2IntArrayMap<Identifier> getTagTiers() {
		return this.tagTiers;
	}

	public void loadTag(Identifier tagId, List<TagEntry> tagEntries) {
		this.tagLookup.putAll(Identifier.of(tagId.getNamespace(), tagId.getPath().substring(PREFIX, tagId.getPath().length() - SUFFIX)), tagEntries);
	}

	public void tagsLoaded() {
		this.tagLookup.asMap().entrySet().parallelStream().forEach(entry -> {
			int value = processTagEntries(entry.getValue());
			if (value != 0) {
				this.tagTiers.put(entry.getKey(), value);
			}
		});
	}

	private int processTagEntries(Collection<TagEntry> tagEntries) {
		Set<Identifier> tagIds = tagEntries.parallelStream()
				.filter(tagEntry -> ((TagEntryAccessor) tagEntry).getTag())
				.map(tagEntry -> ((TagEntryAccessor) tagEntry).getId())
				.collect(Collectors.toUnmodifiableSet());

		if (tagIds.contains(WOODEN) || tagIds.contains(GOLD)) {
			return 1;
		} else if (tagIds.contains(STONE)) {
			return 2;
		} else if (tagIds.contains(IRON)) {
			return 3;
		} else if (tagIds.contains(DIAMOND)) {
			return 4;
		} else if (tagIds.contains(NETHERITE)) {
			return 5;
		} else if (!tagIds.isEmpty()) {
			Set<TagEntry> resolved = tagIds.parallelStream()
					.map(this.tagLookup::get)
					.flatMap(Collection::parallelStream)
					.collect(Collectors.toUnmodifiableSet());
			return processTagEntries(resolved);
		}

		return 0;
	}
}
