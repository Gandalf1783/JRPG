package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water12 extends Tile {

    private Animation animation = new Animation(300, Assets.water12, true);

    public Water12(int id) {
        super(Assets.water12[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
