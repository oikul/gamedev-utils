package utils;

import java.awt.event.MouseEvent;

import javax.swing.JButton;

import handlers.InputHandler;

public abstract class Button extends JButton {

	private static final long serialVersionUID = 1L;
	private InputHandler input;
	
	public Button(String title, int x, int y, int width, int height){
		this.setText(title);
		this.setBounds(x, y, width, height);
		input = new InputHandler(this);
	}
	
	public boolean buttonPressed(){
		return (input.isMouseDown(MouseEvent.BUTTON1));
	}

}
