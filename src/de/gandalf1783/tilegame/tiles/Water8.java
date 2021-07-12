package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water8 extends Tile {

    private Animation animation = new Animation(300, Assets.water8, true);

    public Water8(int id) {
        super(Assets.water8[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
