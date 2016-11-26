package buildingGenerator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class BuildingBuilder {
	
	private final int WOODFLOOR = 0;
	private final int WOODWALL = 1;
	private final int STONEFLOOR = 2;
	private final int STONEWALL = 3;
	
	private int[][] floorPlan;
	private Random rad;
	
	public BuildingBuilder(long seed) {
		rad = new Random(seed);
		
		int width = rad.nextInt(100);
		int height = rad.nextInt(100);
		
		floorPlan = new int[width][height];
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				floorPlan[x][y] = 0;
				
			}
		}
		
		floorPlan = genBuilding(floorPlan);
	}
	
	private int[][] genBuilding(int[][] plan){
		
		int levels = rad.nextInt(3)+1;
		
		split(plan,0,levels);
		
		return plan;
	}
	
	private void split(int[][] plan,int level,int maxLevel){
		
		int lev = rad.nextInt(3)+1;
		
		if(level == maxLevel || lev == level){
			return fillPlan(plan);
		}
		
		//split the array
		
		split(plan,level+1,maxLevel);
		
	}
	
	private int[][] fillPlan(int[][] plan){
		//fill the array 
		return plan;
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		
		g.setColor(Color.blue);
		g.fillRect(100, 100, 11, 11);
		
	}

}
