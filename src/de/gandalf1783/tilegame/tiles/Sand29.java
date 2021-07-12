package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand29 extends Tile {

    private Animation animation = new Animation(300, Assets.sand29, true);

    public Sand29(int id) {
        super(Assets.sand29[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
