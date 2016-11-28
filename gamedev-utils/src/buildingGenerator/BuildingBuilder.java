package buildingGenerator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.lwjgl.util.Rectangle;

import handlers.ResourceHandler;
import mapMaker2D.Settings;

public class BuildingBuilder {

	private final int WOODFLOOR = 0;
	private final int WOODWALL = 1;
	private final int STONEFLOOR = 2;
	private final int STONEWALL = 3;
	private final int PATH = 4;

	private BufferedImage[] pics;

	private int[][] floorPlan;

	public BuildingBuilder(long seed) {
		Main.random = new Random(seed);

		int width = Main.random.nextInt(50) + 50;
		int height = Main.random.nextInt(50) + 50;

		floorPlan = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				floorPlan[x][y] = 0;

			}
		}

		pics = new BufferedImage[4];
		loadPics();

		genBuilding(floorPlan, new Rectangle(0, 0, floorPlan.length, floorPlan[0].length), Main.random.nextBoolean());
	}

	private void loadPics() {

		pics[0] = ResourceHandler.getBufferedImage("BuildingTiles/woodFloor");
		pics[1] = ResourceHandler.getBufferedImage("BuildingTiles/woodWall");
		pics[2] = ResourceHandler.getBufferedImage("BuildingTiles/stoneFloor");
		pics[3] = ResourceHandler.getBufferedImage("BuildingTiles/stoneWall");

	}

	private void genBuilding(int[][] plan, Rectangle area, Boolean splitWidth) {

		if (area.getWidth() - 13 <= 0 || area.getHeight() - 13 <= 0) {
			fillRoom(plan, area);
			return;
		}

		if (area.getHeight() < area.getWidth()) {
			splitWidth = false;
		} else {
			splitWidth = true;
		}

		Rectangle area1;
		Rectangle area2;
		if (splitWidth) {

			int y = Main.random.nextInt(area.getHeight() - 13) + 6;
			for (int i = area.getX(); i < area.getX() + area.getWidth(); i++) {

				floorPlan[i][area.getY() + y] = STONEFLOOR;
				floorPlan[i][area.getY() + y + 1] = STONEFLOOR;

			}
			area1 = new Rectangle(area.getX(), area.getY(), area.getWidth(), y);
			area2 = new Rectangle(area.getX(), area.getY() + y + 2, area.getWidth(), area.getHeight() - y - 2);

		} else {

			int x = Main.random.nextInt(area.getWidth() - 13) + 6;
			for (int i = area.getY(); i < area.getY() + area.getHeight(); i++) {

				floorPlan[area.getX() + x][i] = STONEFLOOR;
				floorPlan[area.getX() + x + 1][i] = STONEFLOOR;

			}

			area1 = new Rectangle(area.getX(), area.getY(), x, area.getHeight());
			area2 = new Rectangle(area.getX() + x + 2, area.getY(), area.getWidth() - x - 2, area.getHeight());
		}

		genBuilding(floorPlan, area1, !splitWidth);
		genBuilding(floorPlan, area2, !splitWidth);

	}

	private void fillRoom(int[][] plan, Rectangle area) {
		makeWall(plan, area);
	}

	private void makeWall(int[][] plan, Rectangle area) {
		boolean hasDoor = false;

		float prob = 1f / (area.getHeight() * 2 + area.getWidth() * 2);
		float probAdder = prob;

		int y1 = area.getY();
		int y2 = area.getY() + area.getHeight() - 1;
		for (int x = area.getX(); x < area.getX() + area.getWidth(); x++) {
			if (!hasDoor && Main.random.nextFloat() < prob) {
				hasDoor = true;
				if (Main.random.nextBoolean()) {
					floorPlan[x][y1] = STONEFLOOR;
					floorPlan[x][y2] = WOODWALL;
				} else {
					floorPlan[x][y1] = WOODWALL;
					floorPlan[x][y2] = STONEFLOOR;
				}
			}else{
				floorPlan[x][y1] = WOODWALL;
				floorPlan[x][y2] = WOODWALL;
			}
			prob += probAdder;
		}
		int x1 = area.getX();
		int x2 = area.getX() + area.getWidth() - 1;
		for (int y = area.getY(); y < area.getY() + area.getHeight(); y++) {
			if (!hasDoor && Main.random.nextFloat() < prob) {
				hasDoor = true;
				if (Main.random.nextBoolean()) {
					floorPlan[x1][y] = STONEFLOOR;
					floorPlan[x2][y] = WOODWALL;
				} else {
					floorPlan[x1][y] = WOODWALL;
					floorPlan[x2][y] = STONEFLOOR;
				}
			}else{
				floorPlan[x1][y] = WOODWALL;
				floorPlan[x2][y] = WOODWALL;
			}
			prob += probAdder;
		}
		
		if(!hasDoor){
			floorPlan[area.getX() + area.getWidth()/2][area.getY()] = STONEFLOOR;
		}

	}

	public void update() {

	}

	public void draw(Graphics g) {

		int size = Main.tileSize;
		int dx = (int) Main.XOffset * size;
		int dy = (int) Main.YOffset * size;

		for (int x = 0; x < floorPlan.length; x++) {
			for (int y = 0; y < floorPlan[0].length; y++) {
				g.drawImage(pics[floorPlan[x][y]], dx + x * size, dy + y * size, dx + x * size + (size),
						dy + y * size + (size), 0, 0, 16, 16, null);
			}
		}

	}
}
