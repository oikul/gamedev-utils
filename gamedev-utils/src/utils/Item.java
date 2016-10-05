package utils;

import java.awt.Graphics2D;
import java.awt.Image;

import entities.Entity;
import handlers.ResourceLoader;

public abstract class Item {
	
	private String name;
	private Image sprite;
	private int value;
	
	public Item(String name, String imagePath, int value) {
		setName(name);
		setSprite(imagePath);
		setValue(value);
	}
	
	public abstract void update();

	public void draw(Graphics2D g, int xPos, int yPos){
		g.drawImage(sprite, xPos, yPos, null);
	}
	
	public abstract void use(Entity target);
	
	public abstract void effect(Entity target);
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(String imagePath) {
		this.sprite = ResourceLoader.getImage(imagePath);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
