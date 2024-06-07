package effects;

import java.awt.*;
import java.awt.image.BufferedImage;
import utils.Constants;

import static utils.Loader.getAssets;

public class Effect {
    private int x, y;
    private int width, height;
    private int currentFrame = 0;
    private int maxFrames;
    private int duration = 0;
    private int frameDelayCounter = 0;
    private final int effectType;
    private BufferedImage[] frames;
    private BufferedImage sheet;
    private int spriteWidth, spriteHeight;

    public Effect(int x, int y, int width, int height, int duration, int effectType) {
        this.x = x;
        this.y = y;
        this.width = (int)(width * Constants.Config.SCALE);
        this.height = (int)(height * Constants.Config.SCALE);
        this.duration = duration;
        this.effectType = effectType;
        init();
        loadAssets();
    }

    private void init() {
        x -= width / 2;
        y -= height / 2;

        switch (effectType) {
            case Constants.Effects.BLOOD_ON_HIT -> {
                sheet = getAssets(Constants.Assets.BLOOD_ON_HIT_ASSETS);
                spriteWidth = 35;
                spriteHeight = 32;
                if (duration == 0 || duration > 5)
                    maxFrames = 5;
                else
                    maxFrames = duration;
            }
        }
    }

    private void loadAssets() {
        frames = new BufferedImage[maxFrames];
        for (int i = 0; i < maxFrames; i++) {
            frames[i] = sheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
        }
    }

    public void update() {
        frameDelayCounter++;
        if (frameDelayCounter >= Constants.Config.ANIMATION_SPEED) {
            currentFrame++;
            frameDelayCounter = 0;
        }
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        if (currentFrame < maxFrames)
            g.drawImage(frames[currentFrame], x - xOffset, y - yOffset, width, height, null);
    }

    public boolean isDone() {
        return currentFrame >= maxFrames;
    }
}
