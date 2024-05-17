package main;

import input.KeyboardInput;
import input.MouseInput;
import static utils.Constants.Config;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private final KeyboardInput keyboardInput = new KeyboardInput(this);
    private final MouseInput mouseInput = new MouseInput(this);
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        this.setFocusable(true);
        this.addKeyListener(keyboardInput);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);

        setPanelSize();
    }


    public void setPanelSize() {
        this.setPreferredSize(new Dimension(Config.WIDTH, Config.HEIGHT));
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
