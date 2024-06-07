package effects;

import java.awt.*;
import java.util.ArrayList;

public class EffectHandler {
    private final ArrayList<Effect> effects;

    public EffectHandler() {
        effects = new ArrayList<>();
    }

    public void update() {
        for (Effect effect : effects) {
            if (!effect.isDone())
                effect.update();
        }
    }

    public void render(Graphics g, int xOffset, int yOffset) {
        for (Effect effect : effects) {
            if (!effect.isDone())
                effect.render(g, xOffset, yOffset);
        }
    }

    public void createEffect(int x, int y, int width, int height, int duration, int effectType) {
        Effect effect = new Effect(x, y, width, height, duration, effectType);
        effects.add(effect);
    }
}
