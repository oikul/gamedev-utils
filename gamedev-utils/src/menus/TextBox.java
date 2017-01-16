package menus;

import javax.swing.JTextField;

public class TextBox extends JTextField {

	private static final long serialVersionUID = 1L;
	
	public TextBox(String defaultText, int x, int y, int width, int height){
		this.setText(defaultText);
		this.setBounds(x, y, width, height);
	}

}
