package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.main.Game;

public class Enemy extends Entity{
	
	private BufferedImage enemy = Game.spritesheet.getSprite(0, 32, 16, 16);

	public int life = 2;
	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, null);
	}
	
	public void tick() {
		y+=speed;
		
		if(y >= Game.HEIGHT) {
			Game.entities.remove(this);
			Player.life--;
			return;
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColidding(this, e)) {
					Game.entities.remove(e);
					life--;
					if(life <= 0){
						Game.score+=10;
						Explosion explosion = new Explosion(x,y,16,16,1,null);
						Game.entities.add(explosion);
						Game.entities.remove(this);
						return;
					}
					break;
				}
			}
			if(e instanceof Player) {
				if(Entity.isColidding(this, e)) {
					Explosion explosion = new Explosion(x,y,16,16,1,null);
					Game.entities.add(explosion);
					Game.entities.remove(this);
					Player.life--;
					break;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(enemy, getX(), getY(),16,16,null);
	}

}
