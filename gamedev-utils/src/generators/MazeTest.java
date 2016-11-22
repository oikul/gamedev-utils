package generators;

import handlers.InputHandler;
import utils.AbstractMain;

public class MazeTest extends AbstractMain {

	private static final long serialVersionUID = 1L;

	@Override
	public void initialise() {
		running = true;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Maze Gen Test");
		this.setSize(InputHandler.screenSize);
		this.setVisible(running);
	}

	@Override
	public void update(float time) {
	}

	@Override
	public void draw() {
	}

	public static void main(String[] args) {
		MazeTest main = new MazeTest();
		main.run();
	}

}
