package dev.sakurakooi.yetanotherhugme.animation;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.sakurakooi.yetanotherhugme.IHugMeAnimatedPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AnimationManager {
    public static void playHugAnimation(@NotNull PlayerEntity player1, @NotNull PlayerEntity player2, HugAnimationEnum animation) {
        animatePlayerFor(player1, animation.getAnimationPlayer1());
        animatePlayerFor(player2, animation.getAnimationPlayer2());
    }

    public static void endHugAnimation(@Nullable PlayerEntity player1, @Nullable PlayerEntity player2) {
        if (player1 != null)
            endAnimatePlayerFor(player1);
        if (player2 != null)
            endAnimatePlayerFor(player2);
    }

    private static void animatePlayerFor(@NotNull PlayerEntity player, Identifier animationPlayer) {
        ModifierLayer<IAnimation> animation = ((IHugMeAnimatedPlayer) player).getAnimationYetAnotherHugMe();
        KeyframeAnimation keyframeAnimation = PlayerAnimationRegistry.getAnimation(animationPlayer);
        KeyframeAnimationPlayer animationPlayer1 = new KeyframeAnimationPlayer(Objects.requireNonNull(keyframeAnimation))
                .setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL)
                .setFirstPersonConfiguration(new FirstPersonConfiguration().setShowLeftArm(true).setShowLeftItem(false).setShowRightArm(true).setShowRightItem(false));
        animation.setAnimation(animationPlayer1);
    }

    private static void endAnimatePlayerFor(@NotNull PlayerEntity player) {
        ModifierLayer<IAnimation> animation = ((IHugMeAnimatedPlayer) player).getAnimationYetAnotherHugMe();
        animation.setAnimation(null);
    }
}
