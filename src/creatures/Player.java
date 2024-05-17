package creatures;

import utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.Player.*;
import static utils.Loader.getAssets;
import static utils.Utils.*;

public class Player extends Creature{
    private Image[][] animations;
    // 1. bite 2. dead 3. falling 4. jump 5. on hit 6. pounce 7. pounced attack 8. pounce end 9. pounce latched 10. ready pounce 11. roar. 12. run 13. scanning 14. walk
    private int aniTick, aniIndex;
    private int playerAction = IDLE;
    private boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false, inAir = false;
    private int[][] lvlData;
    private float airSpeed = 0f;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public void update() {
        updatePos();
        animationTick();
        setAnimation();
    }
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - HITBOX_X_OFFSET),(int)(hitbox.y - HITBOX_Y_OFFSET), width, height, null);
        //drawHitbox(g);
    }

    private void animationTick() {
        aniTick++;
        if (aniTick > PLAYER_ANIMATION_SPEED) {
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

        if(inAir) {
            if(airSpeed > 0) {
                playerAction = FALLING;
            } else {
                playerAction = JUMP;
            }
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
        if (jump) {
            if (!inAir) {
                airSpeed = JUMP_SPEED;
                inAir = true;
            }
        }
        if (!(left || right || inAir)) {
            return;
        }

        float xSpeed = 0;

        if (left)
            xSpeed -= PLAYER_SPEED;
        if (right)
            xSpeed += PLAYER_SPEED;
        if (!inAir) {
            if(!isOnGround(hitbox, lvlData)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
            } else {
                hitbox.y = getYPosToWall(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = FALL_SPEED_AFTER_COLLISION;
                }
            }
        }

        updateXPos(xSpeed);
        moving = true;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getXPosToWall(hitbox, xSpeed);
        }
    }

    private void loadAnimations() {
        BufferedImage sheet = getAssets(Constants.Assets.PLAYER_ASSETS);
        animations = new Image[14][18];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < 18; j++) {
                animations[i][j] = sheet.getSubimage(j * DEFAULT_SPRITE_WIDTH, i * DEFAULT_SPRITE_HEIGHT, DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);
            }
        }
    }

    public void setLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!isOnGround(hitbox, lvlData)) {
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
