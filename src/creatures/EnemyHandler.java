package creatures;

import states.Play;
import utils.Constants;
import utils.Loader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyHandler {
    private Play play;
    private ArrayList<Enemy> enemies;


    public EnemyHandler(Play play) {
        this.play = play;
        enemies = Loader.getEnemies(Constants.Assets.LEVEL_DATA);
    }

    public void update(int[][] lvlData) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive())
                enemy.update(lvlData, play.getPlayer());
        }
    }

    public void render(Graphics g, int xOffset, int yOffset) {
         for (Enemy enemy : enemies) {
             if (enemy.isAlive())
                enemy.render(g, xOffset, yOffset);
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && attackBox.intersects(enemy.getHitbox())) {
                enemy.hurt(10);
                play.getPlayer().heal(5);
                return;
            }
        }

    }

    public void addEnemy(float x, float y) {
        Goblin goblin = new Goblin(x, y);
        enemies.add(goblin);
    }
}
