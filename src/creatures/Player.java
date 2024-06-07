package creatures;

import states.Play;
import utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Config.ANIMATION_SPEED;
import static utils.Constants.Config.GRAVITY;
import static utils.Constants.Player.*;
import static utils.Loader.getAssets;
import static utils.Utils.*;

public class Player extends Creature{
    private Image[][] animations;
    // 1. bite 2. dead 3. falling 4. jump 5. on hit 6. pounce 7. pounced attack 8. pounce end 9. pounce latched 10. ready pounce 11. roar. 12. run 13. scanning 14. walk
    private int aniTick, aniIndex;
    private int playerAction = IDLE;
    private boolean left, right, jump;
    private boolean moving = false, attacking = false, inAir = false;
    private int[][] lvlData;
    private float airSpeed = 0f;
    private boolean isGoingRight = true;
    private BufferedImage statusBars;

    private int maxHealth = 100;
    private int health = maxHealth;
    private int healthWidth = BAR_WIDTH;
    private int maxMana = 100;
    private int mana = maxMana;
    private int manaWidth = BAR_WIDTH;

    private Rectangle2D.Float attackHitbox;
    private int attackHitboxWidth = (int) (22 * PLAYER_SCALE);
    private int attackHitboxHeight = (int) (20 * PLAYER_SCALE);
    private boolean attackChecked;
    private Play play;
    private boolean hurt = false;
    private boolean sneaking = false;
    private Rectangle2D.Float sneakHitbox;
    private float speed = PLAYER_SPEED;
    private int hitboxYOffset = PLAYER_HITBOX_Y_OFFSET;

    boolean isInvoulnerable = false;
    int invoulnerableTime = 0;

