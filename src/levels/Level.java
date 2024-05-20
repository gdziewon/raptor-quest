package levels;

import utils.Loader;
import static utils.Constants.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level {
    private final int[][] lvlData;
    private BufferedImage levelImage;
    private BufferedImage levelBackground;

    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
        this.levelImage = Loader.getAssets(Assets.LEVEL_ASSETS);
        this.levelBackground = Loader.getAssets(Assets.LEVEL_BG);
        changeImages();
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    // TODO: delete this method
    private void changeImages() {
        this.levelImage = levelImage.getSubimage(0, 0, levelImage.getWidth(), levelImage.getHeight());
        this.levelBackground = levelBackground.getSubimage(0, 0, levelBackground.getWidth(), levelBackground.getHeight());
    }

    public void render(Graphics g, int xOffset) {
        // Clear the screen by drawing the background
        g.drawImage(levelBackground, 0, 0, Config.WIDTH, Config.HEIGHT, null);


        // draw the visible portion of the level image scaled to fit the window
        int startX = (int) (xOffset / Config.SCALE);
        g.drawImage(levelImage,
                0, 0, Config.WIDTH, Config.HEIGHT,
                startX, 0, startX + (int)(Config.WIDTH / Config.SCALE), (int)(Config.HEIGHT / Config.SCALE),
                null);
    }

}
