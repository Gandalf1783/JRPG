package de.gandalf1783.tilegame.entities.statics;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;

import java.awt.*;
import java.util.UUID;

public class Sign203 extends StaticEntity {

    public Sign203(Handler handler, UUID uuid, float x, float y, int width, int height) {
        super(handler, uuid, x, y, width, height, false);
    }

    @Override
    public void tick() {
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.sign203, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
