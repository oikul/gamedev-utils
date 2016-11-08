package halloweenGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import handlers.MathHandler;

public class Game {

	private Point2D.Double playerLoc;
	private int playerSpeed;
	private int enemySpeed;
	private Point2D.Double[] enemies;
	private Point2D.Double[] enemyMovements;

	public Game() {
		int numofenemies = 1000;
		enemies = new Point2D.Double[numofenemies];
		enemyMovements = new Point2D.Double[numofenemies];
		playerLoc = new Point2D.Double(Main.width / 2, Main.height / 2);
		Random rad = new Random();
		
		for(int x = 0; x < enemies.length; x++){
			enemies[x] = new Point2D.Double(rad.nextInt(Main.width), rad.nextInt(Main.height));
		}
		
		playerSpeed = 100;
		enemySpeed = 150;
	}

	public void update(float time,Point mouseLocation){
		if(Main.input.isKeyDown(KeyEvent.VK_W)){
			if(Main.input.isKeyDown(KeyEvent.VK_D)){
				playerLoc.x += (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y -= (playerSpeed * time) / Math.sqrt(2);
			}else if(Main.input.isKeyDown(KeyEvent.VK_A)){
				playerLoc.x -= (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y -= (playerSpeed * time) / Math.sqrt(2);
			}else{
				playerLoc.y -= playerSpeed * time;
			}
		}else if(Main.input.isKeyDown(KeyEvent.VK_S)){
			if(Main.input.isKeyDown(KeyEvent.VK_D)){
				playerLoc.x += (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y += (playerSpeed * time) / Math.sqrt(2);
			}else if(Main.input.isKeyDown(KeyEvent.VK_A)){
				playerLoc.x -= (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y += (playerSpeed * time) / Math.sqrt(2);
			}else{
				playerLoc.y += playerSpeed * time;
			}
		}else if(Main.input.isKeyDown(KeyEvent.VK_D)){
			playerLoc.x += playerSpeed * time;
		}else if(Main.input.isKeyDown(KeyEvent.VK_A)){
			playerLoc.x -= playerSpeed * time;
		}
		if(playerLoc.x < 0 ){
			playerLoc.x = 0;
		}else if(playerLoc.x > Main.width ){
			playerLoc.x = Main.width;
		}
		if(playerLoc.y< 0 ){
			playerLoc.y = 0;
		}else if(playerLoc.y > Main.height ){
			playerLoc.y = Main.height;
		}

		
		
		Point2D.Double moveAway;
		Point2D.Double moveTo;
		for(int x = 0; x < enemies.length; x++){
			
			moveAway = MathHandler.getPoint2D(playerLoc, enemies[x], 
					enemySpeed * time * Math.max(Math.min((1/Math.log10(enemies[x].distance(playerLoc))),3),1), 0);
			moveTo = MathHandler.getPoint2D(enemies[x], new Point2D.Double(mouseLocation.x,mouseLocation.y), 
					enemySpeed * time * Math.max(Math.min((1/Math.log10(enemies[x].distance(mouseLocation))),3),1), 0);
			
			System.out.println(Math.log10(enemies[x].distance(mouseLocation))+", "+Math.log10(enemies[x].distance(playerLoc)));
			
			moveAway = MathHandler.averageVector(moveAway, moveTo);
			
			enemies[x].setLocation(enemies[x].x + moveAway.x,enemies[x].y + moveAway.y);
			if(enemies[x].x < 0 ){
				enemies[x].x = 0;
			}else if(enemies[x].x > Main.width ){
				enemies[x].x = Main.width;
			}
			if(enemies[x].y< 0 ){
				enemies[x].y = 0;
			}else if(enemies[x].y > Main.height ){
				enemies[x].y = Main.height;
			}
		}
		
	}

	public void draw(Graphics g) {

		g.setColor(Color.red);
		
		for(int x = 0; x < enemies.length; x++){
			g.fillRect((int)enemies[x].x-8, (int)enemies[x].y-8, 16, 16);
			
		}
		
		g.setColor(Color.green);
		g.fillRect((int)playerLoc.x-16, (int)playerLoc.y-16, 32, 32);
		
	}

}
