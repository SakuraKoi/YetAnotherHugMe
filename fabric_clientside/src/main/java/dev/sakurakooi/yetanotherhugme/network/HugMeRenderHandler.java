package dev.sakurakooi.yetanotherhugme.network;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeAnimationRenderPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class HugMeRenderHandler implements ClientPlayNetworking.PlayPayloadHandler<S2CHugMeAnimationRenderPacket> {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void receive(S2CHugMeAnimationRenderPacket payload, ClientPlayNetworking.Context context) {
        try {
            if (client.world == null) return;
            var player1 = client.world.getPlayerByUuid(payload.player1());
            var player2 = client.world.getPlayerByUuid(payload.player2());
            if (player1 == null || player2 == null) return;
            if (payload.endFlag()) {
                YetAnotherHugMe.INSTANCE.endHugAnimation(player1, player2);
            } else {
                YetAnotherHugMe.INSTANCE.startHugAnimation(player1, player2, payload.hugType());
            }
        } catch (Exception e) {
            YetAnotherHugMe.LOGGER.error("An unexpected exception caught in HugMeRenderHandler", e);
            context.responseSender().disconnect(Text.translatable("yetanotherhugme.network.unexpectedException", e.getMessage()));
        }
    }
}
