package com.dvoragames.world;

import java.awt.Graphics;
import java.util.ArrayList;

import com.dvoragames.entities.Entity;
import com.dvoragames.entities.Player;
import com.dvoragames.graficos.UI;
import com.dvoragames.main.Game;
import com.dvoragames.main.Spawn;

public class World {
	
	public World(){
	}
	
	public static void restartGame(){
		Spawn.targetTime = 120;
		Game.score = 0;
		Player.life = 3;
		UI.minutes = 0;
		UI.seconds = 0;
		Game.entities.clear();
		Game.entities = new ArrayList<Entity>();
		Game.entities.add(Game.player);
		Game.Gamestate = "NORMAL";
		return;
	}
	
	public void render(Graphics g){

	}
	
}
