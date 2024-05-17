package main;

import levels.LevelHandler;
import creatures.Player;
import utils.Constants;

import java.awt.*;

public class Game implements Runnable {
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;

    private Player player;
    private LevelHandler levelHandler;

    public Game() {
        init();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        this.start();
    }

    private void init() {
        levelHandler = new LevelHandler(this);
        player = new Player(10, 500, Constants.Player.SPRITE_WIDTH, Constants.Player.SPRITE_HEIGHT);
        player.setLvlData(levelHandler.getLevel().getLvlData());
    }

    public void start() {
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void update() {
        player.update();
        levelHandler.update();
    }

    public void render(Graphics g) {
        levelHandler.render(g);
        player.render(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / Constants.Config.FPS;
        double timePerUpdate = 1000000000.0 / Constants.Config.UPS;

        int frames = 0;
        int updates = 0;

        double deltaF = 0;
        double deltaU = 0;

        long previous = System.nanoTime();

        long lastCheck = System.currentTimeMillis();


        while (gameWindow.isVisible()) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previous) / timePerUpdate;
            deltaF += (currentTime - previous) / timePerFrame;
            previous = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                System.out.println("FPS: " + frames + " UPS: " + updates);
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }

    public void focusLost() {
        player.resetMovement();
    }

    public Player getPlayer() {
        return player;
    }
}
