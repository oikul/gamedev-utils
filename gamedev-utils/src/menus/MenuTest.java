package menus;

import handlers.InputHandler;
import utils.AbstractMain;

public class MenuTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
	private Menu menu;

	@Override
	public void initialise() {
		running = true;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Menu Test");
		this.setSize(InputHandler.screenSize);
		menu = new Menu("backgrounds/space"){
			private static final long serialVersionUID = 1L;
			@Override
			public int update() {
				return 0;
			}
		};
		this.add(menu);
		this.setVisible(running);
	}

	@Override
	public void update(float time) {
		
	}

	@Override
	public void draw() {
		
	}

	public static void main(String[] args) {
		MenuTest main = new MenuTest();
		main.run();
	}

}
