package utils;

import javax.swing.JFrame;

public abstract class AbstractMain extends JFrame {

	private static final long serialVersionUID = 1L;
	protected boolean running = false;
	public static int width, height;

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

	public abstract void initialise();

	public abstract void update(float time);

	public abstract void draw();

}
