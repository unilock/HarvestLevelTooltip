package cc.unilock.harvestleveltooltip.networking;

import cc.unilock.harvestleveltooltip.util.HLTConstants;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record TagTiersPayload(Object2IntArrayMap<Identifier> tagTiers) implements CustomPayload {
	public static final Id<TagTiersPayload> ID = new Id<>(HLTConstants.id("tagtiers"));
	public static final PacketCodec<ByteBuf, TagTiersPayload> CODEC = PacketCodecs
			.map(Object2IntArrayMap::new, Identifier.PACKET_CODEC, PacketCodecs.INTEGER)
			.xmap(TagTiersPayload::new, TagTiersPayload::tagTiers);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
