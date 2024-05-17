package main;

import creatures.Player;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoop;

    private Player player;


    private final int FPS = 120;
    private final int UPS = 200;

    public Game() {
        init();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        this.start();
    }

    private void init() {
        player = new Player(100, 100);
    }

    public void start() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        int frames = 0;
        int updates = 0;

        double deltaF = 0;
        double deltaU = 0;

        long previous = System.nanoTime();

        long lastCheck = System.currentTimeMillis();


        while (true) {
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
