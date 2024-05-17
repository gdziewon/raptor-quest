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
        this.levelImage = levelImage.getSubimage(0, 0, levelImage.getWidth() - 16, levelImage.getHeight() - 16);
        this.levelBackground = levelBackground.getSubimage(0, 0, levelBackground.getWidth() - 16, levelBackground.getHeight() - 16);
    }

    public void render(Graphics g) {
        g.drawImage(levelBackground, 0, 0, Config.WIDTH, Config.HEIGHT, null);
        g.drawImage(levelImage, 0, 0, Config.WIDTH, Config.HEIGHT, null);
    }
}
