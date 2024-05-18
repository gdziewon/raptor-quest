package utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Constants {
    public static class UI {
        public static final Font DEFAULT_FONT;
        static {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(Assets.FONT);
            try {
                DEFAULT_FONT = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load font");
            }
        }


        public static class Menu {
            public static final float MENU_B_SCALE = 0.5f;

            // Menu button sizes
            public static final int MENU_B_SPRITE_WIDTH = 642;
            public static final int MENU_B_SPRITE_HEIGHT = 210;
            public static final int MENU_B_DEFAULT_WIDTH = 200;
            public static final int MENU_B_DEFAULT_HEIGHT = 50;
            public static final int MENU_B_WIDTH = (int)(MENU_B_DEFAULT_WIDTH * Config.SCALE * MENU_B_SCALE);
            public static final int MENU_B_HEIGHT = (int) (MENU_B_DEFAULT_HEIGHT * Config.SCALE * MENU_B_SCALE);


            // Menu button font sizes
            public static final float MENU_B_FONT_DEFAULT_SIZE = 60f;
            public static final float MENU_FONT_SIZE = MENU_B_FONT_DEFAULT_SIZE * Config.SCALE * MENU_B_SCALE;

            // Menu placement
            public static final int MENU_DEFAULT_Y_POS = 80;
            public static final int MENU_Y_POS = (int)(MENU_DEFAULT_Y_POS * Config.SCALE);

            public static final int MENU_B_X_OFFSET = MENU_B_WIDTH / 2;

            public static final int MENU_B_X_PLACEMENT = Config.WIDTH / 2;
            public static final int PLAY_BUTTON_Y = (int) (80 * Config.SCALE * MENU_B_SCALE) + MENU_Y_POS;
            public static final int OPTIONS_BUTTON_Y = (int) (170 * Config.SCALE * MENU_B_SCALE) + MENU_Y_POS;
            public static final int QUIT_BUTTON_Y = (int) (260 * Config.SCALE * MENU_B_SCALE) + MENU_Y_POS;
        }

        public static class Pause {
            public static final float PAUSE_B_SCALE = 0.4f;

            public static final int SOUND_B_SPRITE_WIDTH = 322;
            public static final int SOUND_B_SPRITE_HEIGHT = 68;
            public static final int SOUND_B_DEFAULT_WIDTH = 118;
            public static final int SOUND_B_DEFAULT_HEIGHT = 25;
            public static final int SOUND_B_WIDTH = (int)(SOUND_B_DEFAULT_WIDTH * Config.SCALE * PAUSE_B_SCALE);
            public static final int SOUND_B_HEIGHT = (int) (SOUND_B_DEFAULT_HEIGHT * Config.SCALE * PAUSE_B_SCALE);

            public static final int SOUND_B_X = (int) (290 * Config.SCALE) + 30;
            public static final int MUSIC_B_Y = (int) (120 * Config.SCALE);
            public static final int SFX_B_Y = (int) (150 * Config.SCALE);
        }
    }
    public static class Player {
        // Player actions
        public static final int IDLE = 10;
        public static final int RUNNING = 11;
        public static final int ATTACKING = 0;
        public static final int HURT = 4;
        public static final int DEAD = 1;
        public static final int JUMP = 3;
        public static final int FALLING = 2;

        // Player animation lengths
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

        // Player scale
        public static final float PLAYER_DEFAULT_SCALE = 0.6f;
        public static final float PLAYER_SCALE = Config.SCALE * PLAYER_DEFAULT_SCALE;

        // Player sprite size
        public static final int DEFAULT_SPRITE_WIDTH = 128;
        public static final int DEFAULT_SPRITE_HEIGHT = 64;
        public static final int SPRITE_WIDTH = (int)(DEFAULT_SPRITE_WIDTH * PLAYER_SCALE);
        public static final int SPRITE_HEIGHT = (int)(DEFAULT_SPRITE_HEIGHT * PLAYER_SCALE);

        // Player hitbox size
        public static final int HITBOX_WIDTH = (int)(35 * PLAYER_SCALE);
        public static final int HITBOX_HEIGHT = (int) (30 * PLAYER_SCALE) ;
        public static final int HITBOX_X_OFFSET = (int) (45 * PLAYER_SCALE);
        public static final int HITBOX_Y_OFFSET = (int) (34 * PLAYER_SCALE);

        // Player animation speed
        public static final int PLAYER_ANIMATION_SPEED = 30;

        // Player speed
        public static final float PLAYER_SPEED = 1.0f * PLAYER_SCALE;

        // Player gravity and jump speed
        public static final float GRAVITY = 0.05f * PLAYER_SCALE;
        public static final float JUMP_SPEED = -4.0f * PLAYER_SCALE;
        public static final float FALL_SPEED_AFTER_COLLISION = 0.5f * PLAYER_SCALE;

    }

    public static class Assets {
        public static final String PLAYER_ASSETS = "raptor.png";
        public static final String LEVEL_ASSETS = "lvl.png";
        public static final String LEVEL_DATA = "lvl_data.png";
        public static final String LEVEL_BG = "lvl_bg.png";
        public static final String MENU_BUTTON = "menu_button.png";
        public static final String FONT = "font.ttf";
        public static final String MENU_BG0 = "menu_bg0.png";
        public static final String MENU_BG1 = "menu_bg1.png";
        public static final String PAUSE_BG = "pause_bg.png";
        public static final String SOUND_BUTTONS = "sound_buttons.png";

    }

    public static class Config {
        // Game size
        public final static int TILE_DEFAULT_SIZE = 24;
        public final static float SCALE = 2.5f;
        public final static int WIDTH_IN_TILES = 25;
        public final static int HEIGHT_IN_TILES = 16;
        public final static int TILE_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
        public final static int WIDTH = WIDTH_IN_TILES * TILE_SIZE;
        public final static int HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;

        // Game loop speed
        public final static int FPS = 120;
        public final static int UPS = 200;
    }
}
