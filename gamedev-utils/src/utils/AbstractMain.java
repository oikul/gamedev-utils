package utils;

import javax.swing.JFrame;

public abstract class AbstractMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int TICKS_PER_SECOND = 25;
	private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	protected boolean running = false;
	protected int FPS = 60;

	public void run() {
		initialise();
		double nextTick = System.currentTimeMillis();
		int loops;
		while (running) {
			long beforeTime = System.currentTimeMillis();
			update();
			draw();
			long afterTime = System.currentTimeMillis();
			long sleepTime = 1000 / FPS - (afterTime - beforeTime);
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			draw();
		}
	}

	public abstract void initialise();

	public abstract void update();

	public abstract void draw();

}
