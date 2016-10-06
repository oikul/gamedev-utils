package generators;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import handlers.MathHandler;

public class NoiseGenerator {

	private Random random;
	private int width, height;
	
	public NoiseGenerator(long seed) {
		random = new Random(seed);
	}

	/**
	 * creates a 2D array of perlin noise recommended values for octaves are 4
	 * and 5
	 * 
	 * @param width
	 *            the number of sampling points horizontally
	 * @param height
	 *            the number of sampling points vertically
	 * @param octave1
	 *            the first octave for smoothing
	 * @param octave2
	 *            the second octave for perlin
	 * @return a 2D array of perlin noise
	 */
	public float[][] getPerlinNoise(int width, int height, int octave1, int octave2) {
		this.width = width;
		this.height = height;
		return genPerlinNoise(genSmoothNoisePerlin(genWhiteNoise(width, height), octave1), octave2);
	}

	/**
	 * generates some smooth noise by getting the average of nearby randomly
	 * generated numbers
	 * 
	 * @param width
	 *            the first size of the array
	 * @param height
	 *            the second size of the array
	 * @param octave
	 *            the number of times the smoothing process is repeated
	 * @return a 2D array of noise
	 */
	public float[][] getSmoothNoise(int width, int height, int octave) {
		this.width = width;
		this.height = height;
		return genSmoothNoise(genWhiteNoise(width, height), octave);
	}

	

