package generators;

import java.util.Random;

import handlers.MathHandler;

public class SmoothNoiseGenerator {
	
	private Random random;
	private int width, height;
	
	public SmoothNoiseGenerator(long seed){
		random = new Random(seed);
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
	
	private float[][] genWhiteNoise(int width, int height) {
		float[][] noise = new float[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				noise[i][j] = random.nextFloat();
			}
		}
		return noise;
	}

}
