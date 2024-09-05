package dev.sakurakooi.yetanotherhugme.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record S2CHugMeHandshakeReqPacket(int version) {
    public static final String ID = "yetanotherhugme:handshake";

    public static final BiConsumer<ByteArrayDataOutput, S2CHugMeHandshakeReqPacket> WRITER = (buf, packet) -> {
        buf.writeInt(packet.version);
    };

    public static final Function<ByteArrayDataInput, S2CHugMeHandshakeReqPacket> READER = buf -> {
        return new S2CHugMeHandshakeReqPacket(buf.readInt());
    };
}
