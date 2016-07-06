package mapMaker2D;

import java.awt.image.BufferedImage;

public class Tile {

	private TileID id;
	private BufferedImage image;
	
	public Tile(TileID id, BufferedImage image) {
		this.id = id;
		this.image = image;
	}

	public TileID getId() {
		return id;
	}

	public BufferedImage getImage() {
		return image;
	}

}
















































