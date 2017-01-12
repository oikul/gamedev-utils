package utils;

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
		deltaTime = 1;
		update(0);
		while (running) {
			beforeTime = System.nanoTime();
			draw();
			update(deltaTime / 1000000000f);
			afterTime = System.nanoTime();
			deltaTime = afterTime - beforeTime;
			fps++;
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
