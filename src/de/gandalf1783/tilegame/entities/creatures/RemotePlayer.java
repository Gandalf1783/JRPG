package de.gandalf1783.tilegame.entities.creatures;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.inventory.Inventory;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

public class RemotePlayer extends Creature {

    //Animations
    private Animation animDown, animUp, animLeft, animRight;
    // Attack timer
    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
    // Inventory
    private Inventory inventory;

    float lastx, lasty;

    private String name;
    private UUID uuid;

    public RemotePlayer(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_WIDTH);
        bounds.x = 22;
        bounds.y = 44;
        bounds.width = 19;
        bounds.height = 19;

        //Animatons
        animDown = new Animation(50, Assets.remoteplayer_down, true);
        animUp = new Animation(50, Assets.remoteplayer_up, true);
        animLeft = new Animation(50, Assets.remoteplayer_left, true);
        animRight = new Animation(50, Assets.remoteplayer_right, true);
        inventory = new Inventory(handler);
        name = "Player";
    }

    /**
     * Ticks the RemotePlayer.
     */
    @Override
    public void tick() {
        //Test if still alive:
        if(health == 0 || !(MultiplayerGameState.getEntityHashMap().containsValue(this))) {
            active = false; //If not in the Map anymore, set alive = false, so the Object is getting removed and dies.
        }
        //Animations
        //TODO: Add Direction Detection and set the correct AnimationFrame (its Direction), so its looks better.
        animDown.tick();
        animUp.tick();
        animRight.tick();
        animLeft.tick();

        inventory.tick();
    }


    /**
     * Handles a hit of a RemotePlayer.
     * @param amt
     */
    @Override
    public void hurt(int amt) {
        if(!MultiplayerGameState.getEntityHashMap().containsValue(this)) { //Tests if the RemotePlayer is still valid in the World
            return; //If not, return and do nothing.
        }
        super.hurt(amt);
    }

    /**
     * Renders the current AnimationFrame onto the Display
     * @param g The current Graphics g Object.
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        Text.drawString(g, this.name, (int) (x - handler.getGameCamera().getxOffset())+32, (int) (y - handler.getGameCamera().getyOffset()) - 10, true, Color.WHITE, Assets.font15);
    }


    /**
     * Function, that handles the dead of a RemotePlayer.
     */
    @Override
    public void die() {
        System.out.println("[REMOTE PLAYER] DEAD.");
        this.active = false;
    }

    /**
     * Returns the current AnimationFrame as an BufferedImage
     * @return BufferedImage
     */
    private BufferedImage getCurrentAnimationFrame(){
        if(direction == 4){
            return animLeft.getCurrentFrame();
        }else if(direction == 2){
            return animRight.getCurrentFrame();
        }else if(direction == 1){
            return animUp.getCurrentFrame();
        } else if(direction == 3) {
            return animDown.getCurrentFrame();
        } else {
            return animDown.getCurrentFrame();
        }
    }

    /**
     * Sets UUID of the RemotePlayer
     * @param uuid
     */
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public Boolean isSolid() {
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
