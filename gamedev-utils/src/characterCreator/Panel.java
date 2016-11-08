package characterCreator;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import handlers.InputHandler;
import handlers.ResourceHandler;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String hairs = "hair/", torsos = "torsos/", legs = "legs/";
	private ArrayList<BufferedImage> hairList, torsoList, legList;
	private int hairIndex, torsoIndex, legIndex;

	private JButton hairChange, torsoChange, legChange;

	public Panel(String partsLocation) {
		hairList = new ArrayList<BufferedImage>();
		torsoList = new ArrayList<BufferedImage>();
		legList = new ArrayList<BufferedImage>();
		boolean more = true;
		// load up hair sprites
		for (int i = 0; more; i++) {
			try {
				BufferedImage hair = ResourceHandler.getBufferedImage(partsLocation + hairs + "hair_" + i);
				hairList.add(hair);
			} catch (Exception e) {
				more = false;
			}
		}
		more = true;
		// load up torso sprites
		for (int i = 0; more; i++) {
			try {
				BufferedImage torso = ResourceHandler.getBufferedImage(partsLocation + torsos + "torso_" + i);
				torsoList.add(torso);
			} catch (Exception e) {
				more = false;
			}
		}
		more = true;
		// load up leg sprites
		for (int i = 0; more; i++) {
			try {
				BufferedImage leg = ResourceHandler.getBufferedImage(partsLocation + legs + "leg_" + i);
				legList.add(leg);
			} catch (Exception e) {
				more = false;
			}
		}
		this.setLayout(null);
		int x, y, width = InputHandler.screenSize.width / 18, height = InputHandler.screenSize.height / 32;
		// Buttons
		hairChange = new JButton(">");
		x = (int) InputHandler.midPoint.x - (width / 2);
		y = (int) InputHandler.midPoint.y / 3 - (height / 2);
		hairChange.setBounds(x, y, width, height);
		hairChange.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				hairIndex ++;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
		this.add(hairChange);
		torsoChange = new JButton(">");
		x = (int) InputHandler.midPoint.x - (width / 2);
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (height / 2);
		torsoChange.setBounds(x, y, width, height);
		torsoChange.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				torsoIndex ++;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
		this.add(torsoChange);
		legChange = new JButton(">");
		x = (int) InputHandler.midPoint.x - (width / 2);
		y = (int) InputHandler.midPoint.y - (height / 2);
		legChange.setBounds(x, y, width, height);
		legChange.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				legIndex ++;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
		this.add(legChange);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
