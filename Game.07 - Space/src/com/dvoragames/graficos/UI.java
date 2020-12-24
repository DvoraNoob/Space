package com.dvoragames.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.entities.LifeAdd;
import com.dvoragames.entities.Player;
import com.dvoragames.main.Game;

public class UI {
	
	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;
	public static String formatTime;
	
	public BufferedImage deadIcon = Game.spritesheet.getSprite(16, 0, 16, 16);

	public void tick() {
		frames++;
		if(frames == 60) {
			frames = 0;
			seconds++;
			if(seconds == 60) {
				seconds = 0;
				minutes++;
			}
		}
	}

	public void render(Graphics g) {
		
		if(Game.Gamestate == "NORMAL") {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD,20));
			g.drawString("Score: " + Game.score,10, 25);
			
			g.drawImage(deadIcon, Game.WIDTH*Game.SCALE-35, 10, 25, 25, null);
			g.drawImage(deadIcon, Game.WIDTH*Game.SCALE-65, 10, 25, 25, null);
			g.drawImage(deadIcon, Game.WIDTH*Game.SCALE-95, 10, 25, 25, null);
			
			if(Player.life >= 1) {
				g.drawImage(LifeAdd.lifeIcon, Game.WIDTH*Game.SCALE-35, 10, 25, 25, null);
				if(Player.life>=2) {
					g.drawImage(LifeAdd.lifeIcon, Game.WIDTH*Game.SCALE-65, 10, 25, 25, null);
					if(Player.life == 3) {
						g.drawImage(LifeAdd.lifeIcon, Game.WIDTH*Game.SCALE-95, 10, 25, 25, null);
	
					}
				}
			}else if(Game.Gamestate == "MENU") {
				
			}
		}
		
		formatTime = "";
		if(minutes < 10) {
			formatTime+="0"+minutes+":";
		}else {
			formatTime+=minutes+":";
		}
		
		if(seconds < 10) {
			formatTime+="0"+seconds;
		}else {
			formatTime+=seconds;
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD,20));
		g.drawString("Time: " + formatTime,10, 50);
	}
	
}
