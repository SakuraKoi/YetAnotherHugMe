package dev.sakurakooi.yetanotherhugme.network;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import dev.sakurakooi.yetanotherhugme.packet.C2SHugMeHandshakeAckPacket;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeHandshakeReqPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class HugMeHandshakeHandler implements ClientPlayNetworking.PlayPayloadHandler<S2CHugMeHandshakeReqPacket> {
    @Override
    public void receive(S2CHugMeHandshakeReqPacket payload, ClientPlayNetworking.Context context) {
        YetAnotherHugMe.INSTANCE.doCleanup();
        ClientPlayNetworking.send(new C2SHugMeHandshakeAckPacket(1));
    }
}
