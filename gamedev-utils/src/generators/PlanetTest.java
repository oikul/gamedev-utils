package generators;

import java.awt.Graphics;

import biome.Biome;
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
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(running);
		this.setSize(InputHandler.screenSize);
		Biome.createDefaultBiomes();
		long seed = MathHandler.random.nextLong();
		planet = new PlanetGenerator(Biome.islands, 500, 500, seed);
		noise = new PerlinNoiseGenerator(seed);
		planet.generatePlanet(noise.getPerlinNoise(500, 500, 4, 5));
	}

	@Override
	public void update(float time) {
		
	}

	@Override
	public void draw() {
		Graphics g = this.getGraphics();
		planet.draw(g, 4);
	}

}