	private float[][] genWhiteNoise(int width, int height) {
		float[][] noise = new float[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				noise[i][j] = random.nextFloat();
			}
		}
		return noise;
	}

	private float[][] genSmoothNoise(float[][] whiteNoise, int octaves) {
		float[][] smoothNoise = new float[width][height];
		for (int repeat = 0; repeat < octaves; repeat++) {
			float[][] noise = new float[width][height];
			for (int i = 0; i < width - 1; i++) {
				for (int j = 0; j < height - 1; j++) {
					if (i > 0 && j > 0 && i < width && j < height) {
						noise[i][j] = (float) MathHandler.smooth9(whiteNoise[i - 1][j - 1], whiteNoise[i - 1][j],
								whiteNoise[i - 1][j + 1], whiteNoise[i][j - 1], whiteNoise[i][j], whiteNoise[i][j + 1],
								whiteNoise[i + 1][j - 1], whiteNoise[i + 1][j], whiteNoise[i + 1][j + 1]);
					} else if (i == 0 && j == 0) {
						noise[i][j] = (float) MathHandler.smooth4(whiteNoise[i][j], whiteNoise[i][j + 1], whiteNoise[i + 1][j],
								whiteNoise[i + 1][j + 1]);
					} else if (i == 0 && j == height) {
						noise[i][j] = (float) MathHandler.smooth4(whiteNoise[i][j - 1], whiteNoise[i][j],
								whiteNoise[i + 1][j - 1], whiteNoise[i + 1][j]);
					} else if (i == width && j == 0) {
						noise[i][j] = (float) MathHandler.smooth4(whiteNoise[i - 1][j], whiteNoise[i - 1][j + 1],
								whiteNoise[i][j], whiteNoise[i][j + 1]);
					} else if (i == 0) {
						noise[i][j] = (float) MathHandler.smooth6(whiteNoise[i][j - 1], whiteNoise[i][j], whiteNoise[i][j + 1],
								whiteNoise[i + 1][j - 1], whiteNoise[i + 1][j], whiteNoise[i + 1][j + 1]);
					} else if (j == 0) {
						noise[i][j] = (float) MathHandler.smooth6(whiteNoise[i - 1][j], whiteNoise[i - 1][j + 1],
								whiteNoise[i][j], whiteNoise[i][j + 1], whiteNoise[i + 1][j], whiteNoise[i + 1][j + 1]);
					} else if (i == width && j == height) {
						noise[i][j] = (float) MathHandler.smooth4(whiteNoise[i - 1][j - 1], whiteNoise[i - 1][j],
								whiteNoise[i][j - 1], whiteNoise[i][j]);
					} else if (i == width) {
						noise[i][j] = (float) MathHandler.smooth6(whiteNoise[i - 1][j - 1], whiteNoise[i - 1][j],
								whiteNoise[i - 1][j + 1], whiteNoise[i][j - 1], whiteNoise[i][j], whiteNoise[i][j + 1]);
					} else if (j == height) {
						noise[i][j] = (float) MathHandler.smooth6(whiteNoise[i - 1][j - 1], whiteNoise[i - 1][j],
								whiteNoise[i][j - 1], whiteNoise[i][j], whiteNoise[i + 1][j - 1], whiteNoise[i + 1][j]);
					}
					smoothNoise[i][j] += noise[i][j];
				}
			}
		}
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height - 1; j++) {
				smoothNoise[i][j] = smoothNoise[i][j] / octaves;
			}
		}
		return smoothNoise;
	}

	private float[][] genSmoothNoisePerlin(float[][] baseNoise, int octave) {
		float[][] smoothNoise = new float[width][height];
		int samplePeriod = 1 << octave;
		float sampleFrequency = 1.0f / samplePeriod;
		for (int i = 0; i < width; i++) {
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width;
			float horizontal_blend = (i - sample_i0) * sampleFrequency;
			for (int j = 0; j < height; j++) {
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height;
				float vertical_blend = (j - sample_j0) * sampleFrequency;
				float top = (float) MathHandler.cosineInterpolate(baseNoise[sample_i0][sample_j0],
						baseNoise[sample_i1][sample_j0], horizontal_blend);
				float bottom = (float) MathHandler.cosineInterpolate(baseNoise[sample_i0][sample_j1],
						baseNoise[sample_i1][sample_j1], horizontal_blend);
				smoothNoise[i][j] = (float) MathHandler.cosineInterpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}

	private float[][] genPerlinNoise(float[][] baseNoise, int octaveCount) {
		float[][][] smoothNoise = new float[octaveCount][][];
		float persistance = 0.5f;
		for (int i = 0; i < octaveCount; i++) {
			smoothNoise[i] = genSmoothNoisePerlin(baseNoise, i);
		}
		float[][] perlinNoise = new float[width][height];
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		for (int octave = octaveCount - 1; octave >= 0; octave--) {
			amplitude *= persistance;
			totalAmplitude += amplitude;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				perlinNoise[i][j] /= totalAmplitude;
			}
		}
		return perlinNoise;
	}

	

	public Rectangle[] generateRooms(int numOfRooms, int xLimit, int yLimit, int xMod, int yMod, int widthLimit,
			int heightLimit, int widthMod, int heightMod, int corridorSize) {
		ArrayList<Rectangle> rooms = new ArrayList<Rectangle>();
		for (int i = 0; i < numOfRooms; i++) {
			rooms.add(new Rectangle(MathHandler.random.nextInt(xLimit) + xMod, MathHandler.random.nextInt(yLimit) + yMod,
					MathHandler.random.nextInt(widthLimit) + widthMod,
					MathHandler.random.nextInt(heightLimit) + heightMod));
		}
		int pls = rooms.size();
		for (int i = 0; i < pls; i++) {
			for (int j = 0; j < pls; j++) {
				Rectangle r1 = rooms.get(i), r2 = rooms.get(j);
				int x, y, width, height;
				if (r1.x < r2.x) {
					x = r1.x + r1.width / 2;
					width = (r2.x + r2.width / 2) - x;
				} else {
					x = r2.x + r2.width / 2;
					width = (r1.x + r1.width / 2) - x;
				}
				if (r1.y < r2.y) {
					y = r1.y + r1.height / 2;
					height = (r2.y + r2.height / 2) - y;
				} else {
					y = r2.y + r2.height / 2;
					height = (r1.y + r1.height / 2) - y;
				}
				rooms.add(new Rectangle(x, y, width, corridorSize));
				rooms.add(new Rectangle(x, y, corridorSize, height));
			}
		}
		return rooms.toArray(new Rectangle[rooms.size()]);
	}
}