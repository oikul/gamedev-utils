package menus;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel {

	private static final long serialVersionUID = 1L;
	
	public Label(String text, String fontName, int fontType, int fontSize, Color fontColor, int x, int y, int width, int height){
		this.setText(text);
		this.setFont(new Font(fontName, fontType, fontSize));
		this.setForeground(fontColor);
		this.setBounds(x, y, width, height);
		this.setHorizontalAlignment(JLabel.CENTER);
	}

}
