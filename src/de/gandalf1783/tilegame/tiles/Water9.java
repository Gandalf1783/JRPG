package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Water9 extends Tile {

    private Animation animation = new Animation(300, Assets.water9, true);

    public Water9(int id) {
        super(Assets.water9[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
