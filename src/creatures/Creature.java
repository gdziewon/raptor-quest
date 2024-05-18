package creatures;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Creature {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    public Creature(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // for debugging purposes
    protected void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
