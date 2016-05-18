package utils;

import javax.swing.JFrame;

public abstract class AbstractMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int TICKS_PER_SECOND = 25;
	private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	protected boolean running = false;

	public void run() {
		initialise();
		double nextTick = System.currentTimeMillis();
		int loops;
		while (running) {
			loops = 0;
			while (System.currentTimeMillis() > nextTick && loops < MAX_FRAMESKIP) {
				update();
				nextTick += SKIP_TICKS;
				loops++;
			}
			draw();
		}
	}

	public abstract void initialise();

	public abstract void update();

	public abstract void draw();

}
