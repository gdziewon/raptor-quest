package utils;

public class Constants {
    public static class Player {
        public static final int IDLE = 10;
        public static final int RUNNING = 11;
        public static final int ATTACKING = 0;
        public static final int HURT = 4;
        public static final int DEAD = 1;
        public static final int JUMP = 3;
        public static final int FALLING = 2;

        public static int getAnimationLength(int action) {
            return switch (action) {
                case IDLE -> 3;
                case RUNNING -> 6;
                case ATTACKING -> 5;
                case HURT -> 1;
                case DEAD -> 6;
                case JUMP -> 1;
                case FALLING -> 1;
                default -> 1;
            };
        }


        public static final float PLAYER_DEFAULT_SCALE = 0.6f;
        public static final float PLAYER_SCALE = Config.SCALE * PLAYER_DEFAULT_SCALE;

        public static final int DEFAULT_SPRITE_WIDTH = 128;
        public static final int DEFAULT_SPRITE_HEIGHT = 64;
        public static final int SPRITE_WIDTH = (int)(DEFAULT_SPRITE_WIDTH * PLAYER_SCALE);
        public static final int SPRITE_HEIGHT = (int)(DEFAULT_SPRITE_HEIGHT * PLAYER_SCALE);

        public static final int HITBOX_WIDTH = (int)(35 * PLAYER_SCALE);
        public static final int HITBOX_HEIGHT = (int) (30 * PLAYER_SCALE) ;
        public static final int HITBOX_X_OFFSET = (int) (45 * PLAYER_SCALE);
        public static final int HITBOX_Y_OFFSET = (int) (34 * PLAYER_SCALE);

        public static final int PLAYER_ANIMATION_SPEED = 30;

        public static final float PLAYER_SPEED = 1.0f * PLAYER_SCALE;

        public static final float GRAVITY = 0.05f * PLAYER_SCALE;
        public static final float JUMP_SPEED = -4.0f * PLAYER_SCALE;
        public static final float FALL_SPEED_AFTER_COLLISION = 0.5f * PLAYER_SCALE;

    }

    public static class Assets {
        public static final String PLAYER_ASSETS = "raptor.png";
        public static final String LEVEL_ASSETS = "lvl.png";
        public static final String LEVEL_DATA = "lvl_data.png";
        public static final String LEVEL_BG = "lvl_bg.png";
    }

    public static class LevelHandler {
    }

    public static class Config {
        public final static int TILE_DEFAULT_SIZE = 24;
        public final static float SCALE = 2.5f;
        public final static int WIDTH_IN_TILES = 25;
        public final static int HEIGHT_IN_TILES = 16;
        public final static int TILE_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
        public final static int WIDTH = WIDTH_IN_TILES * TILE_SIZE;
        public final static int HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;

        public final static int FPS = 120;
        public final static int UPS = 200;
    }
}
