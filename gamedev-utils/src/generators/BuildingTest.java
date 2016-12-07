package generators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import handlers.InputHandler;

public class BuildingTest extends JFrame {

	private static final long serialVersionUID = 1L;
	protected boolean running = false;
	private BufferedImage offimage;
	private Graphics graphics;
	private Point mouseLocation;

	private BuildingGenerator builder;

	public static int width, height, tileSize;
	public static float XOffset, YOffset;
	public static Random random;

	public static InputHandler input;

	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public void run() {
		initialise();

		while (running) {
			update();
			if (running) {
				draw();
			}

		}
		this.dispose();

	}

	public void initialise() {

		running = true;

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.width;
		height = dim.height;

		input = new InputHandler(this);

		this.setTitle("");
		this.setSize(dim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		device.setFullScreenWindow(this);
		this.setVisible(true);

		offimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics = this.getGraphics();
		random = new Random();

		builder = new BuildingGenerator(random.nextLong());
		tileSize = 16;
		mouseLocation = new Point(0, 0);

	}

	/**
	 * Move the image on the screen
	 */
	private void moveScreen() {

		// Get the new mouse point
		Point mouseDiff = input.getMousePositionRelativeToComponent();
		// Distance between the mouse points in tiles
		XOffset += (mouseDiff.x - mouseLocation.x);
		YOffset += (mouseDiff.y - mouseLocation.y);

	}

	/**
	 * Zoom function
	 */
	private void zoom() {
		// Puts the current mouse location in the top left corner then resizes
		// and moves back.
		// Keeps the mouse in the same place when resizing
		// Zoom out
		if (input.getMouseWheelDown() && tileSize != 4) {
			XOffset -= mouseLocation.x;
			YOffset -= mouseLocation.y;
			tileSize /= 2;
			XOffset /= 2;
			YOffset /= 2;
			XOffset += mouseLocation.x;
			YOffset += mouseLocation.y;
			BuildingTest.input.stopMouseWheel();
		}
		// Zoom in
		if (input.getMouseWheelUp() && tileSize != 256) {
			XOffset -= mouseLocation.x;
			YOffset -= mouseLocation.y;
			tileSize *= 2;
			XOffset *= 2;
			YOffset *= 2;
			XOffset += mouseLocation.x;
			YOffset += mouseLocation.y;
			BuildingTest.input.stopMouseWheel();
		}
	}

	public void update() {
		// Quit
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			running = false;
			input.artificialKeyReleased(KeyEvent.VK_ESCAPE);
		} else {
			// Reset image location
			if (input.isKeyDown(KeyEvent.VK_R)) {
				XOffset = 0;
				YOffset = 0;
			}
			if (input.isMouseDown(MouseEvent.BUTTON2)) {
				moveScreen();
			} else if (input.hasMouseWheelMoved()) {
				zoom();
			}
			if (input.isKeyDown(KeyEvent.VK_UP)) {
				YOffset--;
			}
			if (input.isKeyDown(KeyEvent.VK_DOWN)) {
				YOffset++;
			}
			if (input.isKeyDown(KeyEvent.VK_LEFT)) {
				XOffset--;
			}
			if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
				XOffset++;
			}
			if(input.isKeyDown(KeyEvent.VK_R)){
				builder = new BuildingGenerator(random.nextLong());
				input.artificialKeyReleased(KeyEvent.VK_R);
			}

			// Gets the new mouse location
			mouseLocation = input.getMousePositionRelativeToComponent();
			// Updates the builder
			builder.update();
			// Stops the mouse wheel
			input.stopMouseWheel();

		}
	}

	private void draw() {

		Graphics g = offimage.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		builder.draw(g);

		graphics.drawImage(offimage, 0, 0, width, height, null);
	}

	public static void main(String[] args) {
		BuildingTest main = new BuildingTest();
		main.run();
	}

}
