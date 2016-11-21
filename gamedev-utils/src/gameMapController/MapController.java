package gameMapController;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Entity;
import entities.Player;

public class MapController {

	private ArrayList<Map> levels;
	private ArrayList<Entity> enemies;
	private Player player;
	
	public MapController(){
		
	}
	
	private void checkCollisions(){
		
		Rectangle playerBox = player.getHitBox();
		for(Entity enemy: enemies){
			if(playerBox.intersects(enemy.getHitBox())){
				player.collided();
				break;
				
			}
		}
	}
	
	public void update(float time){
		checkCollisions();
	}
	
	public void draw(Graphics g){
		
	}
	
}
