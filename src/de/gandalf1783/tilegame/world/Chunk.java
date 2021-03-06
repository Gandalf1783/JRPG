package de.gandalf1783.tilegame.world;

import de.gandalf1783.quadtree.Rectangle;

import java.io.Serializable;

public class Chunk implements Serializable {

    // Y is UP/DOWN!   x y z            x  y  z
    private int[][][] blocks = new int[16][3][16];
    private int chunkX, chunkY;
    private Rectangle rect;

    public Chunk() {
        rect = new de.gandalf1783.quadtree.Rectangle(chunkX*16,chunkY*16, (chunkX+1)*16, (chunkY+1)*16);
    }

    public void setBlock(int x, int y, int z, int blockid) {
        this.blocks[x][y][z] = blockid;
    }
    public void setBlocks(int[][][] blocks) {
        this.blocks = blocks;
    }
    public int[][][] getBlocks() {
        return blocks;
    }
    public int getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }
    public int getChunkX() {
        return chunkX;
    }
    public int getChunkY() {
        return chunkY;
    }

    public void setChunkY(int chunkY) {
        this.chunkY = chunkY;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public Rectangle getRect() {
        rect = new de.gandalf1783.quadtree.Rectangle(chunkX*16*53,chunkY*16*53, (chunkX+1)*16*53, (chunkY+1)*16*53);
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
}
