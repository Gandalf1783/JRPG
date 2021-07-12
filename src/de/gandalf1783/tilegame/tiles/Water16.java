package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water16 extends Tile {

    private Animation animation = new Animation(300, Assets.water16, true);

    public Water16(int id) {
        super(Assets.water16[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
