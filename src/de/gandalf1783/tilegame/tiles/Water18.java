package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water18 extends Tile {

    private Animation animation = new Animation(300, Assets.water18, true);

    public Water18(int id) {
        super(Assets.water18[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
