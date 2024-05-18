package ui;

import utils.Constants;
import utils.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.Pause.*;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundButtons;
    private boolean hovered = false, clicked = false;
    private boolean muted = false;
    private int rowIdx = 0, colIdx = 0;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        init();
    }

    private void init() {
        BufferedImage sheet = Loader.getAssets(Constants.Assets.SOUND_BUTTONS);
        soundButtons = new BufferedImage[2][3];
        for (int i = 0; i < soundButtons.length; i++) {
            for (int j = 0; j < soundButtons[0].length; j++) {
                soundButtons[i][j] = sheet.getSubimage(j * SOUND_B_SPRITE_WIDTH, i * SOUND_B_SPRITE_HEIGHT, SOUND_B_SPRITE_WIDTH, SOUND_B_SPRITE_HEIGHT);
            }
        }
    }
    public void update() {
        if (muted)
            rowIdx = 1;
        else
            rowIdx = 0;

        colIdx = 0;
        if (hovered)
            colIdx = 1;
        if (clicked)
            colIdx = 2;
    }

    public void render(Graphics g) {
        g.drawImage(soundButtons[rowIdx][colIdx], x, y, width, height, null);
    }

    public void reset() {
        hovered = false;
        clicked = false;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
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
