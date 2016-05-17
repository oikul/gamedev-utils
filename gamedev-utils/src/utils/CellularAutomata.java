package utils;

public class CellularAutomata {
	
	private static boolean[][] cells;
	
	public static boolean[][] getAutomataNoise(int width, int height, int birthLimit, int deathLimit, float startChance, int steps){
		initialiseArray(width, height, startChance);
		
		return cells;
	}
	
	private static void initialiseArray(int width, int height, float startChance){
		cells = new boolean[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0 ; j < height; j++){
				cells[i][j] = MathHelper.random.nextFloat() < startChance;
			}
		}
	}
	
	

}
