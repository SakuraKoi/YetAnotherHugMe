package dev.sakurakooi.yetanotherhugme;

import dev.sakurakooi.yetanotherhugme.network.HugMeHandshakeHandler;
import dev.sakurakooi.yetanotherhugme.network.HugMeRenderHandler;
import dev.sakurakooi.yetanotherhugme.packet.C2SHugMeHandshakeAckPacket;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeAnimationRenderPacket;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeHandshakeReqPacket;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YetAnotherHugMe implements ModInitializer {
	public static final String MOD_ID = "yetanotherhugme";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static YetAnotherHugMe INSTANCE;

	@Override
	public void onInitialize() {
		INSTANCE = this;
		PayloadTypeRegistry.playS2C().register(S2CHugMeHandshakeReqPacket.ID, S2CHugMeHandshakeReqPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(C2SHugMeHandshakeAckPacket.ID, C2SHugMeHandshakeAckPacket.CODEC);

		PayloadTypeRegistry.playS2C().register(S2CHugMeAnimationRenderPacket.ID, S2CHugMeAnimationRenderPacket.CODEC);

		ClientPlayNetworking.registerGlobalReceiver(S2CHugMeHandshakeReqPacket.ID, new HugMeHandshakeHandler());
		ClientPlayNetworking.registerGlobalReceiver(S2CHugMeAnimationRenderPacket.ID, new HugMeRenderHandler());

		ClientLoginConnectionEvents.DISCONNECT.register((handler, client) -> {
			doCleanup();
		});
	}

	// called from client disconnect event / server handshake packet(handle bungeecord switch server)
	public void doCleanup() {
		// TODO cleanup data
	}
}