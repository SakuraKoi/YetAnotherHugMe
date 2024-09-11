package dev.sakurakooi.yetanotherhugme.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public record S2CHugMeAnimationRenderPacket(UUID player1, UUID player2, String hugType, boolean endFlag) {
    public static final String ID = "yetanotherhugme:animation";

    public static final BiConsumer<ByteArrayDataOutput, S2CHugMeAnimationRenderPacket> WRITER = (buf, packet) -> {
        buf.writeUTF(packet.player1().toString());
        buf.writeUTF(packet.player2().toString());
        buf.writeUTF(packet.hugType());
        buf.writeBoolean(packet.endFlag());
    };

    public static final Function<ByteArrayDataInput, S2CHugMeAnimationRenderPacket> READER = buf -> {
        return new S2CHugMeAnimationRenderPacket(UUID.fromString(buf.readUTF()), UUID.fromString(buf.readUTF()), buf.readUTF(), buf.readBoolean());
    };
}
