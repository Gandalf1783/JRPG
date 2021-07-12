package de.gandalf1783.tilegame.gfx;

import de.gandalf1783.tilegame.tiles.Tile;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.Entity;

public class GameCamera {
	
	private Handler handler;
	private float xOffset, yOffset;
	
	public GameCamera(Handler handler, float xOffset, float yOffset){
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void checkBlankSpace(){
		if(xOffset > handler.getWorld().getWorldChunkSize()*16 * Tile.TILEWIDTH - handler.getWidth()){
			xOffset = handler.getWorld().getWorldChunkSize()*16 * Tile.TILEWIDTH - handler.getWidth();
		}

		if(yOffset > handler.getWorld().getWorldChunkSize()*16 * Tile.TILEHEIGHT - handler.getHeight()){
			yOffset = handler.getWorld().getWorldChunkSize()*16 * Tile.TILEHEIGHT - handler.getHeight();
		}
	}
	
	public void centerOnEntity(Entity e){
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}
	
	public void move(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}
