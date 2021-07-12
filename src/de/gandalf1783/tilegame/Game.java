package de.gandalf1783.tilegame;

import java.awt.*;
import java.awt.image.BufferStrategy;

import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.GameCamera;
import de.gandalf1783.tilegame.input.KeyManager;
import de.gandalf1783.tilegame.input.MouseManager;
import de.gandalf1783.tilegame.display.Display;
import de.gandalf1783.tilegame.states.GameState;
import de.gandalf1783.tilegame.states.MenuState;
import de.gandalf1783.tilegame.states.State;
import de.gandalf1783.tilegame.threads.AudioThread;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	public State gameState;
	public State menuState;
	public State multiplayerGameState;
	public State errorState;

	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;

	private static int fps = 0;

	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();

		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);

		State.setState(menuState);

		AudioThread audio = new AudioThread();
		Thread t1 = new Thread(audio);
		t1.run();
	}
	
	private void tick(){
		keyManager.tick();
		
		if(State.getState() != null)
			State.getState().tick();
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		g.setColor(Color.black);
		g.drawRect(0,0,width,height);
		if(State.getState() != null)
			State.getState().render(g);
		
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		init();
		int updates = 0;
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				fps = updates;
				updates = 0;
			}
		}
		
		stop();
		
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public GameCamera getGameCamera(){
		return gameCamera;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int getFPS() {
		return fps;
	}


	public void resetGame() {
		handler.getMouseManager().setUIManager(null);

		gameCamera = new GameCamera(handler, 0, 0);

		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
	}
}











