package dev.sakurakooi.yetanotherhugme.packet;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record S2CHugMeHandshakeReqPacket(int version) implements CustomPayload {
    public static final CustomPayload.Id<S2CHugMeHandshakeReqPacket> ID = new Id<>(Identifier.of(YetAnotherHugMe.MOD_ID, "handshake"));
    public static final PacketCodec<RegistryByteBuf, S2CHugMeHandshakeReqPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, S2CHugMeHandshakeReqPacket::version,
            S2CHugMeHandshakeReqPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
