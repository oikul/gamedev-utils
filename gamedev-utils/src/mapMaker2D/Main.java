package mapMaker2D;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.InputHandler;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Component component;
	public static int width, height, tileSize;
	public static Dimension screenSize;
	public static InputHandler input;
	
	private boolean running = false;
	private Image BufferImage;
	private Graphics g;
	private BuildMap builder;


	public void run() {

		init();
		while (running) {
			update();
			draw();
			System.out.println("loop");
		}

	}

	private void init() {
		running = true;
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
		component = this.getComponent(0);
		tileSize = 16;
		BufferImage = this.createImage(width, height);
		g = this.getGraphics();
		builder = new BuildMap(10, 10);
	}

	private int chooseDevice(GraphicsDevice[] gds) {

		Object[] possibilities = new Object[gds.length];
		for (int x = 0; x < gds.length; x++) {

			possibilities[x] = x + ". Width:" + gds[x].getDisplayMode().getWidth() + ", Height:"
					+ gds[x].getDisplayMode().getHeight();

		}

		String choice = (String) JOptionPane.showInputDialog(component,
				"Choose which display you want the screen displayed on. \nBigger screens are normaly better.",
				"Display", JOptionPane.QUESTION_MESSAGE, null, possibilities, null);
		if (choice == null) {
			choice = "-1";
		}else{
			choice = "" + choice.charAt(0);
		}
		return Integer.parseInt(choice);
	}

	private void update() {

		builder.update();
		
	}

	private void draw() {
		Graphics bufferGraphics = BufferImage.getGraphics();
		// draw here
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0, 0, width, height);
		bufferGraphics.setColor(Color.blue);
		bufferGraphics.fillRect(0, 0, 100, 100);
		builder.draw(bufferGraphics);
		
		// to here
		g.drawImage(BufferImage, 0, 0, null);
	}
	
	public static Component getComponent(){
		return component;
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}
