package generators;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.lwjgl.util.Rectangle;

import handlers.ResourceHandler;
import mapMaker2D.Settings;

public class BuildingGenerator {

	private final int WOODFLOOR = 0;
	private final int WOODWALL = 1;
	private final int STONEFLOOR = 2;
	private final int STONEWALL = 3;
	private final int DOOR = 2; 
	private final int PATH = 4;

	private BufferedImage[] pics;

	private int[][] floorPlan;

	public BuildingGenerator(long seed) {
		BuildingTest.random = new Random(seed);

		int width = BuildingTest.random.nextInt(50) + 50;
		int height = BuildingTest.random.nextInt(50) + 50;

		floorPlan = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				floorPlan[x][y] = 0;

			}
		}

		pics = new BufferedImage[4];
		loadPics();

		floorPlan = genBuilding(floorPlan, new Rectangle(0, 0, floorPlan.length, floorPlan[0].length), BuildingTest.random.nextBoolean());
	}

	private void loadPics() {

		pics[0] = ResourceHandler.getBufferedImage("BuildingTiles/woodFloor");
		pics[1] = ResourceHandler.getBufferedImage("BuildingTiles/woodWall");
		pics[2] = ResourceHandler.getBufferedImage("BuildingTiles/stoneFloor");
		pics[3] = ResourceHandler.getBufferedImage("BuildingTiles/stoneWall");

	}

	public int[][] genBuilding(int[][] plan, Rectangle area, Boolean splitWidth) {

		if (area.getWidth() - 13 <= 0 || area.getHeight() - 13 <= 0) {
			fillRoom(plan, area);
			return plan;
		}

		if (area.getHeight() < area.getWidth()) {
			splitWidth = false;
		} else {
			splitWidth = true;
		}
		
		if(area.getWidth() == plan.length){
			splitWidth = false;
		}
		if(area.getWidth() == plan.length){
			
		}

		Rectangle area1;
		Rectangle area2;
		if (splitWidth) {

			int y = BuildingTest.random.nextInt(area.getHeight() - 13) + 6;
			for (int i = area.getX(); i < area.getX() + area.getWidth(); i++) {

				plan[i][area.getY() + y] = STONEFLOOR;
				plan[i][area.getY() + y + 1] = STONEFLOOR;

			}
			area1 = new Rectangle(area.getX(), area.getY(), area.getWidth(), y);
			area2 = new Rectangle(area.getX(), area.getY() + y + 2, area.getWidth(), area.getHeight() - y - 2);

		} else {

			int x = BuildingTest.random.nextInt(area.getWidth() - 13) + 6;
			for (int i = area.getY(); i < area.getY() + area.getHeight(); i++) {

				plan[area.getX() + x][i] = STONEFLOOR;
				plan[area.getX() + x + 1][i] = STONEFLOOR;

			}

			area1 = new Rectangle(area.getX(), area.getY(), x, area.getHeight());
			area2 = new Rectangle(area.getX() + x + 2, area.getY(), area.getWidth() - x - 2, area.getHeight());
		}

		genBuilding(plan, area1, !splitWidth);
		genBuilding(plan, area2, !splitWidth);
		return plan;

	}

	private int[][] fillRoom(int[][] plan, Rectangle area) {
		return makeWall(plan, area);
	}

	private int[][] makeWall(int[][] plan, Rectangle area) {

		int y1 = area.getY();
		int y2 = area.getY() + area.getHeight() - 1;
		for (int x = area.getX(); x < area.getX() + area.getWidth(); x++) {
			plan[x][y1] = WOODWALL;
			plan[x][y2] = WOODWALL;
		}
		
		int x1 = area.getX();
		int x2 = area.getX() + area.getWidth() - 1;
		for (int y = area.getY(); y < area.getY() + area.getHeight(); y++) {
			plan[x1][y] = WOODWALL;
			plan[x2][y] = WOODWALL;
		}
		
		
		
		int width = area.getWidth();
		int height = area.getHeight();
		boolean hallup = false,halldown = false,hallleft = false,hallright = false;

		int halls = checkHall(plan,area);
		// 1 = up, 2 = down, 4 = right, 8 = left
		int walls = 0;
		if(halls > 8){
			halls -= 8;
			hallleft = true;
			walls += area.getWidth()-2;
		}
		if(halls > 4){
			halls -= 4;
			hallright = true;
			walls += area.getWidth()-2;
		}
		if(halls > 2){
			halls -=  2;
			halldown = true;
			walls += area.getHeight()-2;
		}
		if(halls > 1){
			halls -= 1;
			hallup = true;
			walls += area.getHeight()-2;
		}
		int doorLoc = BuildingTest.random.nextInt(walls);
		checkHall(plan,area);
		
		if(doorLoc > width-3 && !hallup){
			doorLoc -= width-2;
		}else {
			plan[area.getX() + doorLoc+1][area.getY()] = DOOR;
			return plan;
		}
		if (doorLoc > width-3 && !halldown){
			doorLoc -= width-2;
		}else {
			plan[area.getX() + doorLoc+1][area.getY() + height-1] = DOOR;
			return plan;
		}
		if (doorLoc > height-3 && !hallleft){
			doorLoc -= height-2;
		}else {
			plan[area.getX()][area.getY() + doorLoc+1] = DOOR;
			return plan;
		}
		if (doorLoc > height-3 && !hallright){
			System.err.println("error with maths");
		}else{
			plan[area.getX() + width-1][area.getY() + doorLoc+1] = DOOR;
			return plan;
		}
		return plan;

	}
	
	private int checkHall(int[][] plan,Rectangle area){
		
		int num = 0;
		try{
		if(plan[area.getX()][area.getY()-1] == STONEFLOOR){
			num +=1;
		}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
		if(plan[area.getX()][area.getY()+area.getHeight()] == STONEFLOOR){
			num +=2;
		}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
		if(plan[area.getX()+ area.getWidth()][area.getY()] == STONEFLOOR){
			num +=4;
		}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		try{
		if(plan[area.getX()-1][area.getY()] == STONEFLOOR){
			num +=8;
		}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
				
		return num;
		
	}

	public void update() {

	}

	public void draw(Graphics g) {

		int size = BuildingTest.tileSize;
		int dx = (int) BuildingTest.XOffset;
		int dy = (int) BuildingTest.YOffset;

		for (int x = 0; x < floorPlan.length; x++) {
			for (int y = 0; y < floorPlan[0].length; y++) {
				g.drawImage(pics[floorPlan[x][y]], dx + x * size, dy + y * size, dx + x * size + (size),
						dy + y * size + (size), 0, 0, 16, 16, null);
			}
		}

	}
}
