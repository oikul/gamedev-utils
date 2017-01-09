package generators;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import biome.Biome;
import handlers.InputHandler;
import handlers.MathHandler;
import utils.AbstractMain;

public class PlanetTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
	PlanetGenerator planet;
	PerlinNoiseGenerator noise;
	InputHandler input;
	public static float ratio;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static void main(String[] args) {
		PlanetTest main = new PlanetTest();
		main.run();
	}

	@Override
	public void initialise() {
		running = true;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("Planet Gen Test");
		this.setUndecorated(true);
		device.setFullScreenWindow(this);
		this.setVisible(running);
		ratio = Math.min(InputHandler.screenSize.width, InputHandler.screenSize.height) / 14f / 16f;
		Biome.createDefaultBiomes();
		long seed = MathHandler.random.nextLong();
		planet = new PlanetGenerator(500, 500, seed);
		noise = new PerlinNoiseGenerator(seed);
		planet.generatePlanet(noise.getPerlinNoise(500, 500, 4, 5));
	}

	@Override
	public void update(float time) {
	}

	@Override
	public void draw() {
		Graphics g = this.getGraphics();
		planet.draw(g, ratio);
	}

	@Override
	public void close() {
		
	}

}
