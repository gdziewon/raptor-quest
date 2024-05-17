package creatures;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static utils.Constants.Player.*;

public class Player extends Creature{
    private Image[][] animations;
    // 1. bite 2. dead 3. falling 4. jump 5. on hit 6. pounce 7. pounced attack 8. pounce end 9. pounce latched 10. ready pounce 11. roar. 12. run 13. scanning 14. walk
    private int aniTick, aniIndex, aniSpeed = 40;
    private int playerAction = IDLE;
    private boolean left, right, up, down;
    private float speed = 3.0f;
    private boolean moving = false, attacking = false;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePos();
        animationTick();
        setAnimation();
    }
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)x,(int)y, null);
    }

    private void animationTick() {
        aniTick++;
        if (aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getAnimationLength(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {

        int lastAction = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (attacking) {
            playerAction = ATTACKING;
        }

        if (lastAction != playerAction) {
            aniIndex = 0;
            aniTick = 0;
        }
    }

    private void updatePos() {
        moving = false;

        if (left && ! right) {
            x -= speed;
            moving = true;
        } else if (right && ! left) {
            x += speed;
            moving = true;
        }

        if (up && ! down) {
            y -= speed;
            moving = true;
        } else if (down && ! up) {
            y += speed;
            moving = true;
        }
    }

    private void loadAnimations() {
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("raptor.png");
            BufferedImage sheet = ImageIO.read(Objects.requireNonNull(inputStream));
            animations = new Image[14][18];
            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < 18; j++) {
                    animations[i][j] = sheet.getSubimage(j * SPRITE_WIDTH, i * SPRITE_HEIGHT,SPRITE_WIDTH, SPRITE_HEIGHT).getScaledInstance(200, 100, Image.SCALE_DEFAULT);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetMovement() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
