package dev.sakurakooi.yetanotherhugme.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record C2SHugMeHandshakeAckPacket(int version) {
    public static final String ID = "yetanotherhugme:handshake";

    public static final BiConsumer<ByteArrayDataOutput, C2SHugMeHandshakeAckPacket> writer = (buf, packet) -> {
        buf.writeInt(packet.version);
    };
    public static final Function<ByteArrayDataInput, C2SHugMeHandshakeAckPacket> READER = buf -> {
        return new C2SHugMeHandshakeAckPacket(buf.readInt());
    };

}
