package de.gandalf1783.tilegame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Map;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.statics.Cactus;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

public abstract class Entity {

	public static final int DEFAULT_HEALTH = 3;

	public static Class[] entityClasses = new Class[256];

	public static Class getEntityClassByID(int EID) {
		return entityClasses[EID];
	}

	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected int health;
	protected boolean active = true;
	protected Rectangle bounds;
	protected boolean wasHit = false;
	protected boolean solid = true;
	protected int direction = 0;

	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;

		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public void hurt(int amt){
		wasHit = true;
		health -= amt;
		if(health <= 0){
			active = false;
		}
	}
	
	public boolean checkEntityCollisions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(!e.isSolid()) continue;
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		Iterator it = MultiplayerGameState.getEntityHashMap().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Entity e = (Entity) pair.getValue();
			if(e.equals(this)) continue;
			if(!e.isSolid()) continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Boolean isSolid() { return solid; }

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
