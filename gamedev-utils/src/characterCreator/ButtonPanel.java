package characterCreator;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton sprite, animate, direction, cancel, done;

	public ButtonPanel(){
		sprite = new JButton("Sprite Sheet");
		animate = new JButton("Animate");
		direction = new JButton("Change Direction");
		cancel = new JButton("Cancel");
		done = new JButton("Done");
	}

}
