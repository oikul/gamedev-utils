package buildingGenerator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	protected boolean running = false;
	private BufferedImage offimage;
	private Graphics graphics;
	
	public static int width, height;
	public static Random random;
	
	private BuildingBuilder builder;
	
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

	public void initialise(){
		
		running = true;
		
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
		graphics = this.getGraphics();
		
		builder = new BuildingBuilder();
	}

	public void update(){
		builder.update();
	}

	private void draw() {

		Graphics g = offimage.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		builder.draw(g);
		
		graphics.drawImage(offimage, 0, 0, width, height, null);
	}
	
	public static void main(String[] args){
		Main main = new Main();
		main.run();
	}

}
