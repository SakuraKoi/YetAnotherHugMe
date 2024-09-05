package dev.sakurakooi.yetanotherhugme.packet;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record S2CHugMeAnimationRenderPacket(UUID player1, UUID player2, String hugType, boolean endFlag) implements CustomPayload {
    public static final Id<S2CHugMeAnimationRenderPacket> ID = new Id<>(Identifier.of(YetAnotherHugMe.MOD_ID, "animation"));
    public static final PacketCodec<RegistryByteBuf, S2CHugMeAnimationRenderPacket> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC, S2CHugMeAnimationRenderPacket::player1,
            Uuids.PACKET_CODEC, S2CHugMeAnimationRenderPacket::player2,
            PacketCodecs.STRING, S2CHugMeAnimationRenderPacket::hugType,
            PacketCodecs.BOOL, S2CHugMeAnimationRenderPacket::endFlag,
            S2CHugMeAnimationRenderPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
