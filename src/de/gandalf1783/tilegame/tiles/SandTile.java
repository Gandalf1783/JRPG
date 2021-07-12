package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Assets;

public class SandTile extends Tile {

    public SandTile(int id) {
        super(Assets.sand, id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
