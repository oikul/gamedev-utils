package mapMaker2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	public static int width, height, tileSize;
	// The dimension of the screen
	public static Dimension screenSize;
	// The input handler
	public static InputHandler input;
	// A boolean to determine if the screen should be forced to the front
	public static boolean forceFront;

	// Boolean if the engine has been closed
	private boolean running = false;
	// The location of the mouse
	private Point mouseLocation;
	// The image getting drawn to the frame
	private Image BufferImage;
	// The images graphics object
	private Graphics g;
	// The buildmap class
	private BuildMap builder;
	// The ui class
	private UI ui;
	// The saving class
	private SaveMap save;

	/**
	 * Sets the listeners for the frame
	 */
	private void addListeners() {
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// Keep the current frame in the screens foreground
				if (forceFront) {
					Main.frame.setState(Frame.NORMAL);
				}
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
	}

	/**
	 * Main game loop
	 */
	public void run() {
		
		// Initialise
		init();
		
		// While running
		while (running) {
			update();

			// If engine has been closed don't bother drawing the image
			if (running) {
				draw();
			}
		}
//		// Clean up
		close();

	}

	/**
	 * Initialise variables
	 */
	private void init() {

		addListeners();
		running = true;
		forceFront = false;
		input = new InputHandler(this);
		// Get the graphics environment
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// Sets all the current screens being used
		Settings.gds = ge.getScreenDevices();
		this.setUndecorated(true);
		// User chooses which screen to display the frame on
		int deviceIndex = chooseDevice(Settings.gds);

		// Sets the frame to display on the chosen device or exits
		try {
			Settings.gds[deviceIndex].setFullScreenWindow(this);
			// Set the width height and screensize
			width = Settings.gds[deviceIndex].getDisplayMode().getWidth();
			height = Settings.gds[deviceIndex].getDisplayMode().getHeight();
			screenSize = new Dimension(width, height);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.exit(0);
		}

		// Set name, close, size and fullscreen
		this.setTitle("2D Map Maker");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(width, height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// save class
		save = new SaveMap();
		// Make the frame visible
		this.setVisible(true);
		// Sets the frame var and the root frame
		frame = this;
		JOptionPane.setRootFrame(frame);
		tileSize = 16;
		// Creates the image
		BufferImage = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
		// Sets the graphics of the frame
		g = this.getGraphics();
		ui = new UI();
		// Build map sizes in tiles 16px atm
		builder = new BuildMap(100, 100, ui);
		mouseLocation = new Point(0, 0);
	}

	/**
	 * Clean up
	 */
	private void close() {
		builder.close();
		this.dispose();
	}

	/**
	 * User chooses screen to use
	 * 
	 * @param gds
	 *            The screens
	 * @return The screen to use index
	 */
	private int chooseDevice(GraphicsDevice[] gds) {

		// Dynamic jOption
		String[] possibilities = new String[gds.length];
		for (int x = 0; x < gds.length; x++) {

			possibilities[x] = x + ". Width:" + gds[x].getDisplayMode().getWidth() + ", Height:"
					+ gds[x].getDisplayMode().getHeight();

		}
		// The chosen screen
		String choice = (String) multipleChoiceDialog(
				"Choose which display you want the screen displayed on. \nBigger screens are normaly better.", null,
				"Display", possibilities, JOptionPane.QUESTION_MESSAGE);
		if (choice == null) {
			choice = "-1";
		} else {
			choice = "" + choice.charAt(0);
		}
		return Integer.parseInt(choice);
	}

	/**
	 * Move the image on the screen
	 */
	private void moveScreen() {

		// Get the new mouse point
		Point mouseDiff = input.getMousePositionRelativeToComponent();
		// Distance between the mouse points in tiles
		XOffset += (mouseDiff.x - mouseLocation.x) / (float) tileSize;
		YOffset += (mouseDiff.y - mouseLocation.y) / (float) tileSize;

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
			XOffset -= (mouseLocation.x / tileSize);
			YOffset -= (mouseLocation.y / tileSize);
			tileSize /= 2;
			XOffset += (mouseLocation.x / tileSize);
			YOffset += (mouseLocation.y / tileSize);
			Main.input.stopMouseWheel();
		}
		// Zoom in
		if (input.getMouseWheelUp() && tileSize != 256) {
			XOffset -= (mouseLocation.x / tileSize);
			YOffset -= (mouseLocation.y / tileSize);
			tileSize *= 2;
			XOffset += (mouseLocation.x / tileSize);
			YOffset += (mouseLocation.y / tileSize);
			Main.input.stopMouseWheel();
		}
	}

	/**
	 * Update
	 */
	private void update() {
		// Quit
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			running = false;
		} else {
			// Reset image location
			if (input.isKeyDown(KeyEvent.VK_E)) {
				XOffset = 0;
				YOffset = 0;
			}
			// Only allow image zoom and movement when the UI isn't in focus
			if (!builder.getUI().isInFocus()) {
				if (input.isMouseDown(MouseEvent.BUTTON2)) {
					moveScreen();
				} else if (input.hasMouseWheelMoved()) {
					zoom();
				}
			}
			if(input.isKeyDown(KeyEvent.VK_CONTROL)){
				if(input.isKeyDown(KeyEvent.VK_S)){
					save.save(builder);
				}
				input.artificialKeyReleased(KeyEvent.VK_S);
			}

			// Gets the new mouse location
			mouseLocation = input.getMousePositionRelativeToComponent();
			// Updates the builder
			builder.update(mouseLocation);
			// Stops the mouse wheel
			input.stopMouseWheel();
		}
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

		// Draw the builder
		builder.draw(bufferGraphics);
		bufferGraphics.setColor(Color.white);
		bufferGraphics.drawRect(mouseLocation.x-2, mouseLocation.y-2, 4, 4);
		
		// To here
		g.drawImage(BufferImage, 0, 0, null);
	}

	/**
	 * To replace the jOption because it wasn't working.
	 * 
	 * @param message
	 *            The message
	 * @param initialSelectionValue
	 *            The initial selection value
	 * @return The object
	 */
	public static Object textEnterDialog(String message, String initialSelectionValue) {
		Object obj = JOptionPane.showInputDialog(frame, message, initialSelectionValue);
		return obj;

	}

	/**
	 * To replace the jOption because it wasn't working.
	 * 
	 * @param message
	 *            The message
	 * @param initialSelectionValue
	 *            The initial selection value
	 * @param title
	 *            The title
	 * @param selectionValues
	 *            The values
	 * @param messageType
	 *            The message type
	 * @return The object
	 */
	public static Object multipleChoiceDialog(String message, String initialSelectionValue, String title,
			String[] selectionValues, int messageType) {
		Object obj = JOptionPane.showInputDialog(frame, message, title, messageType, null, selectionValues,
				initialSelectionValue);
		return obj;
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