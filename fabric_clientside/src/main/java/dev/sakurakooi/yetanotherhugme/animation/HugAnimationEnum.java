package dev.sakurakooi.yetanotherhugme.animation;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import net.minecraft.util.Identifier;

// https://github.com/TunYuntuwuQWQ/HugMe/blob/main/src/main/java/nya/tuyw/hugme/animation/HugAnimationEnum.java
public enum HugAnimationEnum {
    NORMALHUG {
        @Override
        public Identifier getAnimationPlayer1() {
            return Identifier.of(YetAnotherHugMe.MOD_ID, "hug_normal_sender");
        }

        @Override
        public Identifier getAnimationPlayer2() {
            return Identifier.of(YetAnotherHugMe.MOD_ID, "hug_normal_receiver");
        }

        @Override
        public int getAnimationTicks() {
            return 20 * 6;
        }
    },
    TOUCHHEADHUG {
        @Override
        public Identifier getAnimationPlayer1() {
            return Identifier.of(YetAnotherHugMe.MOD_ID, "hug_touch_sender");
        }

        @Override
        public Identifier getAnimationPlayer2() {
            return Identifier.of(YetAnotherHugMe.MOD_ID, "hug_touch_receiver");
        }

        @Override
        public int getAnimationTicks() {
            return 20 * 6;
        }
    };

    public abstract Identifier getAnimationPlayer1();

    public abstract Identifier getAnimationPlayer2();

    public abstract int getAnimationTicks();
}