package generators;

import java.util.Random;

public class CellularAutomata {
	
	private static Random random = new Random();

	/**
	 * generates a 2D array of boolean values using cellular-automata
	 * 
	 * @param width
	 *            the first size of the array
	 * @param height
	 *            the second size of the array
	 * @param birthLimit
	 *            the number of true values next to the current node before it
	 *            can't spread
	 * @param deathLimit
	 *            how many nodes have to be next to it before it can't die
	 * @param numberOfSteps
	 *            the number of times the algorithm runs
	 * @return a 2D array of boolean values
	 */
	public boolean[][] getCellularAutomataNoise(int width, int height, int birthLimit, int deathLimit,
			int numberOfSteps) {
		return genCellularAutomataNoise(width, height, birthLimit, deathLimit, numberOfSteps);
	}

	private boolean[][] genCellularAutomataNoise(int width, int height, int birthLimit, int deathLimit,
			int numberOfSteps) {
		boolean[][] automata = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				automata[i][j] = random.nextBoolean();
			}
		}
		while (numberOfSteps > 0) {
			doStep(automata, birthLimit, deathLimit, width, height);
			numberOfSteps--;
		}
		return automata;
	}

	private void doStep(boolean[][] automata, int birthLimit, int deathLimit, int width, int height) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int count = 0;
				if (i > 0 && j > 0) {
					if (automata[i - 1][j - 1]) {
						count++;
					}
				}
				if (i > 0) {
					if (automata[i - 1][j]) {
						count++;
					}
					if (j + 1 < height) {
						if (automata[i - 1][j + 1]) {
							count++;
						}
					}
				}
				if (j > 0) {
					if (automata[i][j - 1]) {
						count++;
					}
					if (i + 1 < width) {
						if (automata[i + 1][j - 1]) {
							count++;
						}
					}
				}
				if (j + 1 < height) {
					if (automata[i][j + 1]) {
						count++;
					}
				}
				if (i + 1 < width) {
					if (automata[i + 1][j]) {
						count++;
					}
				}
				if (i + 1 < width && j + 1 < height) {
					if (automata[i + 1][j + 1]) {
						count++;
					}
				}
				if (count <= deathLimit && automata[i][j]) {
					automata[i][j] = false;
				}
				if (count >= birthLimit && !automata[i][j]) {
					automata[i][j] = true;
				}
			}
		}
	}
	
	public static void setSeed(long seed){
		random.setSeed(seed);
	}

}
