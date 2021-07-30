package de.gandalf1783.tilegame.states;

import java.awt.*;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.world.Generation;
import de.gandalf1783.tilegame.world.World;

public class GameState extends State {
	
	private World world;
	private Boolean displayDebug = false;
	private boolean firstTick = true;

	public GameState(Handler handler){
		super(handler);


	}

	private void init() {
		world = new World(handler);
		world.setSeed(Generation.SEED);
		world.getNewMap();

		handler.setWorld(world);
	}

	@Override
	public void tick() {
		if(firstTick) {
			firstTick = false;
			init();
		}
		if(handler.getKeyManager().debug) {
			try {
				displayDebug = !displayDebug;
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
		}
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);

		if(displayDebug) {
			int x = (int) world.getEntityManager().getPlayer().getX();
			int y = (int) world.getEntityManager().getPlayer().getY();
			int width = world.getEntityManager().getPlayer().getWidth();
			int height = world.getEntityManager().getPlayer().getHeight();
			Text.drawString(g, "X:  " + x, 10, 12, false, Color.WHITE, Assets.font15);
			Text.drawString(g, "Y:  " + y, 10, 24, false, Color.WHITE, Assets.font15);
			Text.drawString(g, "HP: " +world.getEntityManager().getPlayer().getHealth(), 10, 36, false, Color.WHITE, Assets.font15);
			Text.drawString(g, "COLIDES: "+world.getEntityManager().getPlayer().checkEntityCollisions(world.getEntityManager().getPlayer().getxMove(), world.getEntityManager().getPlayer().getyMove()), 10, 48, false, Color.WHITE, Assets.font15);
			g.drawRect(x  - (int) world.getHandler().getGameCamera().getxOffset(), y - (int) world.getHandler().getGameCamera().getyOffset(), width, height );
		}
	}

}
