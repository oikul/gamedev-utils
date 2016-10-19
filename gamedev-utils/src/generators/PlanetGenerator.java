package generators;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import biome.Biome;
import biome.BiomePart;
import blocks.Block;

public class PlanetGenerator {

	private Biome biome;
	private Block[][] planet;
	private Random random;

	public PlanetGenerator(Biome biome, int width, int height, long seed) {
		setBiome(biome);
		planet = new Block[width][height];
		random = new Random(seed);
	}

	public void generatePlanet(float[][] noise) {
		for (int i = 0; i < noise.length; i++) {
			for (int j = 0; j < noise[0].length; j++) {
				for (BiomePart part : biome.getBiomeParts()) {
					if (part.getStart() <= noise[i][j] && part.getEnd() >= noise[i][j]
							&& part.getChance() <= random.nextFloat()) {
						planet[i][j] = part.getBlock();
					}
				}
			}
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < planet.length; i++) {
			for (int j = 0; j < planet[0].length; j++) {
				if (planet[i][j] != null) {
					planet[i][j].draw(g2d, i * planet[i][j].getWidth(), j * planet[i][j].getHeight());
				}
			}
		}
	}

	public void draw(Graphics g, int scale) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < planet.length; i++) {
			for (int j = 0; j < planet[0].length; j++) {
				if (planet[i][j] != null) {
					planet[i][j].draw(g2d, i * planet[i][j].getWidth(), j * planet[i][j].getHeight(), scale);
				}
			}
		}
	}

	public Biome getBiome() {
		return biome;
	}

	public void setBiome(Biome biome) {
		this.biome = biome;
	}

}
