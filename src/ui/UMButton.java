package ui;

import utils.Constants;
import utils.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.Menu.*;

public class UMButton extends PauseButton{
    private BufferedImage[] UMButtons;
    private boolean hovered = false, clicked = false;
    private int currentImageIdx = 0;

    public UMButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        init();
    }

    // TODO: get some assets for the buttons
    private void init() {
        BufferedImage sheet = Loader.getAssets(Constants.Assets.MENU_BUTTON);
        UMButtons = new BufferedImage[3];
        for (int i = 0; i < UMButtons.length; i++) {
            UMButtons[i] = sheet.getSubimage(i * MENU_B_SPRITE_WIDTH, 0, MENU_B_SPRITE_WIDTH, MENU_B_SPRITE_HEIGHT);
        }
    }


    public void update() {
        currentImageIdx = 0;
        if (hovered)
            currentImageIdx = 1;
        if (clicked)
            currentImageIdx = 2;
    }

    public void render(Graphics g) {
        g.drawImage(UMButtons[currentImageIdx], x, y, width, height, null);
    }

    public void reset() {
        hovered = false;
        clicked = false;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}
