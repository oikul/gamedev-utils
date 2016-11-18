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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import handlers.InputHandler;
import handlers.MathHandler;
import handlers.ResourceHandler;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String partsLocation, hairs = "head/hair/", torsos = "torso/", legs = "legs/";
	private ArrayList<String> hairList, torsoList, legList;
	private int hairIndex, torsoIndex, legIndex;
	private BufferedImage sprites;
	private int prevColorH = Color.WHITE.getRGB(), prevColorT = Color.WHITE.getRGB(), prevColorL = Color.WHITE.getRGB(),
			prevColorS = Color.WHITE.getRGB();

	private JButton hairChange, torsoChange, legChange, done, cancel;
	private JSlider hairR, hairG, hairB, torsoR, torsoG, torsoB, legR, legG, legB, skinR, skinG, skinB;
	private JLabel hair, torso, leg, skin, r0, r1, r2, r3, g0, g1, g2, g3, b0, b1, b2, b3;

	public Panel(String partsLocation) {
		this.partsLocation = partsLocation;
		hairList = new ArrayList<String>();
		torsoList = new ArrayList<String>();
		legList = new ArrayList<String>();
		boolean more = true;
		// load up hair sprites
		for (int i = 0; more; i++) {
			try {
				ResourceHandler.getBufferedImage(partsLocation + hairs + "hair_" + i);
				hairList.add(partsLocation + hairs + "hair_" + i);
			} catch (Exception e) {
				more = false;
			}
		}
		more = true;
		// load up torso sprites
		for (int i = 0; more; i++) {
			try {
				ResourceHandler.getBufferedImage(partsLocation + torsos + "torso_" + i);
				torsoList.add(partsLocation + torsos + "torso_" + i);
			} catch (Exception e) {
				more = false;
			}
		}
		more = true;
		// load up leg sprites
		for (int i = 0; more; i++) {
			try {
				ResourceHandler.getBufferedImage(partsLocation + legs + "leg_" + i);
				legList.add(partsLocation + legs + "leg_" + i);
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
				if (hairIndex < hairList.size() - 1) {
					hairIndex++;
				} else {
					hairIndex = 0;
				}
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
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
				if (torsoIndex < torsoList.size() - 1) {
					torsoIndex++;
				} else {
					torsoIndex = 0;
				}
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
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
				if (legIndex < legList.size() - 1) {
					legIndex++;
				} else {
					legIndex = 0;
				}
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
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
		hairR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(hairR);
		y = (int) InputHandler.midPoint.y / 3 - (height / 2);
		hairG = new JSlider();
		hairG.setMaximum(255);
		hairG.setMinimum(0);
		hairG.setValue(MathHandler.random.nextInt(255));
		hairG.setBounds(x, y, width, height);
		this.add(hairG);
		hairG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		y = (int) InputHandler.midPoint.y / 3 + height;
		hairB = new JSlider();
		hairB.setMaximum(255);
		hairB.setMinimum(0);
		hairB.setValue(MathHandler.random.nextInt(255));
		hairB.setBounds(x, y, width, height);
		hairB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(hairB);
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (2 * height);
		torsoR = new JSlider();
		torsoR.setMaximum(255);
		torsoR.setMinimum(0);
		torsoR.setValue(MathHandler.random.nextInt(255));
		torsoR.setBounds(x, y, width, height);
		torsoR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(torsoR);
		y = (int) (2 * InputHandler.midPoint.y) / 3 - (height / 2);
		torsoG = new JSlider();
		torsoG.setMaximum(255);
		torsoG.setMinimum(0);
		torsoG.setValue(MathHandler.random.nextInt(255));
		torsoG.setBounds(x, y, width, height);
		torsoG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(torsoG);
		y = (int) (2 * InputHandler.midPoint.y) / 3 + height;
		torsoB = new JSlider();
		torsoB.setMaximum(255);
		torsoB.setMinimum(0);
		torsoB.setValue(MathHandler.random.nextInt(255));
		torsoB.setBounds(x, y, width, height);
		torsoB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(torsoB);
		y = (int) InputHandler.midPoint.y - (2 * height);
		legR = new JSlider();
		legR.setMaximum(255);
		legR.setMinimum(0);
		legR.setValue(MathHandler.random.nextInt(255));
		legR.setBounds(x, y, width, height);
		legR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(legR);
		y = (int) InputHandler.midPoint.y - (height / 2);
		legG = new JSlider();
		legG.setMaximum(255);
		legG.setMinimum(0);
		legG.setValue(MathHandler.random.nextInt(255));
		legG.setBounds(x, y, width, height);
		legG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(legG);
		y = (int) InputHandler.midPoint.y + height;
		legB = new JSlider();
		legB.setMaximum(255);
		legB.setMinimum(0);
		legB.setValue(MathHandler.random.nextInt(255));
		legB.setBounds(x, y, width, height);
		legB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(legB);
		y = 4 * ((int) InputHandler.midPoint.y / 3) - (2 * height);
		skinR = new JSlider();
		skinR.setMaximum(255);
		skinR.setMinimum(0);
		skinR.setValue(MathHandler.random.nextInt(255));
		skinR.setBounds(x, y, width, height);
		skinR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(skinR);
		y = 4 * ((int) InputHandler.midPoint.y / 3) - (height / 2);
		skinG = new JSlider();
		skinG.setMaximum(255);
		skinG.setMinimum(0);
		skinG.setValue(MathHandler.random.nextInt(255));
		skinG.setBounds(x, y, width, height);
		skinG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(skinG);
		y = 4 * ((int) InputHandler.midPoint.y / 3) + height;
		skinB = new JSlider();
		skinB.setMaximum(255);
		skinB.setMinimum(0);
		skinB.setValue(MathHandler.random.nextInt(255));
		skinB.setBounds(x, y, width, height);
		skinB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
				repaint();
			}
		});
		this.add(skinB);
		done = new JButton("Done");
		done.setBounds(13 * (int) InputHandler.screenSize.width / 16, 13 * (int) InputHandler.screenSize.height / 16, (int) InputHandler.midPoint.x/8, (int) InputHandler.midPoint.y/8);
		done.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		cancel = new JButton("Cancel");
		cancel.setBounds(10 * (int) InputHandler.screenSize.width / 16, 13 * (int) InputHandler.screenSize.height / 16, (int) InputHandler.midPoint.x/8, (int) InputHandler.midPoint.y/8);
		this.add(done);
		this.add(cancel);
		sprites = makeSpriteSheet(hairList.get(hairIndex), torsoList.get(torsoIndex), legList.get(legIndex));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(sprites, InputHandler.screenSize.width / 28, InputHandler.screenSize.height / 14,
				2 * InputHandler.screenSize.width / 6, 4 * InputHandler.screenSize.height / 5, null);
	}

	public BufferedImage makeSpriteSheet(String hairPath, String torsoPath, String legsPath) {
		BufferedImage base = ResourceHandler.getBufferedImage(partsLocation + "base");
		BufferedImage hair = ResourceHandler.getBufferedImage(hairPath);
		BufferedImage torso = ResourceHandler.getBufferedImage(torsoPath);
		BufferedImage legs = ResourceHandler.getBufferedImage(legsPath);
		int rgb = -1;
		rgb = new Color(skinR.getValue(), skinG.getValue(), skinB.getValue()).getRGB();
		for (int i = 0; i < base.getWidth(); i++) {
			for (int j = 0; j < base.getHeight(); j++) {
				if (base.getRGB(i, j) == prevColorS || base.getRGB(i, j) == Color.WHITE.getRGB()) {
					base.setRGB(i, j, rgb);
				}
			}
		}
		prevColorS = rgb;
		Graphics g = base.getGraphics();
		rgb = new Color(hairR.getValue(), hairG.getValue(), hairB.getValue()).getRGB();
		for (int i = 0; i < hair.getWidth(); i++) {
			for (int j = 0; j < hair.getHeight(); j++) {
				if (hair.getRGB(i, j) == prevColorH || hair.getRGB(i, j) == Color.white.getRGB()) {
					hair.setRGB(i, j, rgb);
				}
			}
		}
		prevColorH = rgb;
		rgb = new Color(torsoR.getValue(), torsoG.getValue(), torsoB.getValue()).getRGB();
		for (int i = 0; i < torso.getWidth(); i++) {
			for (int j = 0; j < torso.getHeight(); j++) {
				if (torso.getRGB(i, j) == prevColorT || torso.getRGB(i, j) == Color.WHITE.getRGB()) {
					torso.setRGB(i, j, rgb);
				}
			}
		}
		prevColorT = rgb;
		rgb = new Color(legR.getValue(), legG.getValue(), legB.getValue()).getRGB();
		for (int i = 0; i < legs.getWidth(); i++) {
			for (int j = 0; j < legs.getHeight(); j++) {
				if (legs.getRGB(i, j) == prevColorL || legs.getRGB(i, j) == Color.WHITE.getRGB()) {
					legs.setRGB(i, j, rgb);
				}
			}
		}
		prevColorL = rgb;
		g.drawImage(torso, 0, 0, null);
		g.drawImage(legs, 0, 0, null);
		g.drawImage(hair, 0, 0, null);
		g.drawImage(hair, 0, hair.getHeight(), null);
		g.drawImage(hair, 0, hair.getHeight() * 2, null);
		return base;
	}

}
