package main;

import levels.LevelHandler;
import creatures.Player;
import states.*;
import utils.Constants;

import java.awt.Graphics;

public class Game implements Runnable {
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;

    private Player player;
    private LevelHandler levelHandler;

    private Menu menu;
    private Play play;

    public Game() {
        init();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        this.start();
    }

    private void init() {
        GameState.currentState = GameState.MENU;
        menu = new Menu(this);
        play = new Play(this);
    }

    public void start() {
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void update() {
        switch (GameState.currentState) {
            case MENU -> menu.update();
            case PLAY -> play.update();
            case OPTIONS -> System.out.println("Options");
            case QUIT -> System.exit(0);
        }
    }

    public void render(Graphics g) {
        switch (GameState.currentState) {
            case MENU -> menu.render(g);
            case PLAY -> play.render(g);
        }
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
        if (GameState.currentState == GameState.PLAY) {
            play.getPlayer().resetMovement();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Play getPlay() {
        return play;
    }
}
