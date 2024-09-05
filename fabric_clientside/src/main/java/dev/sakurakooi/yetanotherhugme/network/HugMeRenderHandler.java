package dev.sakurakooi.yetanotherhugme.network;

import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeAnimationRenderPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class HugMeRenderHandler implements ClientPlayNetworking.PlayPayloadHandler<S2CHugMeAnimationRenderPacket> {
    @Override
    public void receive(S2CHugMeAnimationRenderPacket payload, ClientPlayNetworking.Context context) {
        // TODO unimplemented
    }
}
