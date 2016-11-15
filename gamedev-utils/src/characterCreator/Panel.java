package characterCreator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import handlers.InputHandler;
import handlers.MathHandler;
import handlers.ResourceHandler;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String hairs = "hair/", torsos = "torsos/", legs = "legs/";
	private ArrayList<BufferedImage> hairList, torsoList, legList;
	private int hairIndex, torsoIndex, legIndex;

	private JButton hairChange, torsoChange, legChange;
	private JSlider hairR, hairG, hairB, torsoR, torsoG, torsoB, legR, legG, legB, skinR, skinG, skinB;
	private JLabel hair, torso, leg, skin, r0, r1, r2, r3, g0, g1, g2, g3, b0, b1, b2, b3;

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
				if (hairIndex < hairList.size()) {
					hairIndex++;
				} else {
					hairIndex = 0;
				}
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
		hair = new JLabel("Hair:");
		x = (int) (4 * InputHandler.midPoint.x) / 5 - (width / 2);
		hair.setBounds(x, y, width, height);
		x = (int) (6 * InputHandler.midPoint.x) / 5 - (width / 2);
		r0 = new JLabel("Red");
		y = (int) InputHandler.midPoint.y / 3 - (2 * height);
		r0.setBounds(x, y, width, height);
		g0 = new JLabel("Green");
		y = (int) InputHandler.midPoint.y / 3 - (height / 2);
		g0.setBounds(x, y, width, height);
		b0 = new JLabel("Blue");
		y = (int) InputHandler.midPoint.y / 3 + height;
		b0.setBounds(x, y, width, height);
		this.add(hairChange);
		this.add(hair);
		this.add(r0);
		this.add(g0);
		this.add(b0);
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
				if (torsoIndex < torsoList.size()) {
					torsoIndex++;
				} else {
					torsoIndex = 0;
				}
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
		torso = new JLabel("Torso:");
		x = (int) (4 * InputHandler.midPoint.x) / 5 - (width / 2);
		torso.setBounds(x, y, width, height);
		x = (int) (6 * InputHandler.midPoint.x) / 5 - (width / 2);
		r1 = new JLabel("Red");
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (2 * height);
		r1.setBounds(x, y, width, height);
		g1 = new JLabel("Green");
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (height / 2);
		g1.setBounds(x, y, width, height);
		b1 = new JLabel("Blue");
		y = (int) (2 * InputHandler.midPoint.y) / 3 + height;
		b1.setBounds(x, y, width, height);
		this.add(torsoChange);
		this.add(torso);
		this.add(r1);
		this.add(g1);
		this.add(b1);
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
				if (legIndex < legList.size()) {
					legIndex++;
				} else {
					legIndex = 0;
				}
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
		leg = new JLabel("Legs:");
		x = (int) (4 * InputHandler.midPoint.x) / 5 - (width / 2);
		leg.setBounds(x, y, width, height);
		x = (int) (6 * InputHandler.midPoint.x) / 5 - (width / 2);
		r2 = new JLabel("Red");
		y = (int) InputHandler.midPoint.y - (2 * height);
		r2.setBounds(x, y, width, height);
		g2 = new JLabel("Green");
		y = (int) InputHandler.midPoint.y - (height / 2);
		g2.setBounds(x, y, width, height);
		b2 = new JLabel("Blue");
		y = (int) InputHandler.midPoint.y + height;
		b2.setBounds(x, y, width, height);
		this.add(legChange);
		this.add(leg);
		this.add(r2);
		this.add(g2);
		this.add(b2);
		skin = new JLabel("Skin:");
		x = (int) (4 * InputHandler.midPoint.x) / 5 - (width / 2);
		y = (int) (4 * InputHandler.midPoint.y) / 3 - (height / 2);
		skin.setBounds(x, y, width, height);
		x = (int) (6 * InputHandler.midPoint.x) / 5 - (width / 2);
		r3 = new JLabel("Red");
		y = 4 * ((int) InputHandler.midPoint.y / 3) - (2 * height);
		r3.setBounds(x, y, width, height);
		g3 = new JLabel("Green");
		y = 4 * ((int) InputHandler.midPoint.y / 3) - (height / 2);
		g3.setBounds(x, y, width, height);
		b3 = new JLabel("Blue");
		y = 4 * ((int) InputHandler.midPoint.y / 3) + height;
		b3.setBounds(x, y, width, height);
		this.add(skin);
		this.add(r3);
		this.add(g3);
		this.add(b3);
		// rbg sliders
		width = InputHandler.screenSize.width / 3;
		height = InputHandler.screenSize.height / 30;
		x = ((4 * InputHandler.screenSize.width) / 5) - (width / 2);
		y = (int) InputHandler.midPoint.y / 3 - (2 * height);
		hairR = new JSlider();
		hairR.setMaximum(255);
		hairR.setMinimum(0);
		hairR.setValue(MathHandler.random.nextInt(255));
		hairR.setBounds(x, y, width, height);
		this.add(hairR);
		y = (int) InputHandler.midPoint.y / 3 - (height / 2);
		hairG = new JSlider();
		hairG.setMaximum(255);
		hairG.setMinimum(0);
		hairG.setValue(MathHandler.random.nextInt(255));
		hairG.setBounds(x, y, width, height);
		this.add(hairG);
		y = (int) InputHandler.midPoint.y / 3 + height;
		hairB = new JSlider();
		hairB.setMaximum(255);
		hairB.setMinimum(0);
		hairB.setValue(MathHandler.random.nextInt(255));
		hairB.setBounds(x, y, width, height);
		this.add(hairB);
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (2 * height);
		torsoR = new JSlider();
		torsoR.setMaximum(255);
		torsoR.setMinimum(0);
		torsoR.setValue(MathHandler.random.nextInt(255));
		torsoR.setBounds(x, y, width, height);
		this.add(torsoR);
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (height / 2);
		torsoG = new JSlider();
		torsoG.setMaximum(255);
		torsoG.setMinimum(0);
		torsoG.setValue(MathHandler.random.nextInt(255));
		torsoG.setBounds(x, y, width, height);
		this.add(torsoG);
		y = (int) (2 * InputHandler.midPoint.y) / 3 + height;
		torsoB = new JSlider();
		torsoB.setMaximum(255);
		torsoB.setMinimum(0);
		torsoB.setValue(MathHandler.random.nextInt(255));
		torsoB.setBounds(x, y, width, height);
		this.add(torsoB);
		y = (int) InputHandler.midPoint.y - (2 * height);
		legR = new JSlider();
		legR.setMaximum(255);
		legR.setMinimum(0);
		legR.setValue(MathHandler.random.nextInt(255));
		legR.setBounds(x, y, width, height);
		this.add(legR);
		y = (int) InputHandler.midPoint.y - (height / 2);
		legG = new JSlider();
		legG.setMaximum(255);
		legG.setMinimum(0);
		legG.setValue(MathHandler.random.nextInt(255));
		legG.setBounds(x, y, width, height);
		this.add(legG);
		y = (int) InputHandler.midPoint.y + height;
		legB = new JSlider();
		legB.setMaximum(255);
		legB.setMinimum(0);
		legB.setValue(MathHandler.random.nextInt(255));
		legB.setBounds(x, y, width, height);
		this.add(legB);
		y = 4 * ((int) InputHandler.midPoint.y / 3) - (2 * height);
		skinR = new JSlider();
		skinR.setMaximum(255);
		skinR.setMinimum(0);
		skinR.setValue(MathHandler.random.nextInt(255));
		skinR.setBounds(x, y, width, height);
		this.add(skinR);
		y = 4 * ((int) InputHandler.midPoint.y / 3) - (height / 2);
		skinG = new JSlider();
		skinG.setMaximum(255);
		skinG.setMinimum(0);
		skinG.setValue(MathHandler.random.nextInt(255));
		skinG.setBounds(x, y, width, height);
		this.add(skinG);
		y = 4 * ((int) InputHandler.midPoint.y / 3) + height;
		skinB = new JSlider();
		skinB.setMaximum(255);
		skinB.setMinimum(0);
		skinB.setValue(MathHandler.random.nextInt(255));
		skinB.setBounds(x, y, width, height);
		this.add(skinB);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(InputHandler.screenSize.width / 16, InputHandler.screenSize.height / 16,
				InputHandler.screenSize.width / 4, 2 * InputHandler.screenSize.height / 3);
	}

}
