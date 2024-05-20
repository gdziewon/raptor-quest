package states;

import creatures.Player;
import levels.LevelHandler;
import main.Game;
import ui.PauseOverlay;
import static utils.Constants.Config.*;
import utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Play extends State implements StateMethods {
    private Player player;
    private LevelHandler levelHandler;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;

    private int lvlXOffset = 0;
    private int tilesInLvl;
    private int maxLvlXOffset;

    public Play(Game game) {
        super(game);
        init();
    }

    public void init() {
        levelHandler = new LevelHandler(game);
        player = new Player(60, 180 * SCALE, Constants.Player.SPRITE_WIDTH, Constants.Player.SPRITE_HEIGHT);
        player.setLvlData(levelHandler.getLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);
        tilesInLvl = levelHandler.getLevel().getLvlData().length;
        maxLvlXOffset = tilesInLvl * Constants.Config.TILE_SIZE;
    }

    @Override
    public void update() {
        if (paused)
            pauseOverlay.update();
        else {
            player.update();
            levelHandler.update();
            checkCloseToBorders();
        }
    }

    private void checkCloseToBorders() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - lvlXOffset;
        if (diff < LEFT_BORDER) {
            lvlXOffset -= (LEFT_BORDER - diff);
        } else if (diff > RIGHT_BORDER) {
            lvlXOffset += (diff - RIGHT_BORDER);
        }

        if (lvlXOffset < 0) {
            lvlXOffset = 0;
        } else if (lvlXOffset > maxLvlXOffset - Constants.Config.WIDTH) {
            lvlXOffset = maxLvlXOffset - Constants.Config.WIDTH;
        }
    }


    @Override
    public void render(Graphics g) {
        levelHandler.render(g, lvlXOffset);
        player.render(g, lvlXOffset);

        if (paused)
            pauseOverlay.render(g);
    }

    @Override
    public void mouseCLicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_SPACE -> player.setJump(true);
            case KeyEvent.VK_ESCAPE -> paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_SPACE -> player.setJump(false);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void unpause() {
        this.paused = false;
    }
}
