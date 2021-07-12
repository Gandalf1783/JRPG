package de.gandalf1783.tilegame.entities.creatures;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.entities.Entity;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.inventory.Inventory;
import de.gandalf1783.tilegame.objects.BasicRequest;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.states.State;

public class Player extends Creature {
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight;
	private Animation animAttackDown, animAttackUp, animAttackLeft, animAttackRight;
	private Boolean attackDown, attackUp, attackLeft, attackRight;
	// Inventory
	private Inventory inventory;
	private de.gandalf1783.quadtree.Rectangle rect;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 22;
		bounds.y = 44;
		bounds.width = 19;
		bounds.height = 19;

		rect = new de.gandalf1783.quadtree.Rectangle(x,y,19,19);

		//Animatons
		animDown = new Animation(50, Assets.player_down, true);
		animUp = new Animation(50, Assets.player_up, true);
		animLeft = new Animation(50, Assets.player_left, true);
		animRight = new Animation(50, Assets.player_right, true);
		animAttackDown = new Animation(100, Assets.player_attackDown, false);
		animAttackLeft = new Animation(100, Assets.player_attackLeft, false);
		animAttackRight = new Animation(100, Assets.player_attackRight,false);
		animAttackUp = new Animation(100, Assets.player_attackUp, false);
		//Attack Animations
		attackDown = false;
		attackLeft = false;
		attackRight = false;
		attackUp = false;
		inventory = new Inventory(handler);
	}

	/**
	 * Ticks the Player
	 */
	@Override
	public void tick() {
		//Animations
		if(active) {
			animDown.tick();
			animUp.tick();
			animRight.tick();
			animLeft.tick();
			animAttackUp.tick();
			animAttackRight.tick();
			animAttackLeft.tick();
			animAttackDown.tick();
			//Movement
			getInput(); //Retrives the Input from Keyboard
			move(); //Moves the Player accordingly
			// Attack
			checkAttacks();
			// Inventory
			inventory.tick(); //Ticks the Inventory and its Contents
		}
		if(attackUp && animAttackUp.getHasPlayed()) {
			attackUp = false;
		}
		if(attackRight && animAttackRight.getHasPlayed()) {
			attackRight = false;
		}
		if(attackLeft && animAttackLeft.getHasPlayed()) {
			attackLeft = false;
		}
		if(attackDown && animAttackDown.getHasPlayed()) {
			attackDown = false;
		}

		handler.getGameCamera().centerOnEntity(this);
	}

	/**
	 * Checks for Attack from the Booleans
	 */
	private void checkAttacks(){
		if(attackDown || attackLeft || attackRight || attackUp)
			return;
		
		if(inventory.isActive())
			return;

		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		if(handler.getKeyManager().aUp){
			attackUp = true;
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
			if(animAttackUp.getHasPlayed())
				animAttackUp = new Animation(90, Assets.player_attackUp, false);
		}else if(handler.getKeyManager().aDown){
			attackDown = true;
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
			if(animAttackDown.getHasPlayed())
				animAttackDown = new Animation(90, Assets.player_attackDown, false);
		}else if(handler.getKeyManager().aLeft){
			attackLeft = true;
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
			if(animAttackLeft.getHasPlayed()) {
				animAttackLeft = new Animation(90, Assets.player_attackLeft, false);
			}
		}else if(handler.getKeyManager().aRight){
			attackRight = true;
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
			if(animAttackRight.getHasPlayed())
				animAttackRight = new Animation(90, Assets.player_attackRight,false);
		}else{
			return;
		}

		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				e.hurt(1);
				if(State.getState() == handler.getGame().multiplayerGameState) {
					if(MultiplayerGameState.getEntityHashMap().containsValue(e)) {
						((MultiplayerGameState) State.getState()).handleEntityHit(getKeyFromValue(MultiplayerGameState.getEntityHashMap(), e).toString(), 1);
					}
				}
			}
		}
	}

	/**
	 * Handles the dead of the Player
	 */
	@Override
	public void die(){
		if(State.getState() == handler.getGame().multiplayerGameState){
			System.out.println("You died.");
			MultiplayerGameState.handlePlayerDead();
			active = false;
		}
	}

	/**
	 * Retrieves the input of the Player (Keyboard) and sets the xMove and yMove accordingly.
	 */
	private void getInput(){
		xMove = 0;
		yMove = 0;

		if(inventory.isActive())
			return;
		
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
		if(handler.getKeyManager().dropItem) {
			getInventory().dropItem();
		}

	}

	/**
	 * Renders the current Animation Frame of the Player
	 * @param g
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		Text.drawString(g, "V", (int) (x - handler.getGameCamera().getxOffset())+32, (int) (y - handler.getGameCamera().getyOffset()) - 10, true, Color.ORANGE, Assets.font23);
	}

	/**
	 * Renders e.g. an interface afterwards the player has been rendered. This means, that everything here will be rendered on top of the player texture.
	 * @param g Graphics Object g
	 */
	public void postRender(Graphics g){
		inventory.render(g);
	}

	/**
	 * Returns the current AnimationFrame as an BufferedImage.
	 * @return
	 */
	private BufferedImage getCurrentAnimationFrame(){
		if(attackUp) {
			return animAttackUp.getCurrentFrame();
		} else if(attackRight) {
			return animAttackRight.getCurrentFrame();
		} else if(attackLeft) {
			return animAttackLeft.getCurrentFrame();
		} else if(attackDown) {
			return animAttackDown.getCurrentFrame();
		} else if(xMove < 0){
			return animLeft.getCurrentFrame();
		}else if(xMove > 0){
			return animRight.getCurrentFrame();
		}else if(yMove < 0){
			return animUp.getCurrentFrame();
		} else if(yMove > 0) {
			return animDown.getCurrentFrame();
		} else {
			return Assets.player_stand;
		}
	}

	/**
	 * Returns the Inventory of the Player
	 * @return Inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Sets the Inventory of the Player
	 * @param inventory Inventory
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	/**
	 * Returns the Key from a Value from a Map
	 * @param hm Map to work with
	 * @param value Value to Search for. (Object)
	 * @return Returns the Key (Object)
	 */
	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Sets the Health of the Player
	 * @param health Health (int)
	 */
	@Override
	public void setHealth(int health) {
		super.setHealth(health);
		if(health <= 0) {
			BasicRequest request = new BasicRequest();
			request.text = "DIE";
			request.data = MultiplayerGameState.getUUID().toString();
			MultiplayerGameState.getClient().sendTCP(request);
		}

	}

	public de.gandalf1783.quadtree.Rectangle getRect() {
		return rect;
	}

	public void setRect(de.gandalf1783.quadtree.Rectangle rect) {
		this.rect = rect;
	}

}
