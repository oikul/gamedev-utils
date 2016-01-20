package utils;

import java.awt.Graphics2D;
import java.awt.Image;

public class Item {
	
	private String name;
	private Image sprite;
	private Type type;
	private int stat;
	
	public static enum Type{
		WEAPON, FOOD, ARMOUR_HELM, ARMOUR_CHEST, ARMOUR_LEGS, ARMOUR_FEET, ARMOUR_GLOVES, TRINKET
	}
	
	public Item(String name, String imagePath, Type type) {
		setName(name);
		setSprite(imagePath);
		setType(type);
	}
	
	public void update(){
		
	}

	public void draw(Graphics2D g, int xPos, int yPos){
		g.drawImage(sprite, xPos, yPos, null);
	}
	
	public void use(Entity target){
		switch(type){
		case FOOD:
			target.heal(stat);
			effect(target);
			break;
		case WEAPON:
			target.inflictDamage(stat);
			effect(target);
			break;
		case TRINKET:
			effect(target);
			break;
		case ARMOUR_HELM:
			effect(target);
			break;
		case ARMOUR_CHEST:
			effect(target);
			break;
		case ARMOUR_GLOVES:
			effect(target);
			break;
		case ARMOUR_LEGS:
			effect(target);
			break;
		case ARMOUR_FEET:
			effect(target);
			break;
		default:
			break;
		}
	}
	
	public void effect(Entity target){
		
	}
	
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
