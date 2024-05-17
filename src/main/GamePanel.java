package main;

import input.KeyboardInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private KeyboardInput keyboardInput = new KeyboardInput(this);
    private MouseInput mouseInput = new MouseInput(this);
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        this.setFocusable(true);
        this.addKeyListener(keyboardInput);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);

        setPanelSize();
    }


    public void setPanelSize() {
        this.setPreferredSize(new Dimension(800, 600));
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
