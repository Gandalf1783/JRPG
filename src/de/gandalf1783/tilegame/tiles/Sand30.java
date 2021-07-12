package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand30 extends Tile {
    private Animation animation = new Animation(300, Assets.sand30, true);

    public Sand30(int id) {
        super(Assets.sand30[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
