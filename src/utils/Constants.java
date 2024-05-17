package utils;

public class Constants {
    public static class Player {
        public static final int IDLE = 10;
        public static final int RUNNING = 11;
        public static final int ATTACKING = 0;
        public static final int HURT = 4;
        public static final int DEAD = 1;

        public static int getAnimationLength(int action) {
            return switch (action) {
                case IDLE -> 3;
                case RUNNING -> 6;
                case ATTACKING -> 10;
                case HURT -> 1;
                case DEAD -> 6;
                default -> 1;
            };
        }

        public static final int SPRITE_WIDTH = 128;
        public static final int SPRITE_HEIGHT = 64;
    }
}
