package utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import gameMapController.MapController;
import handlers.InputHandler;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	protected boolean running = false;
	private BufferedImage offimage;
	private Graphics g;
	private MapController controller;
	
	public static int width, height;
	public static Random random;
	public static InputHandler input;
	
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public void run() {
		initialise();

		long beforeTime, afterTime, deltaTime = 0;

		while (running) {
			beforeTime = System.nanoTime();
			update(deltaTime);
			if (running) {
				draw();
			}
			afterTime = System.nanoTime();
			deltaTime = afterTime - beforeTime;

		}
		this.dispose();

	}

	public void initialise(){

		input = new InputHandler(this);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.width;
		height = dim.height;

		this.setTitle("");
		this.setSize(dim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		device.setFullScreenWindow(this);
		this.setVisible(true);
		
		offimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = this.getGraphics();
		
		controller = new MapController();
		
	}

	public void update(float time){
		controller.update(time);
	}

	private void draw() {

		Graphics offgraphics = offimage.getGraphics();
		controller.draw(offgraphics);
		g.drawImage(offimage, 0, 0, width, height, null);
	}

}
