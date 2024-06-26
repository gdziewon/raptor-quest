package levels;

import utils.Loader;
import static utils.Constants.*;
import static utils.Constants.Environment.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level {
    private final int levelNumber;
    private final int[][] lvlData;
    private BufferedImage levelImage;
    private BufferedImage levelBackground0, levelBackground1, levelBackground2;

    public Level(int[][] lvlData, String levelName, String levelBackground0,
                 String levelBackground1, String levelBackground2,  int levelNumber) {
        this.lvlData = lvlData;
        this.levelImage = Loader.getAssets(levelName);
        this.levelBackground0 = Loader.getAssets(levelBackground0);
        this.levelBackground1 = Loader.getAssets(levelBackground1);
        this.levelBackground2 = Loader.getAssets(levelBackground2);
        this.levelNumber = levelNumber;
        changeImages();
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    // TODO: delete this method
    private void changeImages() {
        this.levelImage = levelImage.getSubimage(0, 0, levelImage.getWidth(), levelImage.getHeight());
        this.levelBackground0 = levelBackground0.getSubimage(0, 0, levelBackground0.getWidth(), levelBackground0.getHeight());
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        renderBackground(g, xOffset);
        // draw the visible portion of the level image scaled to fit the window
        int startX = (int) (xOffset / Config.SCALE);
        int startY = (int) (yOffset / Config.SCALE);
        g.drawImage(levelImage,
                0, 0, Config.WIDTH, Config.HEIGHT,
                startX, startY, startX + (int)(Config.WIDTH / Config.SCALE), startY+ (int)(Config.HEIGHT / Config.SCALE),
                null);
    }

    public void renderBackground(Graphics g, int xOffset) {
        g.drawImage(levelBackground0, 0, 0, Config.WIDTH, Config.HEIGHT, null);

        for (int i = 0; i < 3; i++) {
            g.drawImage(levelBackground1, i * LEVEL_BG1_WIDTH - (int)(xOffset * 0.3), (int)(80 * Config.SCALE), LEVEL_BG1_WIDTH, LEVEL_BG1_HEIGHT, null);
            g.drawImage(levelBackground2, i * LEVEL_BG1_WIDTH - (int)(xOffset * 0.8), (int)(80 * Config.SCALE), LEVEL_BG1_WIDTH, LEVEL_BG1_HEIGHT, null);
        }


    }

}
