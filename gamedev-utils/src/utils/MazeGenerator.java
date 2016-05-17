package utils;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {

	private Cell[][] cells;
	private ArrayList<Cell> visited = new ArrayList<Cell>();

	public MazeGenerator(int width, int height) {
		cells = new Cell[width][height];
	}

	public void generate(long seed) {
		Random random = new Random(seed);
		int startI = random.nextInt(cells.length);
		int startJ = random.nextInt(cells[0].length);
		visited.add(cells[startI][startJ]);
		while (visited.size() < cells.length * cells[0].length) {
			int count = 0;
			if (random.nextBoolean()) { // go left or right
				int nextI = startI + (random.nextInt(3) - 1);
				count = 1;
				while (visited.contains(cells[nextI][startJ]) && count < 3) {
					nextI = startI + (random.nextInt(3) - 1);
					count++;
				}
				if (count == 3) {
					
				} else {
					if (nextI > 0 && nextI < cells.length) {
						if (nextI > startI) {
							cells[startI][startJ].setRight(false);
							cells[nextI][startJ].setLeft(false);
						} else {
							cells[startI][startJ].setLeft(false);
							cells[nextI][startJ].setRight(false);
						}
						visited.add(cells[nextI][startJ]);
					}
				}
			} else { // go up or down
				int nextJ = startJ + (random.nextInt(3) - 1);
				count = 1;
				while (visited.contains(cells[startI][nextJ]) && count < 3) {
					nextJ = startJ + (random.nextInt(3) - 1);
					count++;
				}
				if (count == 3) {
					
				} else {
					if (nextJ > 0 && nextJ < cells.length) {
						if (nextJ > startJ) {
							cells[startI][startJ].setDown(false);
							cells[startI][nextJ].setUp(false);
						} else {
							cells[startI][startJ].setUp(false);
							cells[startI][nextJ].setDown(false);
						}
						visited.add(cells[startI][nextJ]);
					}
				}
			}
		}
	}

	public void draw(Graphics g) {

	}

	private class Cell {

		private boolean up = true, left = true, down = true, right = true;

		public boolean isUp() {
			return up;
		}

		public void setUp(boolean up) {
			this.up = up;
		}

		public boolean isLeft() {
			return left;
		}

		public void setLeft(boolean left) {
			this.left = left;
		}

		public boolean isDown() {
			return down;
		}

		public void setDown(boolean down) {
			this.down = down;
		}

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
		}
	}
}
