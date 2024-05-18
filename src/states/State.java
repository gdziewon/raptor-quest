package states;

import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public abstract class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isHovering(MouseEvent e, MenuButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }
}
