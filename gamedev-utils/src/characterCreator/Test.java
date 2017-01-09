package characterCreator;

import java.awt.GridLayout;

import handlers.InputHandler;
import utils.AbstractMain;

public class Test extends AbstractMain {

	private static final long serialVersionUID = 1L;
	private PartPanel headPanel, torsoPanel, legPanel, miscPanel, skinPanel;

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
		miscPanel = new PartPanel("character16/misc/", "shoes", "arms", 0, 0, width, height);
		skinPanel = new PartPanel("character16/skin/", "base", "deco", 0, 0, width, height);
		this.setLayout(new GridLayout(4, 4));
		this.add(headPanel, 0);
		this.add(torsoPanel, 1);
		this.add(legPanel, 2);
		this.add(skinPanel, 3);
		this.add(miscPanel, 4);
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

	@Override
	public void close() {
		
	}

}
