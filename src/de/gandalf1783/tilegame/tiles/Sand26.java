package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand26 extends Tile {

    private Animation animation = new Animation(300, Assets.sand26, true);

    public Sand26(int id) {
        super(Assets.sand26[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
