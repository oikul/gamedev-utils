package entities;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.lwjgl.util.vector.Vector2f;

public abstract class Entity {

	protected boolean shouldAttack;
	protected int health, maxHealth, attack, defence, currentX, currentY;
	protected float xLocation, yLocation, speed;
	protected long animationTimer, changeTimer, attackCooldown;
	protected String name;
	protected Point size;
	protected Vector2f attackVector;
	protected Rectangle hitBox;
	protected BufferedImage[][] sprite;

	public Entity(int xLocation, int yLocation, int maxHealth, int attack, int defence, float speed,
			BufferedImage[][] sprite) {
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.maxHealth = this.health = maxHealth;
		this.attack = attack;
		this.defence = defence;
		this.speed = speed;
		this.sprite = sprite;
		currentX = currentY = 0;
		shouldAttack = false;
		attackVector = new Vector2f(0, 0);
		this.size = new Point(sprite[0][0].getWidth(), sprite[0][0].getHeight());
		changeTimer = 500;
		attackCooldown = System.currentTimeMillis() + changeTimer;
		animationTimer = System.currentTimeMillis() + changeTimer;
		hitBox = new Rectangle(xLocation - size.x / 2, yLocation - size.y / 2, size.x, size.y);
	}

	public float getX() {
		return xLocation;
	}

	public float getY() {
		return yLocation;
	}

	public Point getSize() {
		return size;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void kill() {
		health = 0;
	}

	public void update(float time) {

		if (System.currentTimeMillis() > animationTimer) {
			animationTimer += changeTimer;
			currentY++;
			if (currentY > 2) {
				currentY = 0;
			}
		}
	}

	public BufferedImage draw() {
		return sprite[currentX][currentY];

	}

	public boolean isAlive() {
		return health > 0;
	}

}