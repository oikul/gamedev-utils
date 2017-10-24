package generators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import blocks.Block;

public class MazeGenerator {

	private int width, height, size;
	private boolean[][] north, east, south, west, visited, treasure;
	private Random random;
	private BufferedImage maze;
	private boolean drawn = false;

	public MazeGenerator(int width, int height, int size, long seed) {
		this.width = width;
		this.height = height;
		this.size = size;
		random = new Random(seed);
	}

	public void initialise() {
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
		maze = new BufferedImage(width * size * 4, height * size * 4, BufferedImage.TYPE_INT_ARGB);
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

	public void draw(Graphics2D g2d, Block wall, Block floor, int mult) {
		if (!drawn) {
			Graphics2D offGraphics = (Graphics2D) maze.getGraphics();
			for (int i = 4; i <= (size + 4); i++) {
				for (int j = 4; j <= (size + 4); j++) {
					floor.draw(offGraphics, i * 16 * mult, j * 16 * mult, mult);
				}
			}
			for (int i = 1; i <= width; i++) {
				for (int j = 1; j <= height; j++) {
					wall.draw(offGraphics, i * size * mult, j * size * mult, mult);
					if (south[i][j]) {
						drawLine(offGraphics, (i * size), (j * size), ((i + 1) * size), (j * size), mult, wall);
					}
					if (north[i][j]) {
						drawLine(offGraphics, i * size, (j + 1) * size, (i + 1) * size, (j + 1) * size, mult, wall);
					}
					if (west[i][j]) {
						drawLine(offGraphics, i * size, j * size, i * size, (j + 1) * size, mult, wall);
					}
					if (east[i][j]) {
						drawLine(offGraphics, (i + 1) * size, j * size, (i + 1) * size, (j + 1) * size, mult, wall);
					}
					if (treasure[i][j]) {
						offGraphics.setColor(Color.yellow);
						// g2d.fillRect(i * size + 1, j * size + 1, 16 - 1, 16 -
						// 1);
					}
				}
			}
			drawn = true;
		}
		g2d.drawImage(maze, 0, 0, maze.getWidth(), maze.getHeight(), null);
	}

	private void drawLine(Graphics2D g2d, int startx, int starty, int endx, int endy, int mult, Block wall) {
		for (int i = startx; i < endx; i += 16) {
			wall.draw(g2d, i * mult, starty * mult, mult);
		}
		for (int j = starty; j < endy; j += 16) {
			wall.draw(g2d, startx * mult, j * mult, mult);
		}

	}

}
