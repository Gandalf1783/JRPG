package de.gandalf1783.tilegame.tiles;

import de.gandalf1783.tilegame.gfx.Assets;

public class CobbleStoneTile extends Tile {


    public CobbleStoneTile(int id) {
        super(Assets.cobblestone, id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
