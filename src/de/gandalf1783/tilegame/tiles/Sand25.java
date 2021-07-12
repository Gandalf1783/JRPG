package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand25 extends Tile {

    private Animation animation = new Animation(300, Assets.sand25, true);

    public Sand25(int id) {
        super(Assets.sand25[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
    
}
