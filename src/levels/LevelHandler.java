package levels;

import main.Game;
import utils.Constants;
import utils.Loader;
import static utils.Constants.Assets.*;

import java.awt.*;

public class LevelHandler {
    private Game game;
    private Level level;

    public LevelHandler(Game game) {
        this.game = game;
    }

    public void setLevel(String levelData, String levelAssets, String levelBackground0,
                         String levelBackground1, String levelBackground2, int levelNumber) {
        this.level = new Level(Loader.getLevelData(levelData), levelAssets, levelBackground0, levelBackground1, levelBackground2, levelNumber);
    }

    public void newLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                setLevel(LEVEL1_DATA, LEVEL1_ASSETS, LEVEL1_BG0, LEVEL1_BG1, LEVEL1_BG2, levelNumber);
                break;
            default:
                setLevel(LEVEL1_DATA, LEVEL1_ASSETS, LEVEL1_BG0, LEVEL1_BG1, LEVEL1_BG2, levelNumber);
                break;
        }
    }

    public Level getLevel() {
        return level;
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        level.render(g, xOffset, yOffset);
    }

    public void update() {

    }
}
