package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water15 extends Tile {

    private Animation animation = new Animation(300, Assets.water15, true);

    public Water15(int id) {
        super(Assets.water15[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
