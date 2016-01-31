package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class PlanetryBody {
	
	protected Point2D.Double position;
	protected double angle, distance, size;
	protected Color color;
	protected boolean selected = false;

	public PlanetryBody(double distance, double angle, double size, Color color) {
		setDistance(distance);
		setAngle(angle);
		setSize(size);
		setColor(color);
		getXAndY();
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g2d){
		
	}

	protected void getXAndY(){
		position = MathHelper.convertPolarToCartesian(angle, distance, InputHandler.midPoint.x, InputHandler.midPoint.y);
	}
	
	public void incrementAngle(double amount){
		if(angle < 360){
			angle += amount;
		}else{
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

}
