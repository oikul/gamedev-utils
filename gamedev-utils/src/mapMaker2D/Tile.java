package mapMaker2D;

import java.awt.Graphics;
import java.awt.Image;

public class Tile {

	private TileID id;
	private Image image;
	
	public Tile(TileID id, Image image) {
		this.id = id;
		this.image = image;
	}

	public TileID getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

}
