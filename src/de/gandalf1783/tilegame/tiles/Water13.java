package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water13 extends Tile {

    private Animation animation = new Animation(300, Assets.water13, true);

    public Water13(int id) {
        super(Assets.water13[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
