package utils;

import java.awt.Graphics2D;
import java.awt.Image;

public class Entity {

	protected Image[][] sprites;
	protected String name;
	protected int currentISprite, currentJSprite, xPos, yPos, width, height, totalHealth, currentHealth;
	protected boolean isDead = false;
	protected Direction direction;
	
	protected enum Direction{
		UP, LEFT, DOWN, RIGHT
	}
	
	public Entity(String name, String spritePath, int xPos, int yPos, int width, int height){
		setName(name);
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		setSprites(spritePath, width, height);
	}

	public void update(){
		
	}

	public void draw(Graphics2D g){
		if(!isDead){
			g.drawImage(sprites[currentISprite][currentJSprite], xPos, yPos, width, height, null);
		}
	}
	
	public void inflictDamage(int amount){
		currentHealth -= amount;
		if(currentHealth < 0){
			isDead = true;
		}
	}
	
	public void heal(int amount){
		if(currentHealth + amount > totalHealth){
			currentHealth = totalHealth;
		}else{
			currentHealth += amount;
		}
	}
	
	public void moveUp(double amount){
		yPos += amount;
	}
	
	public void moveLeft(double amount){
		xPos += amount;
	}
	
	public void moveDown(double amount){
		yPos -= amount;
	}
	
	public void moveRight(double amount){
		xPos -= amount;
	}
	
	public void moveUL(double amount){
		xPos += amount;
		yPos += amount;
	}
	
	public void moveUR(double amount){
		xPos -= amount;
		yPos += amount;
	}
	
	public void moveDL(double amount){
		xPos += amount;
		yPos -= amount;
	}
	
	public void moveDR(double amount){
		xPos -= amount;
		yPos -= amount;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public void getFrame(){
		switch(direction){
		case UP:
			break;
		case LEFT:
			break;
		case DOWN:
			break;
		case RIGHT:
			break;
		default:
			break;
		}
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Image[][] getSprites(){
		return sprites;
	}

	public void setSprites(String spritePath, int width, int height){
		sprites = ResourceLoader.getPlayerSprites(spritePath, width, height);
	}

	public int getXPos(){
		return xPos;
	}

	public void setXPos(int xPos){
		this.xPos = xPos;
	}

	public int getYPos(){
		return yPos;
	}

	public void setYPos(int yPos){
		this.yPos = yPos;
	}
	
	public int getWidth(){
		return width;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getHeight(){
		return height;
	}

	public void setHeight(int height){
		this.height = height;
	}
	
	public boolean isDead(){
		return isDead;
	}
	
	public void setIsDead(boolean isDead){
		this.isDead = isDead;
	}
}
