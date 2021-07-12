package de.gandalf1783.tilegame.states;

import java.awt.Graphics;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.ui.UIImageButton;
import de.gandalf1783.tilegame.ui.UIManager;
import de.gandalf1783.tilegame.discord.DiscordPresence;

public class MenuState extends State {

	private UIManager uiManager;

	public MenuState(Handler handler) {
		super(handler);
		System.out.println("MENU!");
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		DiscordPresence.init();


		uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.btn_start, () -> {

			handler.getMouseManager().setUIManager(null);
			State.setState(handler.getGame().gameState);

			DiscordPresence.updatePresence("Testing new Features...", "Singleplayer");
		}));

		if(MultiplayerGameState.isServerAvailable()) {
			uiManager.addObject(new UIImageButton(350, 200, 128, 64, Assets.btn_start, () -> {

				handler.getGame().multiplayerGameState = new MultiplayerGameState(handler);

				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().multiplayerGameState);
			}));
		}

		DiscordPresence.updatePresence("Waiting in Menu", "");
	}

	@Override
	public void tick() {
		uiManager.tick();
		
		// Temporarily just go directly to the GameState, skip the menu state!
		//handler.getMouseManager().setUIManager(null);
		//State.setState(handler.getGame().gameState);
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
	}

}
