package de.gandalf1783.tilegame.entities.statics;

import java.awt.Graphics;
import java.util.UUID;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.tiles.Tile;
;

public class Rock extends StaticEntity {

	public Rock(Handler handler, UUID uuid, float x, float y, Boolean solid) {
		super(handler, uuid, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, solid);
		
		bounds.x = 3;
		bounds.y = (int) (height / 2f);
		bounds.width = width - 6;
		bounds.height = (int) (height - height / 2f);
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
