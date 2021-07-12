package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water17 extends Tile {

    private Animation animation = new Animation(300, Assets.water17, true);

    public Water17(int id) {
        super(Assets.water17[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
