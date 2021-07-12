package de.gandalf1783.tilegame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.creatures.Player;

public class EntityManager {
	
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> allEntities;
	private Comparator<Entity> renderSorter = (a, b) -> {
		if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
			return -1;
		return 1;
	};
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		allEntities = new ArrayList<Entity>();
		addEntity(player);
	}

	/**
	 * Ticks the Entity Manager and all the Sub-Entities of it (if any)
	 */
	public void tick(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			e.tick();
			if(!e.isActive())
				it.remove();
		}
		entities.sort(renderSorter);
	}

	/**
	 * Renders all Entities.
	 * @param g
	 */
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
		player.postRender(g);
	}

	/**
	 * Adds a Entity to the Manager
	 * @param e
	 */
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	//GETTERS SETTERS

	/**
	 * Returns the Handler
	 * @return Handler Instance
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * Sets the Gamehandler Instance
	 * @param handler Handler Instance
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	/**
	 * Returns the Player of the current Instance
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the Player. Not used yet.
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Returns the Entity ArrayList
	 * @return Entity ArrayList
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	/**
	 * Sets the Entity ArrayList
	 * @param entities
	 */
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}
