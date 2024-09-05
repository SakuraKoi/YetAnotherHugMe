package dev.sakurakooi.yetanotherhugme.packet;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record C2SHugMeHandshakeAckPacket(int version) implements CustomPayload {
    public static final Id<C2SHugMeHandshakeAckPacket> ID = new Id<>(Identifier.of(YetAnotherHugMe.MOD_ID, "handshake"));
    public static final PacketCodec<RegistryByteBuf, C2SHugMeHandshakeAckPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, C2SHugMeHandshakeAckPacket::version,
            C2SHugMeHandshakeAckPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
