package characterCreator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import handlers.InputHandler;
import utils.AbstractMain;

public class CharTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
	private Panel panel;
	private InputHandler input;

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
		input = new InputHandler(this);
	}

	@Override
	public void update(float time) {
		if(input.isKeyDown(KeyEvent.VK_R)){
			panel.update();
		}
	}

	@Override
	public void draw() {
	}

}
