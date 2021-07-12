package de.gandalf1783.tilegame.states;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.Entity;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.ui.UIManager;
import de.gandalf1783.tilegame.world.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class YouDiedState extends State {

    private World world;
    private Boolean displayDebug;
    private UUID uuid;
    private HashMap<String, Entity> entityHashMap;
    private UIManager uiManager;


    public YouDiedState(Handler handler, Boolean displayDebug, HashMap<String, Entity> entityHashMap, UUID uuid) {
        super(handler);
        world = handler.getWorld();
        this.displayDebug = displayDebug;
        this.entityHashMap = entityHashMap;
        this.uuid = uuid;
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
    }

    @Override
    public void tick() {
        //ticking World Entities
        world.tick();
        //ticking Server Entities
        Iterator it = entityHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Entity e = (Entity) pair.getValue();
            e.tick();
        }
    }


    @Override
    public void render(Graphics g) {
        //rendering World Entities
        world.render(g);
        if(handler.getKeyManager().debug) {
            try {
                displayDebug = !displayDebug;
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
        }

        if(displayDebug) {
            int x = (int) world.getEntityManager().getPlayer().getX();
            int y = (int) world.getEntityManager().getPlayer().getY();
            int width = world.getEntityManager().getPlayer().getWidth();
            int height = world.getEntityManager().getPlayer().getHeight();
            Text.drawString(g, "X:  " + x, 10, 12, false, Color.WHITE, Assets.font15);
            Text.drawString(g, "Y:  " + y, 10, 24, false, Color.WHITE, Assets.font15);
            Text.drawString(g, "HP: " +world.getEntityManager().getPlayer().getHealth(), 10, 36, false, Color.WHITE, Assets.font15);
            Text.drawString(g, "COLIDE E: "+world.getEntityManager().getPlayer().checkEntityCollisions(world.getEntityManager().getPlayer().getxMove(), world.getEntityManager().getPlayer().getyMove()), 10, 48, false, Color.WHITE, Assets.font15);
            Text.drawString(g, "COLIDE T: "+world.getEntityManager().getPlayer().collisionWithTile(x+ (int) world.getEntityManager().getPlayer().getxMove(),  y+ (int) world.getEntityManager().getPlayer().getyMove()), 10, 60, false, Color.WHITE, Assets.font15);
            if(uuid != null) Text.drawString(g, "UUID: "+uuid.toString(), 10, 72, false, Color.WHITE, Assets.font15);
        }
        Text.drawString(g, "You died.", 220, 300,false, Color.RED, Assets.font50);
    }
}
