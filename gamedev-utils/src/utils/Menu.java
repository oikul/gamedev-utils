package utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public abstract class Menu extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image bg;
	
	public Menu(String background){
		bg = ResourceLoader.getImage(background);
	}
	
	public abstract int update();

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, InputHandler.screenSize.width, InputHandler.screenSize.height, null);
	}

}