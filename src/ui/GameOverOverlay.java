package ui;

import creatures.Goblin;
import main.Game;
import states.GameState;
import states.Play;
import utils.Constants;
import utils.Loader;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {
    private Play play;
    public GameOverOverlay(Play play) {
        this.play = play;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, Constants.Config.WIDTH, Constants.Config.HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(Loader.getFont(Constants.Assets.FONT, 50));
        g.drawString("Game Over", Constants.Config.WIDTH / 2 - 100, Constants.Config.HEIGHT / 2 - 50);
        g.setFont(Loader.getFont(Constants.Assets.FONT, 20));
        g.drawString("Press Enter to go to Menu...", Constants.Config.WIDTH / 2 - 100, Constants.Config.HEIGHT / 2);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            play.reset();
            GameState.currentState = GameState.MENU;
        }
    }
}
