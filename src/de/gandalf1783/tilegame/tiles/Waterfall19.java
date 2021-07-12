package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Waterfall19 extends Tile {

    private Animation animation;

    public Waterfall19(int id) {
        super(Assets.water19[0], id);
        animation = new Animation(220, Assets.water19, true);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
