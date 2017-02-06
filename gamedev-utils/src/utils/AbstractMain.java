package utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public abstract class AbstractMain extends JFrame{

	private static final long serialVersionUID = 1L;
	public static int width, height;
	
	protected boolean running = false;
	protected Graphics g;
	protected BufferedImage offImage;
	
	private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	public void run() {
		initialise();
		long beforeTime, afterTime, deltaTime = 0;
		long counter = System.nanoTime() + 1000000000;
		int fps = 0;
		final int maxFPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayModes()[0].getRefreshRate();
		long minFrameTime = 1000000000l / maxFPS;
		deltaTime = 1;
		update(0);
		while (running) {
			beforeTime = System.nanoTime();
			draw();
			update(deltaTime / 1000000000f);
			afterTime = System.nanoTime();
			deltaTime = afterTime - beforeTime;
			fps++;
			if (deltaTime < minFrameTime) {
				try {
					Thread.sleep((minFrameTime - deltaTime) / 1000000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (System.nanoTime() > counter) {
				counter += 1000000000;
				System.out.println("fps: " + fps);
				fps = 0;
			}
		}
		close();
	}
	
	public void defaultInit(String title){
		running = true;
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = dim.width;
		height = dim.height;

		this.setTitle(title);
		this.setSize(dim);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		device.setFullScreenWindow(this);
		this.setVisible(true);
		
		g = this.getGraphics();
		offImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
	}

	public abstract void initialise();

	public abstract void update(float time);

	public abstract void draw();

	public abstract void close();

}
