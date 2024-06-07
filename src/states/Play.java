package states;

import creatures.EnemyHandler;
import creatures.Player;
import effects.EffectHandler;
import levels.LevelHandler;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;

import static utils.Constants.Assets.LEVEL1_ASSETS;
import static utils.Constants.Assets.LEVEL1_DATA;
import static utils.Constants.Config.*;
import utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Play extends State implements StateMethods {
    private Player player;
    private LevelHandler levelHandler;
    private EnemyHandler enemyHandler;
    private EffectHandler effectHandler;
    private boolean paused = false;
    private boolean gameOver = false;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;

    private int lvlXOffset = 0;
    private int tilesInLvl;
    private int maxLvlXOffset;

    private int lvlYOffset = 0;
    private int maxLvlYOffset;


    public Play(Game game) {
        super(game);
        init();
    }

    public void init() {
        levelHandler = new LevelHandler(game);
        levelHandler.newLevel(1);
        enemyHandler = new EnemyHandler(this);
        effectHandler = new EffectHandler();
        player = new Player(1 * TILE_SIZE, 26 * TILE_SIZE, Constants.Player.SPRITE_WIDTH, Constants.Player.SPRITE_HEIGHT, this);
        player.setLvlData(levelHandler.getLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);
        tilesInLvl = levelHandler.getLevel().getLvlData().length;
        maxLvlXOffset = tilesInLvl * Constants.Config.TILE_SIZE - TILE_SIZE / 2;

        maxLvlYOffset = levelHandler.getLevel().getLvlData()[0].length * TILE_SIZE - TILE_SIZE / 2;
        gameOverOverlay = new GameOverOverlay(this);
    }

    @Override
    public void update() {
        if (paused)
            pauseOverlay.update();
        else if (!gameOver) {
            player.update();
            enemyHandler.update(levelHandler.getLevel().getLvlData());
            effectHandler.update();
            levelHandler.update();
            checkCloseToBorders();

            if (player.getHitbox().x >= maxLvlXOffset - 5)
                gameOver = true;
        }
    }

    private void checkCloseToBorders() {
        int playerX = (int) player.getHitbox().x;
        int playerY = (int) player.getHitbox().y;


        int diff = playerX - lvlXOffset;
        if (diff < LEFT_BORDER) {
            lvlXOffset -= (LEFT_BORDER - diff);
        } else if (diff > RIGHT_BORDER) {
            lvlXOffset += (diff - RIGHT_BORDER);
        }

        int diffY = playerY - lvlYOffset;
        if (diffY > TOP_BORDER) {
            lvlYOffset += (diffY - TOP_BORDER);
        } else if (diffY < BOTTOM_BORDER) {
            lvlYOffset -= (BOTTOM_BORDER - diffY);
        }

        if (lvlXOffset < 0) {
            lvlXOffset = 0;
        } else if (lvlXOffset > maxLvlXOffset - Constants.Config.WIDTH) {
            lvlXOffset = maxLvlXOffset - Constants.Config.WIDTH;
        }

        if (lvlYOffset < 0) {
            lvlYOffset = 0;
        } else if (lvlYOffset > maxLvlYOffset - Constants.Config.HEIGHT) {
            lvlYOffset = maxLvlYOffset - Constants.Config.HEIGHT;
        }
    }

    public void reset() {
        init();
        paused = false;
        gameOver = false;
        lvlXOffset = 0;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyHandler.checkEnemyHit(attackBox);
    }


    @Override
    public void render(Graphics g) {
        levelHandler.render(g, lvlXOffset, lvlYOffset);
        player.render(g, lvlXOffset, lvlYOffset);
        enemyHandler.render(g, lvlXOffset, lvlYOffset);
        effectHandler.render(g, lvlXOffset, lvlYOffset);
        player.drawUI(g);

        if (paused)
            pauseOverlay.render(g);
        else if (gameOver)
            gameOverOverlay.render(g);
    }

    @Override
    public void mouseCLicked(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1 && !gameOver)
//            player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused && !gameOver) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused && !gameOver) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused && !gameOver) {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_H -> player.setAttacking(true);
                case KeyEvent.VK_SPACE -> player.setJump(true);
                case KeyEvent.VK_SHIFT -> player.setSneaking(true);
                case KeyEvent.VK_ESCAPE -> paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_D -> player.setRight(false);
                case KeyEvent.VK_SHIFT -> player.setSneaking(false);
                case KeyEvent.VK_SPACE -> player.setJump(false);
            }
    }

    public Player getPlayer() {
        return player;
    }

    public void unpause() {
        this.paused = false;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public EffectHandler getEffectHandler() {
        return effectHandler;
    }
}
