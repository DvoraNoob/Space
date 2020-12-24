package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.graficos.UI;
import com.dvoragames.main.Game;

public class LifeAdd extends Entity{
	
	public static BufferedImage lifeIcon = Game.spritesheet.getSprite(32, 0, 16, 16);
	
	public LifeAdd(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick(){
		y+=speed;
		
		if(y >= Game.HEIGHT) {
			Game.entities.remove(this);
			return;
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(Entity.isColidding(this, e)) {
					if(Player.life < 3) {
						Game.entities.remove(this);
						Player.life++;
					}else if(Player.life >= 3) {
						Game.entities.remove(this);
						Game.score+=10;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(lifeIcon, getX(), getY(), 7, 7, null);
	}
	

}
