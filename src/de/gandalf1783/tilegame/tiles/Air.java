package de.gandalf1783.tilegame.tiles;

import java.awt.image.BufferedImage;

public class Air extends Tile{

    public Air(int id) {
        super(new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB), id);
    }

}
