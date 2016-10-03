package mapMaker2D;

import java.awt.Point;

public class TileUpdate {

	private Tile tile;
	private Point location;

	public TileUpdate(Tile tile, Point location) {
		this.tile = tile;
		this.location = location;
	}

	public Tile getTile() {
		return tile;
	}

	public Point getLocation() {
		return location;
	}

}
