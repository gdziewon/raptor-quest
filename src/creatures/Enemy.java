package creatures;

import utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Config.ANIMATION_SPEED;
import static utils.Constants.Config.GRAVITY;
import static utils.Constants.Enemies.*;
import static utils.Utils.*;

public abstract class Enemy extends Creature{
    protected int aniIndex, aniTick, enemyState, enemyType;
    protected Image[][] animations;
    protected int hitboxXOffset, hitboxYOffset;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed = 1f;
    protected float walkSpeed;
    protected int direction = LEFT;
    protected int tileYPos;
    protected int attackRange = Constants.Config.TILE_SIZE;
    protected Rectangle2D.Float attackHitbox;
    protected int maxHealth;
    protected int health;
    protected boolean alive = true;
    protected boolean attackChecked;

    protected static final int LEFT = 0, RIGHT = 1;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        health = maxHealth;
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick > ANIMATION_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getAnimationLength(enemyState, enemyType)) {
                aniIndex = 0;
                switch (enemyState) {
                    case ATTACKING, HURT -> setState(IDLE);
                    case DEAD -> alive = false;
                }
            }
        }
    }

    protected void setState(int state) {
        enemyState = state;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsOnGround(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMove(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetYPosToWall(hitbox, fallSpeed);
            tileYPos = (int) (hitbox.y / Constants.Config.TILE_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;
        if (direction == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }

        if (CanMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData) &&
                IsFloor(hitbox, xSpeed, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            direction = (direction == LEFT) ? RIGHT : LEFT;
        }
    }

    protected void turnToPlayer(Player player) {
        if (player.getHitbox().x < hitbox.x) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        return isPlayerInRange(player) && IsSightClear(lvlData, hitbox, player.getHitbox());
    }

    protected boolean isPlayerInRange(Player player) {
        float range = player.getSneaking() ? attackRange * 1.5f : attackRange * 4;
        return Math.abs(player.getHitbox().x - hitbox.x) < range;
    }

    protected boolean isPlayerInAttackRange(Player player) {
        return Math.abs(player.getHitbox().x - hitbox.x) < attackRange && isPlayerOnTheSameTile(player);
    }

    protected boolean isPlayerOnTheSameTile(Player player) {
        return (int) ((player.getHitbox().y + player.getHitbox().height) / Constants.Config.TILE_SIZE) == tileYPos;
    }

    protected abstract void updateBehaviour(int[][] lvlData, Player player);


    public void update(int[][] lvlData, Player player) {
        updateBehaviour(lvlData, player);
        updateAnimation();
        updateAttackHitbox();

        if (hitbox.intersects(player.getHitbox())) {
            player.hurt(getDamage(enemyType) / 2);
        }
    }

    protected abstract void updateAttackHitbox();

    public void render(Graphics g, int xOffset, int yOffset) {
        Graphics2D g2d = (Graphics2D) g;

        int x = (int) (hitbox.x - hitboxXOffset) - xOffset;
        int y = (int) (hitbox.y - hitboxYOffset) - yOffset;

        if (direction == RIGHT) {
            g2d.drawImage(animations[enemyState][aniIndex], x, y, width, height, null);
        } else {
            g2d.drawImage(animations[enemyState][aniIndex], x + width, y, -width, height, null);
        }

        drawHitbox(g, xOffset, yOffset);
        drawAttackHitbox(g, xOffset, yOffset);
    }

    protected void drawAttackHitbox(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackHitbox.x - xOffset), (int) (attackHitbox.y - yOffset), (int) attackHitbox.width, (int) attackHitbox.height);
    }

    public boolean isAlive() {
        return alive;
    }

    public void hurt(int damage) {
        health -= damage;
        if (health <= 0) {
            setState(DEAD);
        } else {
            setState(HURT);
        }
    }

    protected void checkHit(Player player) {
        if (attackHitbox.intersects(player.getHitbox())) {
            player.hurt(getDamage(enemyType));
        }
        attackChecked = true;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
