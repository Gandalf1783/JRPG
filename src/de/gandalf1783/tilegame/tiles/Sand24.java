package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand24 extends Tile {

    private Animation animation = new Animation(300, Assets.sand24, true);

    public Sand24(int id) {
        super(Assets.sand24[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
    
}
