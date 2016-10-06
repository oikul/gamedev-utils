package generators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import blocks.Block;

public class MazeGenerator {

	private int width, height, size = 64;
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
		//west[1][1] = false;
		//east[width][height] = false;
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

	public void draw(Graphics2D g2d, int mult) {
		for(int i = 4; i < (size + 2)*2; i++){
			for(int j = 4; j < (size + 2)*2; j++){
				Block.stone.draw(g2d, i*16 * mult, j*16 * mult, mult);
			}
		}
		for (int i = 1; i <= width; i++) {
			for (int j = 1; j <= height; j++) {
				Block.rock.draw(g2d, i * size * mult, j * size * mult, mult);
				if (south[i][j]) {
					drawLine(g2d, (i * size), (j * size), ((i + 1) * size), (j * size), mult);
				}
				if (north[i][j]) {
					drawLine(g2d, i * size, (j + 1) * size, (i + 1) * size, (j + 1) * size, mult);
				}
				if (west[i][j]) {
					drawLine(g2d, i * size, j * size, i * size, (j + 1) * size, mult);
				}
				if (east[i][j]) {
					drawLine(g2d, (i + 1) * size, j * size, (i + 1) * size, (j + 1) * size, mult);
				}
				if (treasure[i][j]) {
					g2d.setColor(Color.yellow);
					//g2d.fillRect(i * size + 1, j * size + 1, 16 - 1, 16 - 1);
				}
			}
		}
	}
	
	private void drawLine(Graphics2D g2d, int startx, int starty, int endx, int endy, int mult){
		for(int i = startx; i < endx; i+=16){
			Block.rock.draw(g2d, i * mult, starty * mult, mult);
		}
		for(int j = starty; j < endy; j+=16){
			Block.rock.draw(g2d, startx * mult, j * mult, mult);
		}
		
	}
	
}