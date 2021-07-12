package de.gandalf1783.tilegame.entities.statics;

import java.awt.Graphics;
import java.util.UUID;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.items.Item;
import de.gandalf1783.tilegame.tiles.Tile;

public class Tree extends StaticEntity {

	public Tree(Handler handler, UUID uuid, float x, float y) {
		super(handler, uuid, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, true);
		
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f);
		bounds.width = width - 20;
		bounds.height = (int) (height - height / 1.5f);
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){
		handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
