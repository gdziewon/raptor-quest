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

    public void setLevel(String levelData, String levelAssets) {
        this.level = new Level(Loader.getLevelData(levelData), levelAssets);
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
