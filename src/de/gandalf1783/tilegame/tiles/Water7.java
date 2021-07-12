package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water7 extends Tile {

    private Animation animation = new Animation(300, Assets.water7, true);

    public Water7(int id) {
        super(Assets.water7[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
