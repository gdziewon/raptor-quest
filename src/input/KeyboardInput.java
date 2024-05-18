package input;

import main.GamePanel;
import states.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.currentState) {
            case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
            case PLAY -> gamePanel.getGame().getPlay().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.currentState) {
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
            case PLAY -> gamePanel.getGame().getPlay().keyReleased(e);
        }
    }
}
