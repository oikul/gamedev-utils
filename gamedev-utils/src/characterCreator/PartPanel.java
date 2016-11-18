package characterCreator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import handlers.MathHandler;
import handlers.ResourceHandler;

public class PartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> firstPartList, secondPartList;
	private int firstIndex, secondIndex;
	private int firstRGB, secondRGB, x, y, width, height;

	private JButton firstChangeLeft, firstChangeRight, secondChangeLeft, secondChangeRight;
	private JSlider firstR, firstG, firstB, secondR, secondG, secondB;
	private JLabel first, r0, g0, b0, second, r1, g1, b1;

	public PartPanel(String partsLocation, String firstPart, String secondPart, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		firstPartList = new ArrayList<String>();
		secondPartList = new ArrayList<String>();
		boolean more = true;
		for (int i = 0; more; i++) {
			try {
				String s = partsLocation + firstPart + "_" + i;
				ResourceHandler.getBufferedImage(s);
				firstPartList.add(s);
			} catch (Exception e) {
				more = false;
			}
		}
		more = true;
		for (int i = 0; more; i++) {
			try {
				String s = partsLocation + secondPart + "_" + i;
				ResourceHandler.getBufferedImage(s);
				secondPartList.add(s);
			} catch (Exception e) {
				more = false;
			}
		}
		firstChangeLeft = new JButton("<");
		firstChangeLeft.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (firstIndex > 0) {
					firstIndex--;
				} else {
					firstIndex = firstPartList.size();
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
		firstChangeRight = new JButton(">");
		firstChangeRight.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (firstIndex < firstPartList.size() - 1) {
					firstIndex++;
				} else {
					firstIndex = 0;
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
		secondChangeLeft = new JButton("<");
		secondChangeLeft.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (secondIndex > 0) {
					secondIndex--;
				} else {
					secondIndex = secondPartList.size();
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
		secondChangeRight = new JButton(">");
		secondChangeRight.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (secondIndex < secondPartList.size() - 1) {
					secondIndex++;
				} else {
					secondIndex = 0;
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
		first = new JLabel(firstPart);
		r0 = new JLabel("Red");
		g0 = new JLabel("Green");
		b0 = new JLabel("Blue");
		second = new JLabel(secondPart);
		r1 = new JLabel("Red");
		g1 = new JLabel("Green");
		b1 = new JLabel("Blue");
		firstR = new JSlider();
		firstR.setMaximum(255);
		firstR.setMinimum(0);
		firstR.setValue(MathHandler.random.nextInt(255));
		firstR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setFirstRGB(new Color(firstR.getValue(), firstG.getValue(), firstB.getValue()).getRGB());
			}
		});
		firstG = new JSlider();
		firstG.setMaximum(255);
		firstG.setMinimum(0);
		firstG.setValue(MathHandler.random.nextInt(255));
		firstG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setFirstRGB(new Color(firstR.getValue(), firstG.getValue(), firstB.getValue()).getRGB());
			}
		});
		firstB = new JSlider();
		firstB.setMaximum(255);
		firstB.setMinimum(0);
		firstB.setValue(MathHandler.random.nextInt(255));
		firstB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setFirstRGB(new Color(firstR.getValue(), firstG.getValue(), firstB.getValue()).getRGB());
			}
		});
		secondR = new JSlider();
		secondR.setMaximum(255);
		secondR.setMinimum(0);
		secondR.setValue(MathHandler.random.nextInt(255));
		secondR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setSecondRGB(new Color(secondR.getValue(), secondG.getValue(), secondB.getValue()).getRGB());
			}
		});
		secondG = new JSlider();
		secondG.setMaximum(255);
		secondG.setMinimum(0);
		secondG.setValue(MathHandler.random.nextInt(255));
		secondG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setSecondRGB(new Color(secondR.getValue(), secondG.getValue(), secondB.getValue()).getRGB());
			}
		});
		secondB = new JSlider();
		secondB.setMaximum(255);
		secondB.setMinimum(0);
		secondB.setValue(MathHandler.random.nextInt(255));
		secondB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setSecondRGB(new Color(secondR.getValue(), secondG.getValue(), secondB.getValue()).getRGB());
			}
		});
		this.setLayout(null);
		x += width / 24;
		y -= height / 24;
		first.setBounds(x, y + height / 9, width / 12, height / 12);
		this.add(first);
		firstChangeLeft.setBounds(x + width / 5, y + height / 9, width / 10, height / 12);
		this.add(firstChangeLeft);
		firstChangeRight.setBounds(x + 2 * width / 5, y + height / 9, width / 10, height / 12);
		this.add(firstChangeRight);
		r0.setBounds(x, y + 2 * height / 9, width / 12, height / 12);
		this.add(r0);
		firstR.setBounds(x + width / 5, y + 2 * height / 9, 2 * width / 3, height / 12);
		this.add(firstR);
		g0.setBounds(x, y + 3 * height / 9, width / 12, height / 12);
		this.add(g0);
		firstG.setBounds(x + width / 5, y + 3 * height / 9, 2 * width / 3, height / 12);
		this.add(firstG);
		b0.setBounds(x, y + 4 * height / 9, width / 12, height / 12);
		this.add(b0);
		firstB.setBounds(x + width / 5, y + 4 * height / 9, 2 * width / 3, height / 12);
		this.add(firstB);
		second.setBounds(x, y + 5 * height / 9, width / 12, height / 12);
		this.add(second);
		secondChangeLeft.setBounds(x + width / 5, y + 5 * height / 9, width / 10, height / 12);
		this.add(secondChangeLeft);
		secondChangeRight.setBounds(x + 2 * width / 5, y + 5 * height / 9, width / 10, height / 12);
		this.add(secondChangeRight);
		r1.setBounds(x, y + 6 * height / 9, width / 12, height / 12);
		this.add(r1);
		secondR.setBounds(x + width / 5, y + 6 * height / 9, 2 * width / 3, height / 12);
		this.add(secondR);
		g1.setBounds(x, y + 7 * height / 9, width / 12, height / 12);
		this.add(g1);
		secondG.setBounds(x + width / 5, y + 7 * height / 9, 2 * width / 3, height / 12);
		this.add(secondG);
		b1.setBounds(x, y + 8 * height / 9, width / 12, height / 12);
		this.add(b1);
		secondB.setBounds(x + width / 5, y + 8 * height / 9, 2 * width / 3, height / 12);
		this.add(secondB);
	}

	public int getFirstRGB() {
		return firstRGB;
	}

	public void setFirstRGB(int firstRGB) {
		this.firstRGB = firstRGB;
	}

	public int getSecondRGB() {
		return secondRGB;
	}

	public void setSecondRGB(int secondRGB) {
		this.secondRGB = secondRGB;
	}

	public String getFirstPartLocation() {
		return firstPartList.get(firstIndex);
	}

	public String getSecondPartLocation() {
		return secondPartList.get(secondIndex);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawRect(x, y, width, height - 13);
	}

}
