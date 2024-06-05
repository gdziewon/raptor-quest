package utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Constants {
    public static class Enemies {
        public static final int GOBLIN = 1;
        public static final int MUSHROOM = 2;
        public static final int IDLE = 2;
        public static final int RUNNING = 3;
        public static final int ATTACKING = 0;
        public static final int HURT = 4;
        public static final int DEAD = 1;


        // Player scale
        public static final float GOBLIN_DEFAULT_SCALE = 0.5f;
        public static final float GOBLIN_SCALE = Config.SCALE * GOBLIN_DEFAULT_SCALE;

        // Player sprite size
        public static final int GOBLIN_SPRITE_SIZE = 150;
        public static final int GOBLIN_SIZE = (int)(GOBLIN_SPRITE_SIZE * GOBLIN_SCALE);

        // Player hitbox size
        public static final int GOBLIN_HITBOX_WIDTH = (int)(23 * GOBLIN_SCALE);
        public static final int GOBLIN_HITBOX_HEIGHT = (int) (31 * GOBLIN_SCALE) ;
        public static final int GOBLIN_HITBOX_X_OFFSET = (int) (64 * GOBLIN_SCALE);
        public static final int GOBLIN_HITBOX_Y_OFFSET = (int) (70 * GOBLIN_SCALE);

        // Player speed
        public static final float GOBLIN_SPEED = 0.8f * GOBLIN_SCALE;

        // Player scale
        public static final float MUSHROOM_DEFAULT_SCALE = 0.62f;
        public static final float MUSHROOM_SCALE = Config.SCALE * MUSHROOM_DEFAULT_SCALE;

        // Player sprite size
        public static final int MUSHROOM_SPRITE_SIZE = 150;
        public static final int MUSHROOM_SIZE = (int)(MUSHROOM_SPRITE_SIZE * MUSHROOM_SCALE);

        // Player hitbox size
        public static final int MUSHROOM_HITBOX_WIDTH = (int)(22 * MUSHROOM_SCALE);
        public static final int MUSHROOM_HITBOX_HEIGHT = (int) (37 * MUSHROOM_SCALE);
        public static final int MUSHROOM_HITBOX_X_OFFSET = (int) (64 * MUSHROOM_SCALE);
        public static final int MUSHROOM_HITBOX_Y_OFFSET = (int) (64 * MUSHROOM_SCALE);

        // Player speed
        public static final float MUSHROOM_SPEED = 0.45f * MUSHROOM_SCALE;

        // goblin animation lengths
        public static int getAnimationLength(int action, int enemyType) {
            switch (enemyType) {
                case GOBLIN, MUSHROOM -> {
                    return switch (action) {
                        case IDLE -> 4;
                        case RUNNING -> 8;
                        case ATTACKING -> 8;
                        case HURT -> 4;
                        case DEAD -> 4;
                        default -> 1;
                    };
                }
            }
            return 1;
        }

        public static int getMaxHealth(int enemyType) {
            return switch (enemyType) {
                case GOBLIN -> 20;
                case MUSHROOM -> 35;
                default -> 1;
            };
        }

        public static int getDamage(int enemyType) {
            return switch (enemyType) {
                case GOBLIN -> 15;
                case MUSHROOM -> 20;
                default -> 0;
            };
        }
    }


    public static class Environment {
        public static final int LEVEL_BG1_WIDTH_DEFAULT = 512;
        public static final int LEVEL_BG1_HEIGHT_DEFAULT = 256;

        public static final int LEVEL_BG1_WIDTH = (int)(LEVEL_BG1_WIDTH_DEFAULT * Config.SCALE);
        public static final int LEVEL_BG1_HEIGHT = (int)(LEVEL_BG1_HEIGHT_DEFAULT * Config.SCALE);
    }

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
        public static final int SNEAKING = 9;

        // Player animation lengths
        public static int getAnimationLength(int action) {
            return switch (action) {
                case IDLE -> 3;
                case RUNNING -> 6;
                case ATTACKING -> 4;
                case HURT -> 3;
                case DEAD -> 6;
                case JUMP -> 1;
                case FALLING -> 1;
                case SNEAKING -> 2;
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
        public static final int PLAYER_HITBOX_WIDTH = (int)(20 * PLAYER_SCALE);
        public static final int PLAYER_HITBOX_HEIGHT = (int) (30 * PLAYER_SCALE) ;
        public static final int PLAYER_HITBOX_X_OFFSET = (int) (55 * PLAYER_SCALE);
        public static final int PLAYER_HITBOX_Y_OFFSET = (int) (34 * PLAYER_SCALE);

        // Player speed
        public static final float PLAYER_SPEED = 1.0f * PLAYER_SCALE;

        public static final float JUMP_SPEED = -4.0f * PLAYER_SCALE;
        public static final float FALL_SPEED_AFTER_COLLISION = 0.5f * PLAYER_SCALE;


        public static final float STATUS_BAR_DEFAULT_SCALE = 1.0f;
        public static final float STATUS_BAR_SCALE = Config.SCALE * STATUS_BAR_DEFAULT_SCALE;

        public static final int STATUS_BAR_WIDTH = (int) (111 * STATUS_BAR_SCALE);
        public static final int STATUS_BAR_HEIGHT = (int) (15 * STATUS_BAR_SCALE);
        public static final int STATUS_BAR_X = (int) (10 * STATUS_BAR_SCALE);
        public static final int STATUS_BAR_Y = (int) (30 * STATUS_BAR_SCALE);
        public static final int BAR_WIDTH = (int) (109 * STATUS_BAR_SCALE);
        public static final int BAR_HEIGHT = (int) (6 * STATUS_BAR_SCALE);
        public static final int HEALTH_BAR_X = (int) (11 * STATUS_BAR_SCALE);
        public static final int HEALTH_BAR_Y = (int) (31 * STATUS_BAR_SCALE);
        public static final int MANA_BAR_X = (int) (11 * STATUS_BAR_SCALE);
        public static final int MANA_BAR_Y = (int) (38 * STATUS_BAR_SCALE);
    }

    public static class Assets {
        public static final String PLAYER_ASSETS = "raptor.png";
        public static final String STATUS_BARS = "player_bars.png";
        //public static final String LEVEL_ASSETS = "lvl.png";
        //public static final String LEVEL_DATA = "lvl_data.png";
        //public static final String LEVEL_BG = "lvl_bg.png";
        public static final String LEVEL_ASSETS = "longer.png";
        public static final String LEVEL_DATA = "longer_data.png";
        public static final String LEVEL_BG0 = "longer_bg0.png";
        public static final String LEVEL_BG1 = "longer_bg1.png";
        public static final String LEVEL_BG2 = "longer_bg2.png";
        public static final String MENU_BUTTON = "menu_button.png";
        public static final String FONT = "font.ttf";
        public static final String MENU_BG0 = "menu_bg0.png";
        public static final String MENU_BG1 = "menu_bg1.png";
        public static final String PAUSE_BG = "pause_bg.png";
        public static final String SOUND_BUTTONS = "sound_buttons.png";

        public static final String GOBLIN_ASSETS = "goblin.png";
        public static final String MUSHROOM_ASSETS = "mushroom.png";

    }

    public static class Config {
        // Game size
        public final static int TILE_DEFAULT_SIZE = 24;
        public final static float SCALE = 2.5f;
        public final static int WIDTH_IN_TILES = 25;
        public final static int HEIGHT_IN_TILES = 14;
        public final static int TILE_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
        public final static int WIDTH = WIDTH_IN_TILES * TILE_SIZE;
        public final static int HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;

        // Game loop speed
        public final static int FPS = 120;
        public final static int UPS = 200;


        public final static int LEFT_BORDER = (int) (0.3 * Constants.Config.WIDTH);
        public final static int RIGHT_BORDER = (int) (0.7 * Constants.Config.WIDTH);
        public final static int TOP_BORDER = (int) (0.6 * Constants.Config.HEIGHT);
        public final static int BOTTOM_BORDER = (int) (0.4 * Constants.Config.HEIGHT);

        public static final int ANIMATION_SPEED = 25;
        public static final float GRAVITY = 0.05f * Player.PLAYER_SCALE;
    }
}
