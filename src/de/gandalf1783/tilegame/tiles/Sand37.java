package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand37 extends Tile {
    private Animation animation = new Animation(370, Assets.sand37, true);

    public Sand37(int id) {
        super(Assets.sand37[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
