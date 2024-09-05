package dev.sakurakooi.yetanotherhugme.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.sakurakooi.yetanotherhugme.IHugMeAnimatedPlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.encryption.PlayerPublicKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IHugMeAnimatedPlayer {
	@Unique
	private final ModifierLayer<IAnimation> modAnimationContainer = new ModifierLayer<>();

	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void init(ClientWorld world, GameProfile profile, PlayerPublicKey publicKey, CallbackInfo ci) {
		modAnimationContainer.addModifierBefore(new SpeedModifier(0.5F));
		PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayerEntity) (Object)this).addAnimLayer(1000, modAnimationContainer);
	}

	@Override
	public ModifierLayer<IAnimation> getAnimationYetAnotherHugMe() {
		return modAnimationContainer;
	}
}