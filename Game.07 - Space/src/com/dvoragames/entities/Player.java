package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.main.Game;

public class Player extends Entity{

	public boolean right,left;
	private double speed = 1.5;
	public static int life = 3;
	
	public boolean isShooting = false;
	
	private int frames = 0, maxFrames = 20,index = 0, maxIndex = 1;
	
	private BufferedImage[] nave;
	private BufferedImage[] flames;
	
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		nave = new BufferedImage[5];
		flames = new BufferedImage[5];
		
		nave[0] = Game.spritesheet.getSprite(0, 0, 16, 16);
		
		for(int i =  0; i < 2; i++) {
			flames[i] = Game.spritesheet.getSprite((i*16), 16, 16, 16);
		}

	}
	
	public void tick(){
		if(right) {
			x+=speed;
		}else if(left){
			x-=speed;
		}
		
		if(x >= Game.WIDTH-16) {
			x = Game.WIDTH-16;
		}else if(x <= 0) {
			x = 0;
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		
		if(isShooting) {
			isShooting = false;
			int xx = this.getX()+7;
			int yy = this.getY();
			Bullet bullet = new Bullet(xx,yy,3,3,1,null);
			Game.entities.add(bullet);
		}
		
		if(life <= 0) {
			Game.Gamestate = "GAME_OVER";
		}else if(life >=3) {
			life = 3;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(nave[0], getX(), getY(), null);
		g.drawImage(flames[index], getX(), getY()+13, null);
	}


}
