package mapMaker2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class UI {
	// overlay that will hide itself after seconds of inactivity

	private Tile selectedTile;
	private TileSet selectedTileSet;
	private TileSetLoader tsl;
	private boolean inFocus;
	private Point uiLocation;

	public UI() {
		tsl = new TileSetLoader();
		selectedTile = null;
		uiLocation = new Point(Main.width, 0);
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public TileSet getSelectedTileSet() {
		return selectedTileSet;
	}

	public TileSetLoader getTileSetLoader() {
		return tsl;
	}

	public Tile getTile(TileID id) {
		return tsl.getSets().get(id.getTileSet()).getTile(id);
	}

	public boolean isInFocus() {
		return inFocus;
	}

	public void addTileSet(BufferedImage tileSet, String path, int tileSize) {

		TileSet tiles = new TileSet(tileSet, path, tileSize, tsl);
		tsl.getSets().put(path, tiles);
		if (selectedTile == null || selectedTileSet == null) {
			selectedTileSet = tiles;
			selectedTile = tiles.getTile((TileID) tiles.getTiles().keySet().toArray()[0]);
		}

	}

	public void update(Point mouseLocation) {

		if (inFocus) {
			if (mouseLocation.x < (Main.width * 16.0) / 20.0) {
				inFocus = false;
				System.out.println("one");
			} else if (uiLocation.x > (Main.width * 16.0) / 20.0) {
				uiLocation.x -= Main.width / 100.0;
				if (uiLocation.x < (Main.width * 16.0) / 20.0) {
					uiLocation.x = (int) ((Main.width * 16.0) / 20.0);
				}
				System.out.println("two");
			}
		} else {
			if (mouseLocation.x > (Main.width * 19.0) / 20.0) {
				inFocus = true;
				System.out.println("three");
			} else if (uiLocation.x < Main.width) {
				uiLocation.x += Main.width / 100.0;
				System.out.println("four");
			}
		}

	}

	public void draw(Graphics g) {

		g.setColor(new Color(50, 200, 50, 127));
		g.fillRect(uiLocation.x, uiLocation.y, (int) (Main.width / 5.0), Main.height);

	}

}
