package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand32 extends Tile {
    private Animation animation = new Animation(320, Assets.sand32, true);

    public Sand32(int id) {
        super(Assets.sand32[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
