package characterCreator;

import handlers.InputHandler;
import utils.AbstractMain;

public class CharTest extends AbstractMain {
	
	private Panel panel;

	public static void main(String[] args) {
		CharTest main = new CharTest();
		main.run();
	}

	@Override
	public void initialise() {
		running = true;
		this.setTitle("Character Creator");
		this.setBounds(0, 0, InputHandler.screenSize.width, InputHandler.screenSize.height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new Panel("");
		this.add(panel);
		this.setVisible(running);
	}

	@Override
	public void update(float time) {
	}

	@Override
	public void draw() {
	}

}
