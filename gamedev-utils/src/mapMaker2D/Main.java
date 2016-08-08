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

import utils.InputHandler;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static float XOffset, YOffset;
	public static int width, height, tileSize;
	public static Dimension screenSize;
	public static InputHandler input;
	public static boolean forceFront;

	private boolean running = false;
	private Point mouseLocation;
	private Image BufferImage;
	private Graphics g;
	private BuildMap builder;
	private UI ui;

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
				if (forceFront) {
					Main.frame.setState(Frame.NORMAL);
				}
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
	}

	public void run() {
		init();
		// long beforeTime, afterTime, deltaT;
		while (running) {
			// beforeTime = System.nanoTime();
			update();
			draw();
			// afterTime = System.nanoTime();
			// deltaT = afterTime - beforeTime;
			// if (deltaT < 1000 / 60) {
			// try {
			// Thread.sleep(1000 / 60 - deltaT);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// }
		}
		close();

	}

	private void init() {
		addListeners();
		running = true;
		forceFront = false;
		input = new InputHandler(this);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Settings.gds = ge.getScreenDevices();
		this.setUndecorated(true);
		// user chooses which screen to display the frame on
		int deviceIndex = chooseDevice(Settings.gds);

		try {
			Settings.gds[deviceIndex].setFullScreenWindow(this);
			width = Settings.gds[deviceIndex].getDisplayMode().getWidth();
			height = Settings.gds[deviceIndex].getDisplayMode().getHeight();
			screenSize = new Dimension(width, height);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.exit(0);
		}

		this.setTitle("2D Map Maker");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(width, height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setVisible(true);
		frame = this;
		JOptionPane.setRootFrame(frame);
		tileSize = 16;
		BufferImage = new BufferedImage(Main.width, Main.height, BufferedImage.TYPE_INT_ARGB);
		g = this.getGraphics();
		ui = new UI();
		// build map sizes in tiles 16px atm
		builder = new BuildMap(50, 50, ui);
		mouseLocation = new Point(0, 0);
	}

	private void close() {
		this.dispose();
	}

	private int chooseDevice(GraphicsDevice[] gds) {

		String[] possibilities = new String[gds.length];
		for (int x = 0; x < gds.length; x++) {

			possibilities[x] = x + ". Width:" + gds[x].getDisplayMode().getWidth() + ", Height:"
					+ gds[x].getDisplayMode().getHeight();

		}
		String choice = (String) multipleChoiceDialog("Choose which display you want the screen displayed on. \nBigger screens are normaly better.",
				null, "Display", possibilities, JOptionPane.QUESTION_MESSAGE);
//		String choice = (String) JOptionPane.showInputDialog(frame,
//				"Choose which display you want the screen displayed on. \nBigger screens are normaly better.",
//				"Display", JOptionPane.QUESTION_MESSAGE, null, possibilities, null);
		if (choice == null) {
			choice = "-1";
		} else {
			choice = "" + choice.charAt(0);
		}
		return Integer.parseInt(choice);
	}

	private void moveScreen() {

		Point mouseDiff = input.getMousePositionRelativeToComponent();
		XOffset += (mouseDiff.x - mouseLocation.x) / (float) tileSize;
		YOffset += (mouseDiff.y - mouseLocation.y) / (float) tileSize;

	}

	private void zoom() {
		// zoom out
		if (input.getMouseWheelDown() && tileSize != 4) {
			XOffset -= (mouseLocation.x / tileSize);
			YOffset -= (mouseLocation.y / tileSize);
			tileSize /= 2;
			XOffset += (mouseLocation.x / tileSize);
			YOffset += (mouseLocation.y / tileSize);
			Main.input.stopMouseWheel();
		}
		// zoom in
		if (input.getMouseWheelUp() && tileSize != 256) {
			XOffset -= (mouseLocation.x / tileSize);
			YOffset -= (mouseLocation.y / tileSize);
			tileSize *= 2;
			XOffset += (mouseLocation.x / tileSize);
			YOffset += (mouseLocation.y / tileSize);
			Main.input.stopMouseWheel();
		}
	}

	private void update() {
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			running = false;
		} else {
			if (input.isKeyDown(KeyEvent.VK_E)) {
				XOffset = 0;
				YOffset = 0;
			}
			if (!builder.getUI().isInFocus()) {
				if (input.isMouseDown(MouseEvent.BUTTON2)) {
					moveScreen();
					input.stopMouseWheel();
				} else {
					zoom();
				}
			}

			mouseLocation = input.getMousePositionRelativeToComponent();
			builder.update(mouseLocation);
			input.stopMouseWheel();
		}
	}

	private void draw() {
		Graphics bufferGraphics = BufferImage.getGraphics();
		// draw here
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0, 0, width, height);

		builder.draw(bufferGraphics);

		// to here
		g.drawImage(BufferImage, 0, 0, null);
	}

	public static Object textEnterDialog(String message, String initialSelectionValue) {
		Object obj = JOptionPane.showInputDialog(frame, message, initialSelectionValue);
		return obj;

	}

	public static Object multipleChoiceDialog(String message, String initialSelectionValue, String title,
			String[] selectionValues, int messageType) {
		Object obj = JOptionPane.showInputDialog(frame, message, title, messageType, null, selectionValues, initialSelectionValue);
		return obj;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
