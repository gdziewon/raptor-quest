package levels;

import main.Game;
import utils.Loader;
import static utils.Constants.Assets.*;

import java.awt.*;

public class LevelHandler {
    private Game game;
    private Level level;

    public LevelHandler(Game game) {
        this.game = game;
        this.level = new Level(Loader.getLevelData(LEVEL_DATA));
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void render(Graphics g) {
        level.render(g);
    }

    public void update() {

    }
}
