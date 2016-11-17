package characterCreator;

import handlers.InputHandler;
import utils.AbstractMain;

public class CharTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
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
		panel = new Panel("character16/");
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
