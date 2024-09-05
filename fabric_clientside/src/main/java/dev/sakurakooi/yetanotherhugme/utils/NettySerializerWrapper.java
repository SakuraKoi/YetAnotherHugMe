package dev.sakurakooi.yetanotherhugme.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketDecoder;
import net.minecraft.network.codec.PacketEncoder;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class NettySerializerWrapper {

    public static <T> PacketEncoder<RegistryByteBuf, T> writer(BiConsumer<ByteArrayDataOutput, T> writer) {
        return (buf, value) -> {
            var dataOutput = ByteStreams.newDataOutput();
                writer.accept(dataOutput, value);
            byte[] bytes = dataOutput.toByteArray();
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        };
    }

    public static <T> PacketDecoder<RegistryByteBuf, T> reader(Function<ByteArrayDataInput, T> reader) {
        return (buf) -> {
            int length = buf.readInt();
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return reader.apply(ByteStreams.newDataInput(bytes));
        };
    }
}
