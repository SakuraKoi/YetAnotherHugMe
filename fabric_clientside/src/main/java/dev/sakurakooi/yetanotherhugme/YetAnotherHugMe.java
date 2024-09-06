package dev.sakurakooi.yetanotherhugme;

import dev.sakurakooi.yetanotherhugme.animation.HugAnimationEnum;
import dev.sakurakooi.yetanotherhugme.network.HugMeHandshakeHandler;
import dev.sakurakooi.yetanotherhugme.network.HugMeRenderHandler;
import dev.sakurakooi.yetanotherhugme.packet.C2SHugMeHandshakeAckPacket;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeAnimationRenderPacket;
import dev.sakurakooi.yetanotherhugme.packet.S2CHugMeHandshakeReqPacket;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.sakurakooi.yetanotherhugme.animation.AnimationManager;

public class YetAnotherHugMe implements ModInitializer {
	public static final String MOD_ID = "yetanotherhugme";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static YetAnotherHugMe INSTANCE;

	@Getter @Setter
	private boolean renderingPlayer;

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

	public void startHugAnimation(@Nullable PlayerEntity player1, @Nullable PlayerEntity player2, String animationEnumName) {
		if (player1 == null || player2 == null) {
			LOGGER.warn("startHugAnimation: player1 or player2 is null");
			return;
		}
		HugAnimationEnum animation;
		try {
			animation = HugAnimationEnum.valueOf(animationEnumName);
		} catch (IllegalArgumentException e) {
			LOGGER.warn("startHugAnimation: invalid animationEnumName: {}", animationEnumName);
			return;
		}
		// TODO unimplemented
		// RenderPlayerEventHandler.lockPlayers(sender, receiver);
		// TODO unlock after HugAnimationEnum.getAnimationTick
		AnimationManager.playHugAnimation(player1, player2, animation);
	}

	public void endHugAnimation(@Nullable PlayerEntity player1, @Nullable PlayerEntity player2) {
		// TODO unimplemented
		// RenderPlayerEventHandler.unlockPlayers(sender, receiver);
		AnimationManager.endHugAnimation(player1, player2);
	}
}