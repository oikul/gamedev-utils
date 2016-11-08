package halloweenGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import handlers.InputHandler;

/**
 * @author Reece Meek The start of the map builder engine
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	// The frame the engine runs on
	private static JFrame frame;
	// The offset of the image on the frame, + = -> & vv (right and down)
	// add to values to give locations of objects
	public static float XOffset, YOffset;
	// The width height and tilesize
	public static int width, height;
	// The dimension of the screen
	public static Dimension screenSize;
	// The input handler
	public static InputHandler input;

	// Boolean if the engine has been closed
	private boolean running = false;
	// The image getting drawn to the frame
	private Image BufferImage;
	// The images graphics object
	private Graphics g;
	private Game game;
	
	

	/**
	 * Main game loop
	 */
	public void run() {

		// Initialise
		init();

		long beforeTime, afterTime, deltaTime = 0;
		// While running
		while (running) {
			beforeTime = System.nanoTime();
			update(deltaTime/1000000000f);

			// If engine has been closed don't bother drawing the image
			if (running) {
				draw();
			}
			afterTime = System.nanoTime();
			deltaTime = afterTime - beforeTime;
		}
		// // Clean up
		close();

	}

	/**
	 * Initialise variables
	 */
	private void init() {

		running = true;
		input = new InputHandler(this);
		// Get the graphics environment
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setUndecorated(true);
		// User chooses which screen to display the frame on
		int deviceIndex = 0;
			// Set the width height and screensize
			width = ge.getScreenDevices()[deviceIndex].getDisplayMode().getWidth();
			height = ge.getScreenDevices()[deviceIndex].getDisplayMode().getHeight();
			screenSize = new Dimension(width, height);

		// Set name, close, size and fullscreen
		this.setTitle("2D Map Maker");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Make the frame visible
		this.setVisible(true);
		// Sets the frame var and the root frame
		frame = this;
		// Creates the image
		BufferImage = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
		// Sets the graphics of the frame
		g = this.getGraphics();
		game = new Game();
		
	}

	/**
	 * Clean up
	 */
	private void close() {
		this.dispose();
	}

	/**
	 * Update
	 */
	private void update(float time) {
		// Quit
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			running = false;
		}
		game.update(time,input.getMousePositionRelativeToComponent());
	}

	/**
	 * Draw
	 */
	private void draw() {
		// Graphics of the buffer image
		Graphics bufferGraphics = BufferImage.getGraphics();
		// Draw here
		// Clear the screen
		bufferGraphics.setColor(Color.black);
		bufferGraphics.fillRect(0, 0, width, height);
		game.draw(bufferGraphics);

		// To here
		g.drawImage(BufferImage, 0, 0, null);
	}

	/**
	 * Static method to return the classes frame
	 * 
	 * @return The frame
	 */
	public static JFrame getFrame() {
		return frame;
	}

	/**
	 * Main
	 * 
	 * @param args
	 *            Any args, wont actually do anything
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
