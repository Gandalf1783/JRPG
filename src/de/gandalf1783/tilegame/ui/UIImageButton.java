package de.gandalf1783.tilegame.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject {

	private BufferedImage[] images;
	private ClickListener clicker;
	
	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}

	public UIImageButton(float x, float y, int width, int height, BufferedImage image, ClickListener clicker, boolean enableHover) {
		super(x, y, width, height);
		this.images = new BufferedImage[1];
		this.images[0] = image;
		this.clicker = clicker;
		this.enableHover = enableHover;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		if(enableHover) {
			if(hovering)
				g.drawImage(images[1], (int) x, (int) y, width, height, null);
			else
				g.drawImage(images[0], (int) x, (int) y, width, height, null);
		} else {
			g.drawImage(images[0], (int) x, (int) y, width, height, null);
		}

	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}
