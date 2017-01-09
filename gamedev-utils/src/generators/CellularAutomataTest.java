package generators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blocks.Block;
import handlers.InputHandler;
import utils.AbstractMain;

public class CellularAutomataTest extends AbstractMain {

	private static final long serialVersionUID = 1L;
	private CellularAutomata cellGenerator;
	private BufferedImage image = new BufferedImage(InputHandler.screenSize.width, InputHandler.screenSize.height,
			BufferedImage.TYPE_INT_ARGB);
	private boolean[][] cells;

	@Override
	public void initialise() {
		running = true;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Maze Gen Test");
		this.setSize(InputHandler.screenSize);
		this.setVisible(running);
		cellGenerator = new CellularAutomata();
		cells = cellGenerator.getCellularAutomataNoise(100, 100, 4, 3, 1);
		
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
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[0].length; j++){
				if(cells[i][j]){
					Block.clay.draw(g2d, i * Block.clay.getWidth(), j * Block.clay.getHeight());
				}
			}
		}
		g.drawImage(image, 0, 0, null);
	}

	public static void main(String[] args) {
		CellularAutomataTest main = new CellularAutomataTest();
		main.run();
	}

	@Override
	public void close() {
		
	}

}
