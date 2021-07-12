package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Waterfall20 extends Tile {


    private Animation animation;

    public Waterfall20(int id) {
        super(Assets.water20[0], id);
        animation = new Animation(220, Assets.water20, true);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
