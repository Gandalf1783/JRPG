package de.gandalf1783.tilegame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.UUID;

import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.creatures.Player;
import de.gandalf1783.tilegame.states.State;

public class Item {


	public static BufferedImage[] items = new BufferedImage[256];

	public static Item woodItem = new Item(Assets.wood, "Wood", 0, UUID.randomUUID());
	public static Item cactusItem = new Item(Assets.cactus[2], "Cactus", 2, UUID.randomUUID());
	public static Item signItem201 = new Item(Assets.sign201, "Double-Sign", 201, UUID.randomUUID());
	public static Item signItem202 = new Item(Assets.sign202, "Sign",202, UUID.randomUUID());

	
	public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x, y, dimension, count;
	protected boolean pickedUp = false;

	public UUID uuid;

	public Item(BufferedImage texture, String name, int id, UUID uuid){
		this.texture = texture;
		this.name = name;
		this.id = id;

		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);

		items[id] = this.texture;
		this.uuid = uuid;
	}

	public void tick(){
		Player boundsPlayer = handler.getWorld().getEntityManager().getPlayer();
		Rectangle outerBounds = boundsPlayer.getCollisionBounds(0f,0f);

		if(outerBounds.intersects(this.bounds)){
			if(State.getState() == handler.getGame().multiplayerGameState) {
				//TODO:
				// Do not pickup automatically in Multiplayer!
			} else {
				pickedUp = true;
				handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
			}
		}
	}
	
	public void render(Graphics g){
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}

	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}

	public void render(Graphics g, int x, int y, int w, int h){
		g.drawImage(texture, x, y, w, h, null);
	}

	public Item createNew(int count){
		Item i = new Item(texture, name, id, UUID.randomUUID());
		i.setPickedUp(false);
		i.setCount(count);
		return i;
	}

	public Item createNew(int x, int y){
		Item i = new Item(texture, name, id, UUID.randomUUID());
		i.setPosition(x, y);
		return i;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}
	
	// Getters and Setters

	public Handler getHandler() {
		return handler;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getDimension() {
		return dimension;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public UUID getUuid() {
		return uuid;
	}
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
}
