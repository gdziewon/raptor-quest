package utils;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Config;

public class Utils {
    public static boolean CanMove(float x, float y, float width, float height, int[][] lvlData) {
        return !(IsSolid(x, y, lvlData) ||
                IsSolid(x + width, y, lvlData) ||
                IsSolid(x, y + height, lvlData) ||
                IsSolid(x + width, y + height, lvlData));
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData.length * Config.TILE_SIZE;
        if (x < 0 || x >= maxWidth || y < 0 || y >= lvlData[0].length * Config.TILE_SIZE) {
            return true;
        }

        int tileX = (int) x / Config.TILE_SIZE;
        int tileY = (int) y / Config.TILE_SIZE;

        return isTileSolid(tileX, tileY, lvlData);
    }

    public static float GetXPosToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / Config.TILE_SIZE);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Config.TILE_SIZE;
            int offset = (int)(hitbox.width - Config.TILE_SIZE);
            return tileXPos - offset - 1;
        } else {
            return currentTile * Config.TILE_SIZE;
        }
    }

    public static float GetYPosToWall(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)(hitbox.y / Config.TILE_SIZE);
        if (airSpeed > 0) {
            int tileYPos = currentTile * Config.TILE_SIZE;
            int offset = (int)(hitbox.height - Config.TILE_SIZE);
            return tileYPos - offset - 1;
        } else {
            return currentTile * Config.TILE_SIZE;
        }
    }

    public static boolean IsOnGround(Rectangle2D.Float hitbox, int[][] lvlData) {
        return IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData) ||
                IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        float xOffset = xSpeed > 0 ? hitbox.width : 0;
        return IsSolid(hitbox.x + xSpeed + xOffset, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean isTileSolid(int x, int y, int[][] lvlData) {
        return lvlData[x][y] != 0;
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = xStart; i <= xEnd; i++) {
            if (isTileSolid(i, y, lvlData) || !isTileSolid(i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox) {
        int firstX = (int) firstHitbox.x / Config.TILE_SIZE;
        int secondX = (int) secondHitbox.x / Config.TILE_SIZE;
        if (firstX > secondX)
            return IsAllTilesWalkable(secondX, firstX, (int) firstHitbox.y / Config.TILE_SIZE, lvlData);
        else
            return IsAllTilesWalkable(firstX, secondX, (int) firstHitbox.y / Config.TILE_SIZE, lvlData);
    }

}
