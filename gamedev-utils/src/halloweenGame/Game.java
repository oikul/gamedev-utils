package halloweenGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Vector2f;

import handlers.MathHandler;

public class Game {

	private Vector2f playerLoc;
	private int playerSpeed,enemySpeed,size;
	private Vector2f[] enemies;
	private Vector2f[] enemiesMomentum;
	private Vector2f[] movingTo;
	private Vector2f[] movingAway;

	public Game() {
		int numofenemies = 1000;
		enemies = new Vector2f[numofenemies];
		enemiesMomentum = new Vector2f[numofenemies];
		movingTo = new Vector2f[numofenemies];
		movingAway = new Vector2f[numofenemies];
		playerLoc = new Vector2f(Main.width / 2, Main.height / 2);
		Random rad = new Random();

		playerSpeed = 100;
		enemySpeed = 90;
		size = 100;
		for (int x = 0; x < enemies.length; x++) {
			enemies[x] = new Vector2f(rad.nextInt(size)+Main.width/2-size/2,
					rad.nextInt(size)+Main.height/2-size/2);
			movingTo[x] = new Vector2f();
			movingAway[x] = new Vector2f();
			enemiesMomentum[x] = new Vector2f(Main.width/2-enemies[x].x+0.0000001f, Main.height/2-enemies[x].y+0.0000001f);
			float length = enemiesMomentum[x].length();
			enemiesMomentum[x].normalise();
			enemiesMomentum[x].scale(length/20);
			Matrix2f rotate = new Matrix2f();
			rotate.setZero();
			rotate.m01 = -1;
			rotate.m10 = 1;
			Matrix2f.transform(rotate, enemiesMomentum[x], enemiesMomentum[x]);
			
		}

	}

	public void update(float time, Vector2f mouseLocation) {
		if (Main.input.isKeyDown(KeyEvent.VK_W)) {
			if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				playerLoc.x += (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y -= (playerSpeed * time) / Math.sqrt(2);
			} else if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				playerLoc.x -= (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y -= (playerSpeed * time) / Math.sqrt(2);
			} else {
				playerLoc.y -= playerSpeed * time;
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_S)) {
			if (Main.input.isKeyDown(KeyEvent.VK_D)) {
				playerLoc.x += (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y += (playerSpeed * time) / Math.sqrt(2);
			} else if (Main.input.isKeyDown(KeyEvent.VK_A)) {
				playerLoc.x -= (playerSpeed * time) / Math.sqrt(2);
				playerLoc.y += (playerSpeed * time) / Math.sqrt(2);
			} else {
				playerLoc.y += playerSpeed * time;
			}
		} else if (Main.input.isKeyDown(KeyEvent.VK_D)) {
			playerLoc.x += playerSpeed * time;
		} else if (Main.input.isKeyDown(KeyEvent.VK_A)) {
			playerLoc.x -= playerSpeed * time;
		}
		if (playerLoc.x < 0) {
			playerLoc.x = 0;
		} else if (playerLoc.x > Main.width) {
			playerLoc.x = Main.width;
		}
		if (playerLoc.y < 0) {
			playerLoc.y = 0;
		} else if (playerLoc.y > Main.height) {
			playerLoc.y = Main.height;
		}

		float magnitude;
		for (int x = 0; x < enemies.length; x++) {

			magnitude = enemySpeed * time;

			// the vector away from the green square
//			Vector2f.sub(enemies[x], playerLoc, movingAway[x]);
			// the vector towards the mouse
			Vector2f.sub(playerLoc, enemies[x], movingTo[x]);

			float forceTo, forceAway = 0;
			forceTo = movingTo[x].length();
//			forceAway = movingAway[x].length();

//			if (movingAway[x].length() != 0) {
//			movingAway[x].normalise();
//			}
			if (movingTo[x].length() != 0) {
			movingTo[x].normalise();
			}

			movingAway[x].scale((float) Math.log10(forceTo));
//			movingTo[x].scale((float) Math.log10(forceAway));

//			Vector2f.add(movingTo[x], movingAway[x], movingTo[x]);

			if (movingTo[x].length() != 0) {

				movingTo[x].normalise();
				movingTo[x].scale(magnitude/10f);

			}
			
			enemiesMomentum[x].translate(movingTo[x].x, movingTo[x].y);

			enemies[x].translate(enemiesMomentum[x].x, enemiesMomentum[x].y);
			
//			if (enemies[x].x < playerLoc.x - 2 * size) {
//				enemies[x].x = playerLoc.x - 2 * size;
//			} else if (enemies[x].x > playerLoc.x + 2 * size) {
//				enemies[x].x = playerLoc.x + 2 * size;
//			}
//			if (enemies[x].y < playerLoc.y - 2 * size) {
//				enemies[x].y = playerLoc.y - 2 * size;
//			} else if (enemies[x].y > playerLoc.y + 2 * size) {
//				enemies[x].y = playerLoc.y + 2 * size;
//			}
		}

	}

	public void draw(Graphics g) {

		for (int x = 0; x < enemies.length; x++) {
			g.setColor(Color.blue);
			if(x % 2 == 0)g.setColor(Color.darkGray);
			g.fillRect((int) enemies[x].x - 2, (int) enemies[x].y - 2, 4, 4);

		}
		g.setColor(Color.green);
//		g.fillRect((int) playerLoc.x - 16, (int) playerLoc.y - 16, 32, 32);
		g.drawRect((Main.width)/2-2*size , (Main.height)/2-2*size, size*4, size*4);

	}

}