    public Player(float x, float y, int width, int height, Play play) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, PLAYER_HITBOX_WIDTH, PLAYER_HITBOX_HEIGHT);
        initAttackHitbox();
        this.play = play;
    }

    private void initAttackHitbox() {
        attackHitbox = new Rectangle2D.Float(x, y, attackHitboxWidth, attackHitboxHeight);
    }

    public void update() {
        updateBars();
        updateAttackHitbox();
        updatePos();
        checkAttack();
        animationTick();
        setAnimation();
    }

    private void checkAttack() {
        if (!attacking || attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        play.checkEnemyHit(attackHitbox);
    }

    private void updateAttackHitbox() {
        if (isGoingRight) {
            attackHitbox.x = hitbox.x + hitbox.width;
        } else {
            attackHitbox.x = hitbox.x - attackHitbox.width;
        }
        attackHitbox.y = hitbox.y + (int) (5 * PLAYER_SCALE);
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        Graphics2D g2d = (Graphics2D) g;
        int x = (int) (hitbox.x - PLAYER_HITBOX_X_OFFSET) - xOffset;
        int y = (int) (hitbox.y - hitboxYOffset) - yOffset;

        if (isGoingRight) {
            g2d.drawImage(animations[playerAction][aniIndex], x, y, width, height, null);
        } else {
            g2d.drawImage(animations[playerAction][aniIndex], x + width, y, -width, height, null);
        }

        //drawHitbox(g, xOffset, yOffset);
        //drawAttackHitbox(g, xOffset);
        //drawUI(g);
    }

    private void drawAttackHitbox(Graphics g, int xOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackHitbox.x - xOffset), (int) attackHitbox.y, (int) attackHitbox.width, (int) attackHitbox.height);
    }

    public void drawUI(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(HEALTH_BAR_X, HEALTH_BAR_Y, BAR_WIDTH + 2, BAR_HEIGHT);
        g.setColor(Color.GREEN);
        g.fillRect(HEALTH_BAR_X, HEALTH_BAR_Y, healthWidth + 2, BAR_HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(MANA_BAR_X, MANA_BAR_Y, BAR_WIDTH + 2, BAR_HEIGHT);
        g.setColor(Color.BLUE);
        g.fillRect(MANA_BAR_X, MANA_BAR_Y, manaWidth + 2, BAR_HEIGHT);
        g.drawImage(statusBars, STATUS_BAR_X, STATUS_BAR_Y, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT, null);
    }

    private void updateBars() {
        healthWidth = (int) ((float) health / maxHealth * BAR_WIDTH);
        manaWidth = (int) ((float) mana / maxMana * BAR_WIDTH);
    }


    private void animationTick() {
        aniTick++;
        if (aniTick > ANIMATION_SPEED) {
            aniTick = 0;
            aniIndex++;

            invoulnerableTime--;
            if (invoulnerableTime <= 0) {
                isInvoulnerable = false;
            }

            if (aniIndex >= getAnimationLength(playerAction)) {
                if (playerAction == DEAD) {
                    play.setGameOver(true);
                    aniIndex--;
                    return;
                }

                if (hurt) {
                    isInvoulnerable = true;
                    invoulnerableTime = 3;
                }

                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                hurt = false;
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

        if(sneaking) {
            playerAction = SNEAKING;
        }

        if(inAir) {
            setSneaking(false);
            if(airSpeed > 0) {
                playerAction = FALLING;
            } else {
                playerAction = JUMP;
            }
        }

        if (attacking && !hurt) {
            playerAction = ATTACKING;
            if (lastAction != ATTACKING) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if (hurt) {
            playerAction = HURT;
        }

        if (health <= 0) {
            playerAction = DEAD;
        }

        if (lastAction != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;
        if (jump && !hurt) {
            if (!inAir) {
                airSpeed = JUMP_SPEED;
                inAir = true;
            }
        }
        if (!inAir && (left == right)) {
            return;
        }

        float xSpeed = 0;

        if (!hurt) {
            if (left) {
                xSpeed -= speed;
                isGoingRight = false;
            }
            if (right) {
                xSpeed += speed;
                isGoingRight = true;
            }
        }

        if (!inAir) {
            if(!IsOnGround(hitbox, lvlData)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(CanMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
            } else {
                hitbox.y = GetYPosToWall(hitbox, airSpeed);
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
        if (CanMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetXPosToWall(hitbox, xSpeed);
        }
    }

    public void heal(int value) {
        health += value;
        if (health >= maxHealth)
            health = maxHealth;
    }

    public void hurt(int value) {
        if (hurt || isInvoulnerable || value <= 0)
            return;

        health -= value;
        if (health <= 0)
            health = 0;
        hurt = true;
    }

    private void loadAnimations() {
        BufferedImage sheet = getAssets(Constants.Assets.PLAYER_ASSETS);
        animations = new Image[14][18];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < 18; j++) {
                animations[i][j] = sheet.getSubimage(j * DEFAULT_SPRITE_WIDTH, i * DEFAULT_SPRITE_HEIGHT, DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);
            }
        }
        statusBars = getAssets(Constants.Assets.STATUS_BARS);
    }

    public void setLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!IsOnGround(hitbox, lvlData)) {
            inAir = true;
        }
    }

    public void setSneaking(boolean sneaking) {
        if (!this.sneaking && sneaking) {
            y -= PLAYER_HITBOX_HEIGHT / 2;
            hitbox.y += (float) PLAYER_HITBOX_HEIGHT / 2;
            hitbox.height = (float) PLAYER_HITBOX_HEIGHT / 2;
            speed = PLAYER_SPEED / 3;
            hitboxYOffset += PLAYER_HITBOX_HEIGHT / 2;
        } else if (this.sneaking && !sneaking) {
            y += PLAYER_HITBOX_HEIGHT / 2;
            hitbox.y -= (float) PLAYER_HITBOX_HEIGHT / 2;
            hitbox.height = (float) PLAYER_HITBOX_HEIGHT;
            speed = PLAYER_SPEED;
            hitboxYOffset = PLAYER_HITBOX_Y_OFFSET;
        }
        this.sneaking = sneaking;
    }

    public boolean getSneaking() {
        return sneaking;
    }

    public void resetMovement() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
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
