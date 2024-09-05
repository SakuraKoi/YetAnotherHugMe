package dev.sakurakooi.yetanotherhugme.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class NettySerializerWrapper {
    public static <T> byte[] writePacket(BiConsumer<ByteArrayDataOutput, T> writer, T packet) {
        ByteBuf buf = Unpooled.buffer();
        var dataOutput = ByteStreams.newDataOutput();
        writer.accept(dataOutput, packet);
        byte[] bytes = dataOutput.toByteArray();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public static <T> T readPacket(Function<ByteArrayDataInput, T> reader, byte[] packet) {
        ByteBuf buf = Unpooled.wrappedBuffer(packet);
        int length = buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return reader.apply(ByteStreams.newDataInput(bytes));
    }
}
