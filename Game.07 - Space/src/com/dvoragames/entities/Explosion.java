package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.main.Game;

public class Explosion extends Entity{
	
	private int frames = 0, maxFrames = 3,index = 0, maxIndex = 2;
	
	private BufferedImage[] explodeSprite;
	
	public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		explodeSprite = new BufferedImage[3];
		
		for(int i = 0; i<3; i++) {
			explodeSprite[i] = Game.spritesheet.getSprite(16 + (i*16), 32, 16, 16);
		}
	}

	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
				Game.entities.remove(this);
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(explodeSprite[index], getX(), getY(), null);
	}
}
