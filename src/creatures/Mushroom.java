package creatures;

import utils.Constants;
import utils.Loader;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Enemies.*;

public class Mushroom extends Enemy{
    private int attackBoxWidth = (int)(22 * Constants.Config.SCALE);
    private int attackBoxHeight = (int)(16 * Constants.Config.SCALE);
    private int attackHitboxOffsetX = (int)(10 * Constants.Config.SCALE);
    private int attackHitboxOffsetY = (int)(5 * Constants.Config.SCALE);

    public Mushroom(float x, float y) {
        super(x, y, MUSHROOM_SIZE, MUSHROOM_SIZE, MUSHROOM);
        initHitbox(x, y, MUSHROOM_HITBOX_WIDTH, MUSHROOM_HITBOX_HEIGHT);
        initAttackHitbox(x, y, attackBoxWidth, attackBoxHeight);
        hitboxXOffset = MUSHROOM_HITBOX_X_OFFSET;
        hitboxYOffset = MUSHROOM_HITBOX_Y_OFFSET;
        enemyState = IDLE;
        walkSpeed = MUSHROOM_SPEED;
        loadAnimations();
    }

    private void initAttackHitbox(float x, float y, int width, int height) {
        attackHitbox = new Rectangle2D.Float(x, y, width, height);
    }

    private void loadAnimations() {
        animations = new BufferedImage[5][8];
        BufferedImage sheet = Loader.getAssets(Constants.Assets.MUSHROOM_ASSETS);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                animations[i][j] = sheet.getSubimage(j * MUSHROOM_SPRITE_SIZE, i * MUSHROOM_SPRITE_SIZE, MUSHROOM_SPRITE_SIZE, MUSHROOM_SPRITE_SIZE);
            }
        }
    }

    @Override
    protected void updateBehaviour(int[][] lvlData, Player player) {
        updateAttackHitbox();
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE -> setState(RUNNING);
                case RUNNING -> {
                    if(canSeePlayer(lvlData, player) && isPlayerOnTheSameTile(player))
                        turnToPlayer(player);
                    if (isPlayerInAttackRange(player))
                        setState(ATTACKING);
                    move(lvlData);
                }
                case ATTACKING -> {
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 6 && !attackChecked) {
                        checkHit(player);
                    }
                }
            }
        }
    }

    @Override
    protected void updateAttackHitbox() {
        if (direction == RIGHT) {
            attackHitbox.x = hitbox.x + hitbox.width;
        } else {
            attackHitbox.x = hitbox.x - attackBoxWidth;
        }
        attackHitbox.y = hitbox.y + attackHitboxOffsetY;
    }
}
