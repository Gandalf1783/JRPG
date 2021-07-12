package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand35 extends Tile {
    private Animation animation = new Animation(350, Assets.sand35, true);

    public Sand35(int id) {
        super(Assets.sand35[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
