package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water14 extends Tile {

    private Animation animation = new Animation(300, Assets.water14, true);

    public Water14(int id) {
        super(Assets.water14[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
