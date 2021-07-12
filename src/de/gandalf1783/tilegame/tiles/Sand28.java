package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand28 extends Tile {

    private Animation animation = new Animation(300, Assets.sand28, true);

    public Sand28(int id) {
        super(Assets.sand28[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
