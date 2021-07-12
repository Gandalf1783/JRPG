package de.gandalf1783.tilegame.entities.statics;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.Entity;

import java.awt.*;
import java.util.UUID;

public abstract class StaticEntity extends Entity {

	private UUID uuid = UUID.randomUUID();
	private Boolean solid = false;

	public StaticEntity(Handler handler, UUID uuid, float x, float y, int width, int height, boolean solid){
		super(handler, x, y, width, height);
		this.uuid = uuid;
		this.solid = solid;
	}

	public void hurt(int amt) {
		super.hurt(amt);
	}

	public void die() {
		active = false;
	}

	public Boolean isSolid() { return solid;}

	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
}
