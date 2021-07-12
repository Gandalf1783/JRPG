package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class WaterTile extends Tile {

    private Animation animation = new Animation(300, Assets.water1, true);

    public WaterTile( int id) {
        super(Assets.water1[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }

}
