package input;

import main.GamePanel;
import states.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener{
    private GamePanel gamePanel;
    public MouseInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU -> gamePanel.getGame().getMenu().mouseCLicked(e);
            case PLAY -> gamePanel.getGame().getPlay().mouseCLicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU -> gamePanel.getGame().getMenu().mousePressed(e);
            case PLAY -> gamePanel.getGame().getPlay().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU -> gamePanel.getGame().getMenu().mouseReleased(e);
            case PLAY -> gamePanel.getGame().getPlay().mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU -> gamePanel.getGame().getMenu().mouseMoved(e);
            case PLAY -> gamePanel.getGame().getPlay().mouseMoved(e);
        }
    }
}
