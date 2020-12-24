package com.dvoragames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dvoragames.entities.Entity;
import com.dvoragames.entities.Player;
import com.dvoragames.graficos.Spritesheet;
import com.dvoragames.graficos.UI;
import com.dvoragames.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 120;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;
	
	private BufferedImage image;
	
	public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	public Spawn spawn;
	
	public BufferedImage BG;
	public BufferedImage BG2;
	
	public double bgY = 0;
	public double bgY2 = -160;
	public static double bgSpeed = 0.5;
	
	public static int score = 0;

	public UI ui;
	
	public static String Gamestate = "MENU";
	private boolean showMessageGameOver = true;
	private boolean showMessageStart = true;
	private int framesGameOver = 0;
	private int framesStart = 0;
	private boolean restartGame = false;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(Game.WIDTH/2-8,Game.HEIGHT-20,16,16,1,null);
		///world = new World();
		ui = new UI();
		spawn = new Spawn();
		
		try {
			BG = ImageIO.read(getClass().getResource("/bg.png"));
			BG2 = ImageIO.read(getClass().getResource("/bg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		entities.add(player);
		
	}
	
	public void initFrame(){
		frame = new JFrame("Space Traveler");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		if(Gamestate == "NORMAL") {
			spawn.tick();
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			ui.tick();
	
			bgY+=bgSpeed;
			if(bgY-160 >= 0) {
				bgY = -160;
			}
			
			bgY2+=bgSpeed;
			if(bgY2-160 >= 0) {
				bgY2 = -160;
			}
		}else if (Gamestate == "GAME_OVER") {
			framesGameOver++;
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(showMessageGameOver) {
					showMessageGameOver = false;
				}else {
					showMessageGameOver = true;
				}
			}
		}else if (Gamestate == "MENU") {
			framesStart++;
			if(framesStart == 30) {
				framesStart = 0;
				if(showMessageStart) {
					showMessageStart = false;
				}else {
					showMessageStart = true;
				}
			}
		}
	}
	


	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		g.drawImage(BG, 0, (int) bgY, null);
		g.drawImage(BG2, 0, (int) bgY2, null);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		//world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		
		if(Gamestate == "NORMAL") {
			ui.render(g);
		}else if(Gamestate == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(new Font("arial", Font.CENTER_BASELINE, 50));
			g2.setColor(Color.white);
			g2.drawString("GAME OVER", WIDTH*SCALE/2-150, 100);
			g2.setFont(new Font("arial", Font.CENTER_BASELINE, 20));
			g2.setColor(Color.white);
			g2.drawString("SCORE: " + score, WIDTH*SCALE/2-150, 200);
			g2.setFont(new Font("arial", Font.CENTER_BASELINE, 20));
			g2.setColor(Color.white);
			g2.drawString("TIME: " + UI.formatTime, WIDTH*SCALE/2-150, 230);
			g2.setFont(new Font("arial", Font.CENTER_BASELINE, 20));
			g2.setColor(Color.white);
			if(showMessageGameOver) {
				g2.drawString("Press Enter to restart", WIDTH*SCALE/2-105, HEIGHT*SCALE/1-20);
			}
		}else if(Gamestate == "MENU") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(new Font("arial", Font.CENTER_BASELINE, 50));
			g2.setColor(Color.white);
			g2.drawString("Space Traveler", WIDTH*SCALE/2-175, 100);
			g2.setFont(new Font("arial", Font.CENTER_BASELINE, 20));
			if(showMessageStart) {
				g2.drawString("Press Enter to Start", WIDTH*SCALE/2-105, HEIGHT*SCALE/1-20);
			}
		}
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || 
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || 
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(Gamestate == "GAME_OVER" || Gamestate == "MENU") {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				World.restartGame();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || 
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || 
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(Gamestate == "NORMAL") {
			player.isShooting = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
