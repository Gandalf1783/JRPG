package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water10 extends Tile {

    private Animation animation = new Animation(300, Assets.water10, true);

    public Water10(int id) {
        super(Assets.water10[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
