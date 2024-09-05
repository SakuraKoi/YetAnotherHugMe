package dev.sakurakooi.yetanotherhugme.packet;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.utils.NettySerializerWrapper;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record S2CHugMeHandshakeReqPacket(int version) implements CustomPayload {
    public static final CustomPayload.Id<S2CHugMeHandshakeReqPacket> ID = new Id<>(Identifier.of(YetAnotherHugMe.MOD_ID, "handshake"));
    public static final PacketCodec<RegistryByteBuf, S2CHugMeHandshakeReqPacket> CODEC = PacketCodec.ofStatic(NettySerializerWrapper.writer((buf, packet) -> {
        buf.writeInt(packet.version);
    }), NettySerializerWrapper.reader(buf -> new S2CHugMeHandshakeReqPacket(buf.readInt())));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
