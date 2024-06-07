package creatures;

import states.Play;
import utils.Constants;
import utils.Loader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static utils.Constants.Enemies.LADY;

public class EnemyHandler {
    private Play play;
    private ArrayList<Enemy> enemies;


    public EnemyHandler(Play play) {
        this.play = play;
        enemies = Loader.getEnemies(Constants.Assets.LEVEL1_DATA);
    }

    public void update(int[][] lvlData) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive())
                enemy.update(lvlData, play.getPlayer());
        }
    }

    public void render(Graphics g, int xOffset, int yOffset) {
         for (Enemy enemy : enemies) {
             if (enemy.isAlive() && enemy.isInCamera(xOffset, yOffset))
                enemy.render(g, xOffset, yOffset);
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Enemy enemy : enemies) {
            Rectangle2D intersection = attackBox.createIntersection(enemy.getHitbox());
            if (enemy.isAlive() && attackBox.intersects(enemy.getHitbox())) {
                enemy.hurt(10);
                play.getPlayer().heal(5);
                addEnemyBiteEffect(attackBox, intersection, enemy.getEnemyType());
            }
        }
    }

    public void addEnemyBiteEffect(Rectangle2D.Float attackBox, Rectangle2D intersection, int enemyType) {
        int effectY = (int) intersection.getY() + (int) (attackBox.height / 2);
        int duration = enemyType == LADY ? 5 : 3;
        play.getEffectHandler().createEffect((int) intersection.getX(), effectY, 20, 20, duration, Constants.Effects.BLOOD_ON_HIT);
    }

    public void addEnemy(float x, float y) {
        Goblin goblin = new Goblin(x, y);
        enemies.add(goblin);
    }
}
