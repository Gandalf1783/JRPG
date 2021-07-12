package de.gandalf1783.tilegame;

import de.gandalf1783.tilegame.gfx.GameCamera;
import de.gandalf1783.tilegame.input.KeyManager;
import de.gandalf1783.tilegame.input.MouseManager;
import de.gandalf1783.tilegame.world.World;


public class Handler {
	
	private Game game;
	private World world;

	public Handler(Game game){
		this.game = game;
	}
	
	public GameCamera getGameCamera(){
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
