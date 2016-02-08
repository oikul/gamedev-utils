package utils;

import javax.swing.JFrame;

public abstract class AbstractMain extends JFrame implements MainInterface {

	private static final long serialVersionUID = 6540412966091247478L;
	protected boolean running = false;

	public void run() {
		initialise();
		while (running) {
			long beforeTime = System.currentTimeMillis();
			update();
			draw();
			long afterTime = System.currentTimeMillis();
			long sleepTime = 1000 / 60 - (afterTime - beforeTime);
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
