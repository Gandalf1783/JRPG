package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand23 extends Tile {

    private Animation animation = new Animation(300, Assets.sand23, true);

    public Sand23(int id) {
        super(Assets.sand23[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
