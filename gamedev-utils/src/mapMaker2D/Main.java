package mapMaker2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.InputHandler;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static int width, height, tileSize;
	public static Dimension screenSize;
	public static InputHandler input;
	public static boolean forceFront;

	private boolean running = false,zoomed = true;
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
		System.out.println("Main.run(), start");
		init();
		System.out.println("Main.run(), after init");
		long beforeTime, afterTime, deltaT;
		while (running) {
			beforeTime = System.nanoTime();
			update();
			draw();
			afterTime = System.nanoTime();
			deltaT = afterTime - beforeTime;
			System.out.println("Main.run(), "+deltaT);
//			if (deltaT < 1000 / 60) {
//				try {
//					Thread.sleep(1000 / 60 - deltaT);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		}

	}

	private void init() {
		addListeners();
		running = true;
		forceFront = false;
		input = new InputHandler(this);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gds = ge.getScreenDevices();
		// puts the game on the second screen if it exists
		this.setUndecorated(true);
		int deviceIndex = chooseDevice(gds);

		try {
			gds[deviceIndex].setFullScreenWindow(this);
			width = gds[deviceIndex].getDisplayMode().getWidth();
			height = gds[deviceIndex].getDisplayMode().getHeight();
			screenSize = new Dimension(width, height);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.exit(0);
		}

		this.setTitle("2D Map Maker");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setVisible(true);
		frame = this;
		tileSize = 16;
		BufferImage = new BufferedImage(Main.width,Main.height,BufferedImage.TYPE_INT_ARGB);
		g = this.getGraphics();
		ui = new UI();
		// build map sizes in tiles 16px atm
		builder = new BuildMap(50, 50, ui);
	}

	private int chooseDevice(GraphicsDevice[] gds) {

		Object[] possibilities = new Object[gds.length];
		for (int x = 0; x < gds.length; x++) {

			possibilities[x] = x + ". Width:" + gds[x].getDisplayMode().getWidth() + ", Height:"
					+ gds[x].getDisplayMode().getHeight();

		}

		String choice = (String) JOptionPane.showInputDialog(frame,
				"Choose which display you want the screen displayed on. \nBigger screens are normaly better.",
				"Display", JOptionPane.QUESTION_MESSAGE, null, possibilities, null);
		if (choice == null) {
			choice = "-1";
		} else {
			choice = "" + choice.charAt(0);
		}
		return Integer.parseInt(choice);
	}

	private void update() {

		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		if (input.getMouseWheelDown()) {
			tileSize /= 2;
			if (tileSize < 4)
				tileSize = 4;
			Main.input.stopMouseWheel();
			zoomed = true;
		}
		if (input.getMouseWheelUp()) {
			tileSize *= 2;
			if (tileSize > 256)
				tileSize = 256;
			Main.input.stopMouseWheel();
			zoomed = true;
		}

		builder.update(zoomed);
		zoomed = false;
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

	public static JFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
