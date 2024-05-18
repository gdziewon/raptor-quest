package ui;

import states.GameState;
import utils.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import utils.Constants.Assets;
import static utils.Constants.UI.Menu.*;
import static utils.Constants.UI.DEFAULT_FONT;

public class MenuButton {
    private int xPos, yPos;
    private String text;
    private BufferedImage[] buttonImages;
    private Font buttonFont;
    private boolean hovered = false, clicked = false;
    private int currentImageIdx = 0;
    private Rectangle bounds;
    private GameState state;

    public MenuButton(int xPos, int yPos,  String text, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.state = state;
        bounds = new Rectangle(xPos - MENU_B_X_OFFSET, yPos, MENU_B_WIDTH, MENU_B_HEIGHT);
        buttonFont = DEFAULT_FONT.deriveFont(MENU_FONT_SIZE);

        loadAssets();
    }

    private void loadAssets() {
        BufferedImage sheet = Loader.getAssets(Assets.MENU_BUTTON);
        buttonImages = new BufferedImage[3];
        for (int i = 0; i < buttonImages.length; i++) {
            buttonImages[i] = sheet.getSubimage(i * MENU_B_SPRITE_WIDTH, 0, MENU_B_SPRITE_WIDTH, MENU_B_SPRITE_HEIGHT);
        }
    }

    public void render(Graphics g) {
        g.drawImage(buttonImages[currentImageIdx], xPos - MENU_B_X_OFFSET, yPos, MENU_B_WIDTH, MENU_B_HEIGHT + 40, null);
        renderText(g);
    }

    public void renderText(Graphics g) {
        g.setFont(buttonFont);
        g.setColor(Color.BLACK);
        FontMetrics metrics = g.getFontMetrics(buttonFont);
        int textX = xPos - MENU_B_X_OFFSET + (MENU_B_WIDTH - metrics.stringWidth(text)) / 2;
        int textY = yPos + ((MENU_B_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, textX, textY);
    }

    public void update() {
        currentImageIdx = 0;
        if (hovered) {
            currentImageIdx = 1;
            if (clicked) {
                currentImageIdx = 2;
            }
        }
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGameState() {
        GameState.currentState = state;
    }

    public void reset() {
        hovered = false;
        clicked = false;
    }
}
