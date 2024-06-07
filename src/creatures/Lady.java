package creatures;
import utils.Constants;
import utils.Loader;

import static utils.Constants.Enemies.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Lady extends Enemy{

    public Lady(float x, float y) {
        super(x, y, LADY_WIDTH, LADY_HEIGHT, LADY);
        initHitbox(x, y, LADY_HITBOX_WIDTH, LADY_HITBOX_HEIGHT);
        hitboxXOffset = LADY_HITBOX_X_OFFSET;
        hitboxYOffset = LADY_HITBOX_Y_OFFSET;
        enemyState = new Random().nextInt(4);
        walkSpeed = 0;
        loadAnimations();
    }

    private void loadAnimations() {
        animations = new BufferedImage[4][8];
        BufferedImage sheet = Loader.getAssets(Constants.Assets.LADY_ASSETS);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                animations[i][j] = sheet.getSubimage(j * LADY_SPRITE_WIDTH, i * LADY_SPRITE_HEIGHT, LADY_SPRITE_WIDTH, LADY_SPRITE_HEIGHT);
            }
        }
    }

    @Override
    protected void updateBehaviour(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir)
            updateInAir(lvlData);
    }

    @Override
    protected void updateAttackHitbox() {

    }
}
