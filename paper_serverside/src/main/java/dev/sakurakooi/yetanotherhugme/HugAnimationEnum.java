package dev.sakurakooi.yetanotherhugme;

// https://github.com/TunYuntuwuQWQ/HugMe/blob/main/src/main/java/nya/tuyw/hugme/animation/HugAnimationEnum.java
public enum HugAnimationEnum {
    NORMALHUG {
        @Override
        public int getAnimationTicks() {
            return 20 * 6;
        }
    },
    TOUCHHEADHUG {
        @Override
        public int getAnimationTicks() {
            return 20 * 6;
        }
    };

    public abstract int getAnimationTicks();
}