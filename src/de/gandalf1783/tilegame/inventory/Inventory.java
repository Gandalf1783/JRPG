package de.gandalf1783.tilegame.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.items.Item;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.objects.BasicRequest;

public class Inventory {

	private Handler handler;
	private boolean active = false;
	private ArrayList<Item> inventoryItems;
	
	private int invX = 64, invY = 48,
			invWidth = 512, invHeight = 384,
			invListCenterX = invX + 171,
			invListCenterY = invY + invHeight / 2 + 5,
			invListSpacing = 30;
	
	private int invImageX = 452, invImageY = 82,
			invImageWidth = 64, invImageHeight = 64;
	
	private int invCountX = 484, invCountY = 172;
	
	private int selectedItem = 0;
	
	public Inventory(Handler handler){
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();
	}
	
	public void tick(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
			active = !active;
		if(!active)
			return;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedItem--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedItem++;
		
		if(selectedItem < 0)
			selectedItem = 0;
		else if(selectedItem >= inventoryItems.size())
			selectedItem = 0;
	}
	
	public void render(Graphics g){
		if(!active)
			return;
		
		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);
		
		int len = inventoryItems.size();
		if(len == 0)
			return;
		
		for(int i = -5;i < 6;i++){
			if(selectedItem + i < 0 || selectedItem + i >= len)
				continue;
			if(i == 0){
				Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
						invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);
			}else{
				Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX, 
						invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
			}
		}
		
		Item item = inventoryItems.get(selectedItem);
		g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
		Text.drawString(g, item.getCount()+"", invCountX, invCountY, true, Color.WHITE, Assets.font28);
	}
	
	// Inventory methods
	
	public void addItem(Item item){
		System.out.println("Trying to add "+item.getCount()+"x of "+ item.getName());

		for(Item i : inventoryItems) {
			if(i.getId() == item.getId() && i.getName().equalsIgnoreCase(item.getName())) {
				i.setCount(i.getCount() + item.getCount());
				return;
			}
		}
		inventoryItems.add(item);
	}

	public void dropItem() {

		if (inventoryItems.size() <= selectedItem) {
			return;
		}

		if (MultiplayerGameState.isConnected()) {
			BasicRequest request = new BasicRequest();
			request.text = "DROP";
			request.data = selectedItem + "";
			System.out.println("SELECTED ITEM: " + selectedItem);
			MultiplayerGameState.getClient().sendTCP(request);
		} else {
			int count = inventoryItems.get(selectedItem).getCount();
			if (count <= 0) {
				inventoryItems.remove(selectedItem);
				return;
			}

			inventoryItems.get(selectedItem).setCount(count - 1);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
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

	public Item getSelectedItem() {
		return inventoryItems.get(selectedItem);
	}

	public int getSelectedIndex() {
		return selectedItem;
	}
	public int getInvSize() {
		return inventoryItems.size();
	}
}
