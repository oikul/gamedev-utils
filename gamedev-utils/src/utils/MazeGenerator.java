package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class MazeGenerator {

	private int width, height;
	private boolean[][] north, east, south, west, visited, treasure;
	private Random random;

	public MazeGenerator(int width, int height, long seed) {
		this.width = width;
		this.height = height;
		random = new Random(seed);
		initialise();
	}

	private void initialise() {
		visited = new boolean[width + 2][height + 2];
		for (int i = 0; i < width + 2; i++) {
			visited[i][0] = true;
			visited[i][height + 1] = true;
		}
		for (int j = 0; j < height + 2; j++) {
			visited[0][j] = true;
			visited[width + 1][j] = true;
		}
		north = new boolean[width + 2][height + 2];
		east = new boolean[width + 2][height + 2];
		south = new boolean[width + 2][height + 2];
		west = new boolean[width + 2][height + 2];
		treasure = new boolean[width + 2][height + 2];
		for (int i = 0; i < width + 2; i++) {
			for (int j = 0; j < height + 2; j++) {
				north[i][j] = true;
				east[i][j] = true;
				south[i][j] = true;
				west[i][j] = true;
				if (random.nextDouble() < 0.005) {
					treasure[i][j] = true;
				} else {
					treasure[i][j] = false;
				}
			}
		}
		west[1][1] = false;
		east[width][height] = false;
	}

	public void generate(int x, int y) {
		visited[x][y] = true;
		while (!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1] || !visited[x - 1][y]) {
			while (true) {
				int r = random.nextInt(4);
				if (r == 0 && !visited[x][y + 1]) {
					north[x][y] = false;
					south[x][y + 1] = false;
					generate(x, y + 1);
					break;
				} else if (r == 1 && !visited[x + 1][y]) {
					east[x][y] = false;
					west[x + 1][y] = false;
					generate(x + 1, y);
					break;
				} else if (r == 2 && !visited[x][y - 1]) {
					south[x][y] = false;
					north[x][y - 1] = false;
					generate(x, y - 1);
					break;
				} else if (r == 3 && !visited[x - 1][y]) {
					west[x][y] = false;
					east[x - 1][y] = false;
					generate(x - 1, y);
					break;
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				g.setColor(Color.black);
				if (south[i][j]) {
					g.drawLine(i * 16, j * 16, (i + 1) * 16, j * 16);
				}
				if (north[i][j]) {
					g.drawLine(i * 16, (j + 1) * 16, (i + 1) * 16, (j + 1) * 16);
				}
				if (west[i][j]) {
					g.drawLine(i * 16, j * 16, i * 16, (j + 1) * 16);
				}
				if (east[i][j]) {
					g.drawLine((i + 1) * 16, j * 16, (i + 1) * 16, (j + 1) * 16);
				}
				if (treasure[i][j]) {
					g.setColor(Color.yellow);
					g.fillRect(i * 16 + 1, j * 16 + 1, 15, 15);
				}
			}
		}
	}

}
