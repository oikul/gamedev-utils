package generators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blocks.Block;
import handlers.InputHandler;
import handlers.MathHandler;
import utils.AbstractMain;

public class MazeTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
	private MazeGenerator maze;
	private BufferedImage image = new BufferedImage(InputHandler.screenSize.width, InputHandler.screenSize.height,
			BufferedImage.TYPE_INT_ARGB);

	@Override
	public void initialise() {
		running = true;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Maze Gen Test");
		this.setSize(InputHandler.screenSize);
		this.setVisible(running);
		maze = new MazeGenerator(16, 16, 64, MathHandler.random.nextLong());
		maze.initialise();
		maze.generate(1, 1);
	}

	@Override
	public void update(float time) {
	}

	@Override
	public void draw() {
		Graphics g = this.getGraphics();
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, InputHandler.screenSize.width, InputHandler.screenSize.height);
		maze.draw(g2d, Block.rock, Block.stone_solid, 4);
		g.drawImage(image, 0, 0, null);
	}

	public static void main(String[] args) {
		MazeTest main = new MazeTest();
		main.run();
	}

	@Override
	public void close() {
		
	}

}
