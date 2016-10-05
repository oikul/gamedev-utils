package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class MenuButton {

	private Polygon shape, shadow;
	private Color color, textColor;
	private Font textFont;
	private String text;
	private boolean hover, fontSet;

	public MenuButton(Rectangle shape) {
		int x1, y1, x2, y2;
		int shadowDist = 10;
		x1 = shape.x;
		x2 = shape.x + shape.width;
		y1 = shape.y;
		y2 = shape.y + shape.height;
		this.shape = new Polygon(new int[] { x1, x1, x2, x2 }, new int[] { y1, y2, y2, y1 }, 4);
		this.shadow = new Polygon(new int[] { x1 + shadowDist, x1 + shadowDist, x2 + shadowDist, x2 + shadowDist },
				new int[] { y1 + shadowDist, y2 + shadowDist, y2 + shadowDist, y1 + shadowDist }, 4);
		this.color = Color.white;
		this.textColor = Color.black;
	}

	public MenuButton(Rectangle shape, int shadowDist, Color color) {
		int x1, y1, x2, y2;
		x1 = shape.x;
		x2 = shape.x + shape.width;
		y1 = shape.y;
		y2 = shape.y + shape.height;
		this.shape = new Polygon(new int[] { x1, x1, x2, x2 }, new int[] { y1, y2, y2, y1 }, 4);
		this.shadow = new Polygon(new int[] { x1 + shadowDist, x1 + shadowDist, x2 + shadowDist, x2 + shadowDist },
				new int[] { y1 + shadowDist, y2 + shadowDist, y2 + shadowDist, y1 + shadowDist }, 4);
		this.color = color;
		this.textColor = Color.black;
	}

	public MenuButton(Polygon poly) {

		int shadowDist = 10;
		this.shape = poly;
		setShadowDist(shadowDist);
		this.color = Color.white;
		this.textColor = Color.black;

	}

	public MenuButton(Polygon poly, int shadowDist, Color color) {

		this.shape = poly;
		setShadowDist(shadowDist);
		this.color = color;
		this.textColor = Color.black;

	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setFont(Font font) {
		fontSet = true;
		textFont = font;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setTextColor(Color color){
		this.textColor = color;
	}

	public void setShadowDist(int shadowDist) {
		this.shadow = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);
		this.shadow.translate(shadowDist, shadowDist);
	}

	public void update(Point mouseCoords) {
		if (shape.contains(mouseCoords)) {
			hover = true;
		}
	}

	public void draw(Graphics g) {
		if (hover) {
			g.setColor(Color.black);
			g.drawPolygon(shadow);
		}
		g.setColor(color);
		g.drawPolygon(shape);
		if (fontSet) {
			g.setFont(textFont);
		}
		g.setColor(textColor);
		g.setFont(textFont);
		Rectangle bounds = shape.getBounds();
		int x = bounds.x;
		int y = bounds.y;
		int width = bounds.width;
		int height = bounds.height;
		FontMetrics met = g.getFontMetrics();
		int stringWidth = met.stringWidth(text);
		int stringHeight = met.getHeight();
		g.drawString(text, x+(width-stringWidth)/2, y+(height-stringHeight)/2);
	}

}
