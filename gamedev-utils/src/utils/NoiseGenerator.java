package utils;

import java.util.Random;

public class NoiseGenerator {

	private Random random;
	private int width, height;
	
	public NoiseGenerator(){
		random = new Random();
	}
	
	public double[][] getPerlinNoise(int width, int height, int octave1, int octave2){
		this.width = width;
		this.height = height;
		return genPerlinNoise(genSmoothNoisePerlin(genWhiteNoise(width, height), octave1), octave2);
	}
	
	public double[][] getSmoothNoise(int width, int height, int octave){
		this.width = width;
		this.height = height;
		return genSmoothNoise(genWhiteNoise(width, height), octave);
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
	
	private double smooth2(double x0, double x1){
		return (x0 + x1) / 2;
	}
	
	private double smooth3(double x0, double x1, double x2){
		return (smooth2(x0, x1) + x2) / 2;
	}
	
	private double smooth4(double x0, double x1, double x2, double x3){
		return (smooth2(x0, x1) + smooth2(x2, x3))/2;
	}
	
	/*private double smooth5(double x0, double x1, double x2, double x3, double x4){
		return (smooth3(x0, x1, x2) + smooth2(x3, x4)) / 2;
	}*/
	
	private double smooth6(double x0, double x1, double x2, double x3, double x4, double x5){
		return (smooth3(x0, x1, x2) + smooth3(x3, x4, x5)) / 2;
	}
	
	/*private double smooth7(double x0, double x1, double x2, double x3, double x4, double x5, double x6){
		return (smooth3(x0, x1, x2) + smooth4(x3, x4, x5, x6)) / 2;
	}*/
	
	/*private double smooth8(double x0, double x1, double x2, double x3, double x4, double x5, double x6, double x7){
		return (smooth3(x0, x1, x2) + smooth3(x3, x4, x5) + smooth2(x6, x7)) / 3;
	}*/
	
	private double smooth9(double x0, double x1, double x2, double x3, double x4, double x5, double x6, double x7, double x8){
		return (smooth3(x0, x1, x2) + smooth3(x3, x4, x5) + smooth3(x6, x7, x8)) / 3;
	}
	
	private double[][] genSmoothNoise(double[][] whiteNoise, int octaves){
		double[][] smoothNoise = new double[width][height];
		for(int repeat = 0; repeat < octaves; repeat ++){
			double[][] noise = new double[width][height];
			for(int i = 0; i < width - 1; i++){
				for(int j = 0; j < height - 1; j++){
					if(i > 0 && j > 0 && i < width && j < height){
						noise[i][j] = smooth9(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j-1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(i == 0 && j == 0){
						noise[i][j] = smooth4(whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(i == 0 && j == height){
						noise[i][j] = smooth4(whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i+1][j-1], whiteNoise[i+1][j]);
					}else if(i == width && j == 0){
						noise[i][j] = smooth4(whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j], whiteNoise[i][j+1]);
					}else if(i == 0){
						noise[i][j] = smooth6(whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j-1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(j == 0){
						noise[i][j] = smooth6(whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j], whiteNoise[i][j+1], whiteNoise[i+1][j], whiteNoise[i+1][j+1]);
					}else if(i == width && j == height){
						noise[i][j] = smooth4(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i][j-1], whiteNoise[i][j]);
					}else if(i == width){
						noise[i][j] = smooth6(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i-1][j+1], whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i][j+1]);
					}else if(j == height){
						noise[i][j] = smooth6(whiteNoise[i-1][j-1], whiteNoise[i-1][j], whiteNoise[i][j-1], whiteNoise[i][j], whiteNoise[i+1][j-1], whiteNoise[i+1][j]);
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
	
	double interpolate(double x0, double x1, double alpha)
	{
	   return x0 * (1 - alpha) + alpha * x1;
	}
	
	private double[][] genSmoothNoisePerlin(double[][] baseNoise, int octave){
		double[][] smoothNoise = new double[width][height];
		int samplePeriod = 1 << octave; // calculates 2 ^ k
		double sampleFrequency = 1.0f / samplePeriod;
		for (int i = 0; i < width; i++){
			//calculate the horizontal sampling indices
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
			double horizontal_blend = (i - sample_i0) * sampleFrequency;
			for (int j = 0; j < height; j++){
				//calculate the vertical sampling indices
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
				double vertical_blend = (j - sample_j0) * sampleFrequency;
				//blend the top two corners
				double top = interpolate(baseNoise[sample_i0][sample_j0],
						baseNoise[sample_i1][sample_j0], horizontal_blend);
				//blend the bottom two corners
				double bottom = interpolate(baseNoise[sample_i0][sample_j1],
						baseNoise[sample_i1][sample_j1], horizontal_blend);
				//final blend
				smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}

	double[][] genPerlinNoise(double[][] baseNoise, int octaveCount){
		double[][][] smoothNoise = new double[octaveCount][][]; //an array of 2D arrays containing
		double persistance = 0.5f;
		//generate smooth noise
		for (int i = 0; i < octaveCount; i++){
			smoothNoise[i] = genSmoothNoise(baseNoise, i);
		}
		double[][] perlinNoise = new double[width][height];
		double amplitude = 1.0f;
		double totalAmplitude = 0.0f;
		//blend noise together
		for (int octave = octaveCount - 1; octave >= 0; octave--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			for (int i = 0; i < width; i++){
				for (int j = 0; j < height; j++){
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}
		//normalisation
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				perlinNoise[i][j] /= totalAmplitude;
			}
		}
		return perlinNoise;
	}

}