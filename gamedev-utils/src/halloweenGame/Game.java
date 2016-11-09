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

	private Vector2f playerLoc;
	private int playerSpeed;
	private int enemySpeed;
	private Vector2f[] enemies;
	private Vector2f[] enemiesMomentum;
	private Vector2f[] movingTo;
	private Vector2f[] movingAway;

	public Game() {
		int numofenemies = 10;
		enemies = new Vector2f[numofenemies];
		enemiesMomentum = new Vector2f[numofenemies];
		movingTo = new Vector2f[numofenemies];
		movingAway = new Vector2f[numofenemies];
		playerLoc = new Vector2f(Main.width / 2, Main.height / 2);
		Random rad = new Random();

		for (int x = 0; x < enemies.length; x++) {
			enemies[x] = new Vector2f(rad.nextInt(Main.width) + 0.0000000001f,
					rad.nextInt(Main.height) + 0.0000000001f);
			movingTo[x] = new Vector2f();
			movingAway[x] = new Vector2f();
			enemiesMomentum[x] = new Vector2f();
		}

		playerSpeed = 100;
		enemySpeed = 90;
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
			Vector2f.sub(mouseLocation, enemies[x], movingTo[x]);

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
				movingTo[x].scale(magnitude);

			}
			enemiesMomentum[x].translate(movingTo[x].x, movingTo[x].y);

			enemies[x].translate(enemiesMomentum[x].x, enemiesMomentum[x].y);
			if (enemies[x].x < 0) {
				enemies[x].x = 0;
			} else if (enemies[x].x > Main.width) {
				enemies[x].x = Main.width;
			}
			if (enemies[x].y < 0) {
				enemies[x].y = 0;
			} else if (enemies[x].y > Main.height) {
				enemies[x].y = Main.height;
			}
		}

	}

	public void draw(Graphics g) {

		for (int x = 0; x < enemies.length; x++) {
			g.setColor(Color.red);
			g.fillRect((int) enemies[x].x - 8, (int) enemies[x].y - 8, 16, 16);

		}
		g.setColor(Color.green);
		g.fillRect((int) playerLoc.x - 16, (int) playerLoc.y - 16, 32, 32);

	}

}
