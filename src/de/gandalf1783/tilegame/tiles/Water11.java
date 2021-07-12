package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water11 extends Tile {

    private Animation animation = new Animation(300, Assets.water11, true);

    public Water11(int id) {
        super(Assets.water11[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
