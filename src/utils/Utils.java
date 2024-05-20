package utils;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Config;

public class Utils {
    public static boolean canMove(float x, float y, float width, float height, int[][] lvlData) {
        return !(isSolid(x, y, lvlData) ||
                isSolid(x + width, y, lvlData) ||
                isSolid(x, y + height, lvlData) ||
                isSolid(x + width, y + height, lvlData));
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData.length * Config.TILE_SIZE;
        if (x < 0 || x >= maxWidth || y < 0 || y >= Config.HEIGHT) {
            return true;
        }

        float xIndex = x / Config.TILE_SIZE;
        float yIndex = y / Config.TILE_SIZE;

        return lvlData[(int)xIndex][(int)yIndex] != 0;
    }

    public static float getXPosToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / Config.TILE_SIZE);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Config.TILE_SIZE;
            int offset = (int)(hitbox.width - Config.TILE_SIZE);
            return tileXPos - offset - 1;
        } else {
            return currentTile * Config.TILE_SIZE;
        }
    }

    public static float getYPosToWall(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)(hitbox.y / Config.TILE_SIZE);
        if (airSpeed > 0) {
            int tileYPos = currentTile * Config.TILE_SIZE;
            int offset = (int)(hitbox.height - Config.TILE_SIZE);
            return tileYPos - offset - 1;
        } else {
            return currentTile * Config.TILE_SIZE;
        }
    }

    public static boolean isOnGround(Rectangle2D.Float hitbox, int[][] lvlData) {
        return isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData) ||
                isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
    }
}
