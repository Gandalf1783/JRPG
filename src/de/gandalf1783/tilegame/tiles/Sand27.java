package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;

public class Sand27 extends Tile {

    private Animation animation = new Animation(300, Assets.sand27, true);

    public Sand27(int id) {
        super(Assets.sand27[0], id);
    }

    @Override
    public void tick() {
        animation.tick();
        texture = animation.getCurrentFrame();
    }
}
