package de.gandalf1783.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Animation {

	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	private Boolean hasPlayed = false;
	private Boolean repeat;

	public Animation(int speed, BufferedImage[] frames, Boolean repeat){
		this.speed = speed;
		this.frames = frames;
		this.repeat = repeat;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void tick(){
		if(hasPlayed) {
			return;
		}

		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length) {
				index = 0;
				hasPlayed = true;

				if (repeat) {
					hasPlayed = false;
				}
			}
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}

	public Boolean getHasPlayed() {
		return hasPlayed;
	}
}
