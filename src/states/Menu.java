package states;

import main.Game;
import ui.MenuButton;
import utils.Loader;

import static utils.Constants.Config;
import static utils.Constants.UI.Menu.*;
import static utils.Constants.Assets.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage[] background = new BufferedImage[2];
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        init();
    }

    public void init() {
        loadBackground();
        loadButtons();
    }

    public void loadBackground() {
        background[0] = Loader.getAssets(MENU_BG0);
        background[1] = Loader.getAssets(MENU_BG1);
        menuWidth = (int) (background[1].getWidth() * Config.SCALE) * 3;
        menuHeight = (int) (background[1].getHeight() * Config.SCALE) * 3;
        menuX = (Config.WIDTH - menuWidth) / 2;
        menuY = (int) ( MENU_Y_POS);
    }

    public void loadButtons() {
        buttons[0] = new MenuButton(MENU_B_X_PLACEMENT, PLAY_BUTTON_Y, "Play", GameState.PLAY);
        buttons[1] = new MenuButton(MENU_B_X_PLACEMENT, OPTIONS_BUTTON_Y, "Options", GameState.OPTIONS);
        buttons[2] = new MenuButton(MENU_B_X_PLACEMENT, QUIT_BUTTON_Y, "Quit", GameState.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton button : buttons)
            button.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background[0], 0, 0, Config.WIDTH, Config.HEIGHT, null);
        g.drawImage(background[1], menuX, menuY, menuWidth, menuHeight, null);
        for (MenuButton button : buttons)
            button.render(g);
    }

    @Override
    public void mouseCLicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : buttons)
            if(isHovering(e, button)) {
                button.setClicked(true);
                break;
            }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : buttons)
            if(isHovering(e, button) && button.isClicked()) {
                button.applyGameState();
                break;
            }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton button : buttons)
            button.reset();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : buttons)
            button.setHovered(false);

        for (MenuButton button : buttons)
            if(isHovering(e, button)) {
                button.setHovered(true);
                break;
            }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameState.currentState = GameState.PLAY;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
