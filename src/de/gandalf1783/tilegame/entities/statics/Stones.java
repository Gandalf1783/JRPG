package de.gandalf1783.tilegame.entities.statics;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;

import java.awt.*;
import java.util.UUID;

public class Stones extends StaticEntity {

    private int type = 0;

    public Stones(Handler handler, UUID uuid, float x, float y, int width, int height, int type, boolean solid) {
        super(handler, uuid, x, y, width, height, solid);
        this.type = type;
        if(this.type < 0) {
            this.type = 0;
        } else if(this.type > 4) {
            this.type = 4;
        }

        if(this.type == 0) {
            bounds.x = (int) (0.34375*width);
            bounds.y = (int) (0.46875*height);
            bounds.width = (int) (0.3125*width);
            bounds.height = (int) (0.21875*height);
        } else if(type == 1) {
            bounds.x = (int) (0.21875*width);
            bounds.y = (int) (0.4375*height);
            bounds.width = (int) (0.375*width);
            bounds.height = (int) (0.25*height);
        } else if(type == 2) {
            bounds.x = (int) (0.1875*width);
            bounds.y = (int) (0.34375*height);
            bounds.width = (int) (0.5*width);
            bounds.height = (int) (0.34375*height);
        }  else if(type == 3) {
            bounds.x = (int) (0.09375*width);
            bounds.y = (int) (0.3125*height);
            bounds.width = (int) (0.59375*width);
            bounds.height = (int) (0.40625*height);
        } else if(type == 4) {
            bounds.x = (int) (0.09375*width);
            bounds.y = (int) (0.1875*height);
            bounds.width = (int) (0.8125*width);
            bounds.height = (int) (0.5625*height);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.stones1[type], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void hurt(int amt) {}

}
