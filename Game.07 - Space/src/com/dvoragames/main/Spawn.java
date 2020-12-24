package com.dvoragames.main;

import com.dvoragames.entities.Enemy;
import com.dvoragames.entities.Entity;
import com.dvoragames.entities.LifeAdd;

public class Spawn {
	
	public static int targetTime = 120;
	public int curTime = 0;
	 
	public int targetTime2 = 240;
	public int curTime2 = 0;
	
	public static int size;

	public void tick() {
		curTime++;
		curTime2++;
		
		if(curTime == targetTime) {
			curTime = 0;
			targetTime--;
			Game.bgSpeed+=0.1;
			if(targetTime <=30) {
				targetTime = 30;
			}
			
			if(Game.bgSpeed == 5) {
				Game.bgSpeed = 5;
			}
			int yy = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH-16);
			Enemy enemy = new Enemy(xx,yy,16,16,1,null);
			Game.entities.add(enemy);
			
		}
		
		if(curTime2 == targetTime2) {
			curTime2 = 0;
			targetTime2 = Entity.rand.nextInt(1000);

			int yy = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH-16);
			LifeAdd lifeadd = new LifeAdd(xx,yy,16,16,1,null);
			Game.entities.add(lifeadd);
			
		}

	}

}
