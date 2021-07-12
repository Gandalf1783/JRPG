package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class DeepWaterTile extends Tile {

    private Animation animation = new Animation(300, Assets.deepWater, true);

    public DeepWaterTile( int id) {
        super(Assets.deepWater[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }

}
