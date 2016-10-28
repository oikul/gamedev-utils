package mapMaker2D;

import java.awt.event.KeyEvent;

public class Menu {

	private boolean running;

	public Menu() {

	}

	public void open() {
		
		running = true;
		while (running) {

			update();
			draw();
			
		}

	}
	
	private void update(){
		
		if(Main.input.isKeyDown(KeyEvent.VK_ESCAPE)){
			running = false;
			Main.input.artificialKeyReleased(KeyEvent.VK_ESCAPE);
		}
		
	}
	
	private void draw(){
		
	}

}
