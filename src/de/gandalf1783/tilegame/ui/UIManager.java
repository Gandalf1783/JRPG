package de.gandalf1783.tilegame.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import de.gandalf1783.tilegame.Handler;

public class UIManager {

	private Handler handler;
	private ArrayList<UIObject> objects;
	
	public UIManager(Handler handler){
		this.handler = handler;
		objects = new ArrayList<>();
	}
	
	public void tick(){
		try {
			for(UIObject o : objects)
				o.tick();
		} catch (ConcurrentModificationException ex) {
		}
	}
	
	public void render(Graphics g){
		try {
			for(UIObject o : objects)
				o.render(g);
		} catch (ConcurrentModificationException ex) {
		}
	}
	
	public void onMouseMove(MouseEvent e){
		try {
			for(UIObject o : objects)
				o.onMouseMove(e);
		} catch (ConcurrentModificationException ex) {
		}
	}
	
	public void onMouseRelease(MouseEvent e){
		try {
			for(UIObject o : objects)
				o.onMouseRelease(e);
		} catch (ConcurrentModificationException ex) {
		}
	}
	
	public void addObject(UIObject o){
		objects.add(o);
	}
	
	public void removeObject(UIObject o){
		objects.remove(o);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
	
}
