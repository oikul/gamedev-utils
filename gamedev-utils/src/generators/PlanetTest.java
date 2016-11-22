package generators;

import java.awt.Graphics;

import biome.Biome;
import blocks.Block;
import handlers.InputHandler;
import handlers.MathHandler;
import utils.AbstractMain;

public class PlanetTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
	PlanetGenerator planet;
	PerlinNoiseGenerator noise;
	InputHandler input;

	public static void main(String[] args) {
		PlanetTest main = new PlanetTest();
		main.run();
	}

	@Override
	public void initialise() {
		running = true;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Planet Gen Test");
		this.setSize(InputHandler.screenSize);
		this.setVisible(running);
		Biome.createDefaultBiomes();
		long seed = MathHandler.random.nextLong();
		planet = new PlanetGenerator(500, 500, seed);
		noise = new PerlinNoiseGenerator(seed);
		planet.generatePlanet(noise.getPerlinNoise(500, 500, 4, 5));
	}

	@Override
	public void update(float time) {
		Block.lava.update();
		Block.water_murky.update();
		Block.water_ocean.update();
		Block.water_river.update();
	}

	@Override
	public void draw() {
		Graphics g = this.getGraphics();
		planet.draw(g, 4);
	}

}
