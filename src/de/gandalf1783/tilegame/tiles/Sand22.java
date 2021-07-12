package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand22 extends Tile {

    private Animation animation = new Animation(300, Assets.sand22, true);

    public Sand22(int id) {
        super(Assets.sand22[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
