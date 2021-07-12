package de.gandalf1783.tilegame.entities.statics;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

public class Cactus extends StaticEntity {

    private BufferedImage[] cactus = Assets.cactus;
    private int imageIndex = 2;

    public Cactus(Handler handler, UUID uuid, float x, float y, int width, int height) {
        super(handler, uuid, x, y, width, height, true);
        bounds.x = 11;
        bounds.y = 0;
        bounds.width = 32;
        bounds.height = 48;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(cactus[imageIndex], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void hurt(int amt) {
        System.out.println("HEALTH: "+health);
        imageIndex--;
        if(imageIndex < 0) {
            imageIndex = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public Boolean isSolid() {
        return true;
    }
}
