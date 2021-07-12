package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Waterfall21 extends Tile {

    private Animation animation;

    public Waterfall21(int id) {
        super(Assets.water21[0], id);
        animation = new Animation(220, Assets.water21, true);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
