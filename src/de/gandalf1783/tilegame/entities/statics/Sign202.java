package de.gandalf1783.tilegame.entities.statics;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.items.Item;

import java.awt.*;
import java.util.UUID;

public class Sign202 extends StaticEntity {

    public Sign202(Handler handler, UUID uuid, float x, float y, int width, int height) {
        super(handler, uuid, x, y, width, height, false);
    }

    @Override
    public void tick() {
    }

    @Override
    public void die() {
        super.die();
        handler.getWorld().getItemManager().addItem(Item.signItem202.createNew((int) x, (int) y));
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.sign202, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

}
