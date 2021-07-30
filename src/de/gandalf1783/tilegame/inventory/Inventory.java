package de.gandalf1783.tilegame.inventory;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.items.Item;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.objects.BasicRequest;
import de.gandalf1783.tilegame.states.State;
import de.gandalf1783.tilegame.ui.UIImageButton;
import de.gandalf1783.tilegame.ui.UIManager;

public class Inventory {

	private Handler handler;
	private UIManager uiManager;
	private UIManager oldUiManager;

	private boolean active = false;

	private Item[][] items = new Item[5][3];

	private int invX = 180, invY = 60,
			invWidth = 120, invHeight = 138;

	private int zoomFactor = 3;

	private int selectedSlotX = 0, selectedSlotY = 0;
	private int selectedTab = 0;
	
	public Inventory(Handler handler){
		this.handler = handler;

		uiManager = new UIManager(handler);

		Item.woodItem.setCount(2);
		Item.cactusItem.setCount(2);
		Item.signItem201.setCount(2);

		items[0] = new Item[3];
		items[1] = new Item[3];
		items[2] = new Item[3];
		items[3] = new Item[3];
		items[4] = new Item[3];

		items[0][2] = Item.signItem201;
		items[1][1] = Item.cactusItem;
		items[2][1] = Item.woodItem;

	}

	public void tick(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			if (!active) {
				uiManager = new UIManager(handler);

				oldUiManager = handler.getMouseManager().getUiManager();

				selectedSlotX = -1;
				selectedSlotY = -1;

				rebuildInventoryUiManager();
				handler.getMouseManager().setUIManager(uiManager);
			}
			if(active)
				handler.getMouseManager().setUIManager(oldUiManager);
			active = !active;
		}
		if(!active)
			return;

		uiManager.tick();

		/*
			TODO: Tab Handling!
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			if(selectedTab > 0)
				selectedTab--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			if(selectedTab < 3)
				selectedTab++;
		 */
	}
	
	public void render(Graphics g){
		if(!active)
			return;

		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth*zoomFactor, invHeight*zoomFactor, null);

		g.drawImage(Assets.inventoryActiveTab, invX+101*3, invY+38*3+(selectedTab*18*zoomFactor), 17*3, 19*3, null); // TODO: Active Tab (Click on it by Mouse -> UIObject!)

		if(selectedSlotX >= 0 && selectedSlotY >= 0) {
			g.drawImage(Assets.inventoryActiveItem,invX +(11*zoomFactor)+(17*zoomFactor*selectedSlotX), invY+(44*zoomFactor)+(17*zoomFactor*selectedSlotY),14*zoomFactor, 14*zoomFactor, null);
		}

		uiManager.render(g);
	}


	private void rebuildInventoryUiManager() {
		uiManager.getObjects().clear();

		uiManager.addObject(new UIImageButton(invX+(96*zoomFactor), invY+(0*zoomFactor),  25*zoomFactor, 33*zoomFactor ,Assets.emptyImages, () -> {
			handler.getMouseManager().setUIManager(oldUiManager);
			active = false;
		}));

		uiManager.addObject(new UIImageButton(invX+(72*zoomFactor), invY+(114*zoomFactor),  25*zoomFactor, 33*zoomFactor ,Assets.emptyImages, () -> {
			dropItem();
		}));

		int slotX = 0, slotY = 0;

		for(Item[] i1 : items) {
			for(Item i : i1) {

				int finalSlotX = slotX;
				int finalSlotY = slotY;

				if(i == null) {

					uiManager.addObject(new UIImageButton(invX +(11*zoomFactor)+(17*zoomFactor*slotX), invY+(44*zoomFactor)+(17*zoomFactor*slotY), 12*zoomFactor, 12*zoomFactor, Assets.emptyImage, () -> {

						if(selectedSlotX >= 0 && selectedSlotY >= 0) {

							items[finalSlotX][finalSlotY] = items[selectedSlotX][selectedSlotY];
							items[selectedSlotX][selectedSlotY] = null;

							selectedSlotX = -1;
							selectedSlotY = -1;

							rebuildInventoryUiManager();
							System.out.println("Moving Item");

						}
					}, false));

					slotY++;
					continue;
				}


				uiManager.addObject(new UIImageButton(invX +(11*zoomFactor)+(17*zoomFactor*slotX), invY+(44*zoomFactor)+(17*zoomFactor*slotY), 12*zoomFactor, 12*zoomFactor, i.getTexture(), () -> {

					if(selectedSlotX == finalSlotX && selectedSlotY == finalSlotY) {
						selectedSlotX = -1;
						selectedSlotY = -1;
					} else {
						if(selectedSlotX != -1 && selectedSlotY != -1 ) {
							if(items[selectedSlotX][selectedSlotY] != null) {
								Item itemA = items[selectedSlotX][selectedSlotY];
								Item itemB = items[finalSlotX][finalSlotY];
								items[finalSlotX][finalSlotY] = itemA;
								items[selectedSlotX][selectedSlotY] = itemB;
								selectedSlotX = -1;
								selectedSlotY = -1;
								rebuildInventoryUiManager();
							}
						} else {
							selectedSlotX = finalSlotX;
							selectedSlotY = finalSlotY;
						}
					}

					System.out.println("Selection: "+selectedSlotX+"|"+selectedSlotY);
				}, false));

				slotY++;

			}

			slotY = 0;
			slotX++;

		}
	}

	// Inventory methods
	public void addItem(Item item){
		System.out.println("Trying to add "+item.getCount()+"x of "+ item.getName());
		int indexX = 0, indexY = 0;

		for(Item[] i1 : items) {
			for(Item i : i1) {
				if(i == null) {
					items[indexX][indexY] = item;
					return;
				} else {
					indexY++;
				}
			}
			indexY = 0;
			indexX++;
		}
	}

	public void dropItem() {
		if (State.getState() == handler.getGame().multiplayerGameState) {

			BasicRequest request = new BasicRequest();

			request.text = "DROP";
			request.data = selectedSlotX+"#"+selectedSlotY;

			System.out.println("SELECTED ITEM: " + selectedSlotX+"#"+selectedSlotY);

			MultiplayerGameState.getClient().sendTCP(request);

		} else {

			int count = items[selectedSlotX][selectedSlotY].getCount();

			if (count-1 <= 0) {
				items[selectedSlotX][selectedSlotY] = null;
				return;
			}

			items[selectedSlotX][selectedSlotY].setCount(count - 1);
		}
	}

	// GETTERS SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}
}
