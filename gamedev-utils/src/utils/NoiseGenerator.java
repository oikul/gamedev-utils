package utils;

import java.util.Random;

public class NoiseGenerator {

	private Random random;
	private int width, height;

	public NoiseGenerator(long seed){
		random = new Random(seed);
	}

	/**
	 * creates a 2D array of perlin noise recommended values for octaves are 4 and 5
	 * @param width the number of sampling points horizontally
	 * @param height the number of sampling points vertically
	 * @param octave1 the first octave for smoothing
	 * @param octave2 the second octave for perlin
	 * @return a 2D array of perlin noise
	 */
	public double[][] getPerlinNoise(int width, int height, int octave1, int octave2){
		this.width = width;
		this.height = height;
		return genPerlinNoise(genSmoothNoisePerlin(genWhiteNoise(width, height), octave1), octave2);
	}

	/**
	 * generates some smooth noise by getting the average of nearby randomly generated numbers
	 * @param width the first size of the array
	 * @param height the second size of the array
	 * @param octave the number of times the smoothin process is repeated
	 * @return a 2D array of noise
	 */
	public double[][] getSmoothNoise(int width, int height, int octave){
		this.width = width;
		this.height = height;
		return genSmoothNoise(genWhiteNoise(width, height), octave);
	}

	public boolean[][] getCellularAutomataNoise(int width, int height, int birthLimit, int deathLimit, int numberOfSteps){
		this.width = width;
		this.height = height;
		return genCellularAutomataNoise(width, height, birthLimit, deathLimit, numberOfSteps);
	}

	private double[][] genWhiteNoise(int width, int height){
		double[][] noise = new double[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				noise[i][j] = random.nextDouble();
			}
		}
		return noise;
	}

	private double[][] genSmoothNoise(double[][] whiteNoise, int octaves){
		double[][] smoothNoise = new double[width][height];
		for(int repeat = 0; repeat < octaves; repeat ++){
			double[][] noise = new double[width][height];
			for(int i = 0; i < width - 1; i++){
				for(int j = 0; j < height - 1; j++){
					if(i > 0 && j > 0 && i < width && j < height){
						noise[i][j] = MathHelper.smooth9(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j-1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(i == 0 && j == 0){
						noise[i][j] = MathHelper.smooth4(whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(i == 0 && j == height){
						noise[i][j] = MathHelper.smooth4(whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i+1][j-1], whiteNoise[i+1][j]);
					}else if(i == width && j == 0){
						noise[i][j] = MathHelper.smooth4(whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j], whiteNoise[i][j+1]);
					}else if(i == 0){
						noise[i][j] = MathHelper.smooth6(whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j-1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(j == 0){
						noise[i][j] = MathHelper.smooth6(whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(i == width && j == height){
						noise[i][j] = MathHelper.smooth4(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i][j-1], whiteNoise[i][j]);
					}else if(i == width){
						noise[i][j] = MathHelper.smooth6(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i][j+1]);
					}else if(j == height){
						noise[i][j] = MathHelper.smooth6(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i+1][j-1], whiteNoise[i+1][j]);
					}
					smoothNoise[i][j] += noise[i][j];
				}
			}
		}
		for(int i = 0; i < width - 1; i++){
			for(int j = 0; j < height - 1; j++){
				smoothNoise[i][j] = smoothNoise[i][j]/octaves;
			}
		}
		return smoothNoise;
	}

	private double[][] genSmoothNoisePerlin(double[][] baseNoise, int octave){
		double[][] smoothNoise = new double[width][height];
		int samplePeriod = 1 << octave;
		double sampleFrequency = 1.0f / samplePeriod;
		for (int i = 0; i < width; i++){
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width;
			double horizontal_blend = (i - sample_i0) * sampleFrequency;
			for (int j = 0; j < height; j++){
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height;
				double vertical_blend = (j - sample_j0) * sampleFrequency;
				double top = MathHelper.cosineInterpolate(baseNoise[sample_i0][sample_j0], baseNoise[sample_i1][sample_j0], horizontal_blend);
				double bottom = MathHelper.cosineInterpolate(baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1], horizontal_blend);
				smoothNoise[i][j] = MathHelper.cosineInterpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}

	private double[][] genPerlinNoise(double[][] baseNoise, int octaveCount){
		double[][][] smoothNoise = new double[octaveCount][][];
		double persistance = 0.5f;
		for (int i = 0; i < octaveCount; i++){
			smoothNoise[i] = genSmoothNoisePerlin(baseNoise, i);
		}
		double[][] perlinNoise = new double[width][height];
		double amplitude = 1.0f;
		double totalAmplitude = 0.0f;
		for (int octave = octaveCount - 1; octave >= 0; octave--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			for (int i = 0; i < width; i++){
				for (int j = 0; j < height; j++){
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				perlinNoise[i][j] /= totalAmplitude;
			}
		}
		return perlinNoise;
	}

	private boolean[][] genCellularAutomataNoise(int width, int height, int birthLimit, int deathLimit, int numberOfSteps){
		boolean[][] automata = new boolean[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				automata[i][j] = random.nextBoolean();
			}
		}
		while(numberOfSteps > 0){
			doStep(automata, birthLimit, deathLimit);
			numberOfSteps--;
		}
		return automata;
	}

	private void doStep(boolean[][] automata, int birthLimit, int deathLimit){
		for(int i = 0; i < width - 1; i++){
			for(int j = 0; j < height - 1; j++){
				int count = 0;
				if(i > 0 && j > 0){
					if(automata[i-1][j-1]){
						count++;
					}
				}
				if(i > 0){
					if(automata[i-1][j]){
						count++;
					}
					if(automata[i-1][j+1]){
						count++;
					}
				}
				if(j > 0){
					if(automata[i][j-1]){
						count++;
					}
					if(automata[i+1][j-1]){
						count++;
					}
				}
				if(automata[i][j+1]){
					count++;
				}
				if(automata[i+1][j]){
					count++;
				}
				if(automata[i+1][j+1]){
					count++;
				}
				if(count <= deathLimit && automata[i][j]){
					automata[i][j] = false;
				}
				if(count >= birthLimit && !automata[i][j]){
					automata[i][j] = true;
				}
			}
		}
	}

}