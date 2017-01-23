package utils;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public abstract class AbstractMain extends JFrame {

	private static final long serialVersionUID = 1L;
	protected boolean running = false;
	public static int width, height;

	public void run() {
		initialise();
		long beforeTime, afterTime, deltaTime = 0;
		long counter = System.nanoTime() + 1000000000;
		int fps = 0;
		final int maxFPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayModes()[0].getRefreshRate() + 9;
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

	public abstract void initialise();

	public abstract void update(float time);

	public abstract void draw();

	public abstract void close();

}
