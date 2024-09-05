package dev.sakurakooi.yetanotherhugme.packet;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.utils.NettySerializerWrapper;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record S2CHugMeAnimationRenderPacket(UUID player1, UUID player2, String hugType, boolean endFlag) implements CustomPayload {
    public static final Id<S2CHugMeAnimationRenderPacket> ID = new Id<>(Identifier.of(YetAnotherHugMe.MOD_ID, "animation"));
    public static final PacketCodec<RegistryByteBuf, S2CHugMeAnimationRenderPacket> CODEC = PacketCodec.ofStatic(NettySerializerWrapper.writer((buf, packet) -> {
        buf.writeUTF(packet.player1().toString());
        buf.writeUTF(packet.player2().toString());
        buf.writeUTF(packet.hugType());
        buf.writeBoolean(packet.endFlag());
    }), NettySerializerWrapper.reader(buf -> new S2CHugMeAnimationRenderPacket(UUID.fromString(buf.readUTF()), UUID.fromString(buf.readUTF()), buf.readUTF(), buf.readBoolean())));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
