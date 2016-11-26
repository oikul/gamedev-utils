package generators;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import biome.Biome;
import biome.BiomePart;
import blocks.Block;

public class PlanetGenerator {

	private Biome biome;
	private Block[][] planet, decoration;
	private BufferedImage planetImage;
	private Random random;
	private boolean life;

	public PlanetGenerator(int width, int height, long seed) {
		planet = new Block[width][height];
		decoration = new Block[width][height];
		random = new Random(seed);
		float temp = random.nextFloat(), precip = random.nextFloat();
		planetImage = new BufferedImage(width*16, height*16, BufferedImage.TYPE_INT_ARGB);
		setBiome(chooseBiome(temp, precip));
		life = biome.getLifeChance() <= random.nextFloat();
	}

	public PlanetGenerator(Biome biome, int width, int height, long seed) {
		setBiome(biome);
		planet = new Block[width][height];
		decoration = new Block[width][height];
		random = new Random(seed);
		planetImage = new BufferedImage(width*16, height*16, BufferedImage.TYPE_INT_ARGB);
		life = biome.getLifeChance() <= random.nextFloat();
	}
	
	public PlanetGenerator(float temp, float precip, int width, int height, long seed) {
		planet = new Block[width][height];
		decoration = new Block[width][height];
		random = new Random(seed);
		planetImage = new BufferedImage(width*16, height*16, BufferedImage.TYPE_INT_ARGB);
		setBiome(chooseBiome(temp, precip));
		life = biome.getLifeChance() <= random.nextFloat();
	}

	public Biome chooseBiome(float temperature, float precipitation) {
		Biome b = Biome.forest;
		if (temperature >= 0 && temperature < 0.2) {
			if (precipitation >= 0 && precipitation < 0.2) {
				b = Biome.polar_desert;
			} else if (precipitation >= 0.2 && precipitation < 0.4) {
				b = Biome.ice_spikes;
			} else if (precipitation >= 0.4 && precipitation < 0.6) {
				b = Biome.frozen_lakes;
			} else if (precipitation >= 0.6 && precipitation < 0.8) {
				b = Biome.ice_sheet;
			} else if (precipitation >= 0.8 && precipitation <= 1.0) {
				b = Biome.ice_bergs;
			}
		} else if (temperature >= 0.2 && temperature < 0.4) {
			if (precipitation >= 0 && precipitation < 0.2) {
				b = Biome.tundra;
			} else if (precipitation >= 0.2 && precipitation < 0.4) {
				b = Biome.mountain;
			} else if (precipitation >= 0.4 && precipitation < 0.6) {
				b = Biome.taiga;
			} else if (precipitation >= 0.6 && precipitation < 0.8) {
				b = Biome.mountain_forest;
			} else if (precipitation >= 0.8 && precipitation <= 1.0) {
				b = Biome.ocean;
			}
		} else if (temperature >= 0.4 && temperature < 0.6) {
			if (precipitation >= 0 && precipitation < 0.2) {
				b = Biome.steppe;
			} else if (precipitation >= 0.2 && precipitation < 0.4) {
				b = Biome.plains;
			} else if (precipitation >= 0.4 && precipitation < 0.6) {
				b = Biome.forest;
			} else if (precipitation >= 0.6 && precipitation < 0.8) {
				b = Biome.lakes;
			} else if (precipitation >= 0.8 && precipitation <= 1.0) {
				b = Biome.islands;
			}
		} else if (temperature >= 0.6 && temperature < 0.8) {
			if (precipitation >= 0 && precipitation < 0.2) {
				b = Biome.desert_plains;
			} else if (precipitation >= 0.2 && precipitation < 0.4) {
				b = Biome.canyon;
			} else if (precipitation >= 0.4 && precipitation < 0.6) {
				b = Biome.savannah;
			} else if (precipitation >= 0.6 && precipitation < 0.8) {
				b = Biome.jungle;
			} else if (precipitation >= 0.8 && precipitation <= 1.0) {
				b = Biome.rainforest;
			}
		} else if (temperature >= 0.8 && temperature <= 1.0) {
			if (precipitation >= 0 && precipitation < 0.2) {
				b = Biome.lava_ocean;
			} else if (precipitation >= 0.2 && precipitation < 0.4) {
				b = Biome.lava_islands;
			} else if (precipitation >= 0.4 && precipitation < 0.6) {
				b = Biome.lava_lakes;
			} else if (precipitation >= 0.6 && precipitation < 0.8) {
				b = Biome.igneous_desert;
			} else if (precipitation >= 0.8 && precipitation <= 1.0) {
				b = Biome.volcanic_mountains;
			}
		}
		return b;
	}

	public void generatePlanet(float[][] noise) {
		Graphics2D g2d = (Graphics2D) planetImage.getGraphics();
		for (int i = 0; i < noise.length; i++) {
			for (int j = 0; j < noise[0].length; j++) {
				for (BiomePart part : biome.getBiomeParts()) {
					if (part.getStart() <= noise[i][j] && part.getEnd() >= noise[i][j]
							&& part.getChance() >= random.nextFloat()) {
						planet[i][j] = part.getBlock();
					}
				}
				for(BiomePart part : biome.getDecoParts()) {
					if (part.getStart() <= noise[i][j] && part.getEnd() >= noise[i][j]
							&& part.getChance() >= random.nextFloat()) {
						decoration[i][j] = part.getBlock();
					}
				}
				if(life){
					generateLife();
				}
				planet[i][j].draw(g2d, i * planet[i][j].getWidth(), j * planet[i][j].getHeight());
				if(decoration[i][j] != null){
					decoration[i][j].draw(g2d, i * decoration[i][j].getWidth(), j * decoration[i][j].getHeight());
				}
			}
		}
	}
	
	public void generateLife(){
		
	}

	public void draw(Graphics g) {
		g.drawImage(planetImage, 0, 0, null);
	}

	public void draw(Graphics g, int scale) {
		g.drawImage(planetImage, 0, 0, planetImage.getWidth() * scale, planetImage.getHeight() * scale, null);
	}

	public Biome getBiome() {
		return biome;
	}

	public void setBiome(Biome biome) {
		this.biome = biome;
	}

}
