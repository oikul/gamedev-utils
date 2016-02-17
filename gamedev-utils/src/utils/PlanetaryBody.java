package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class PlanetaryBody {

	protected Point2D.Double position;
	protected double angle, distance, size, xDif = 0.0, yDif = 0.0, zoom = 1.0;
	protected Color color;
	protected boolean selected = false, discovered = false;
	protected String name = "";

	public PlanetaryBody(double distance, double angle, double size, Color color) {
		setDistance(distance);
		setAngle(angle);
		setSize(size);
		setColor(color);
		getXAndY();
	}

	public void update() {

	}

	public void draw(Graphics2D g2d) {

	}

	public void panUp(double amount) {
		yDif += amount;
	}

	public void panLeft(double amount) {
		xDif += amount;
	}

	public void panDown(double amount) {
		yDif -= amount;
	}
	
	public void panRight(double amount) {
		xDif -= amount;
	}
	
	public void panUL(double amount) {
		yDif += amount;
		xDif += amount;
	}
	
	public void panUR(double amount) {
		yDif += amount;
		xDif -= amount;
	}
	
	public void panDL(double amount) {
		yDif -= amount;
		xDif += amount;
	}
	
	public void panDR(double amount) {
		yDif -= amount;
		xDif -= amount;
	}
	
	public void getXAndY() {
		position = MathHelper.convertPolarToCartesian(angle, distance, InputHandler.midPoint.x,
				InputHandler.midPoint.y);
	}

	public void incrementAngle(double amount) {
		if (angle < 360) {
			angle += amount;
		} else {
			angle = 0;
		}
	}

	public Point2D.Double getPosition() {
		return position;
	}

	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public double getxDif() {
		return xDif;
	}

	public void setxDif(double xDif) {
		this.xDif = xDif;
	}

	public void increaseXDif(double amount) {
		xDif += amount;
	}

	public double getyDif() {
		return yDif;
	}

	public void setyDif(double yDif) {
		this.yDif = yDif;
	}

	public void increaseYDif(double amount) {
		yDif += amount;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	public void incrementZoom(double amount){
		zoom += amount;
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
