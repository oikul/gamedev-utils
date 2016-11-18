package characterCreator;

import java.awt.GridLayout;

import handlers.InputHandler;
import utils.AbstractMain;

public class Test extends AbstractMain {

	private static final long serialVersionUID = 1L;
	private PartPanel headPanel, torsoPanel, legPanel;

	@Override
	public void initialise() {
		running = true;
		this.setTitle("Character Creator");
		this.setBounds(0, 0, InputHandler.screenSize.width, InputHandler.screenSize.height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		int width = (int) InputHandler.screenSize.width / 4;
		int height = (int) InputHandler.screenSize.height / 4;
		headPanel = new PartPanel("character16/head/", "hair", "deco", 0, 0, width, height);
		torsoPanel = new PartPanel("character16/torso/", "torso", "deco", 0, 0, width, height);
		legPanel = new PartPanel("character16/legs/", "legs", "deco", 0, 0, width, height);
		this.setLayout(new GridLayout());
		// headPanel.setBounds(0, 0, width, height);
		this.add(headPanel);
		// torsoPanel.setBounds(0, 0, width, height);
		this.add(torsoPanel);
		// legPanel.setBounds(0, 0, width, height);
		this.add(legPanel);
		this.setVisible(running);
	}

	@Override
	public void update(float time) {
	}

	@Override
	public void draw() {
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.run();
	}

}
