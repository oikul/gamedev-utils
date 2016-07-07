package mapMaker2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UI {
	// overlay that will hide itself after seconds of inactivity

	private int selectedTileIndex, selectedTileSetIndex;
	private Tile selectedTile;
	private TileSet selectedTileSet;
	private TileSetLoader tsl;
	private boolean inFocus;
	private Point uiLocation;

	private BufferedImage uiImage;
	private ArrayList<BufferedImage> imageSets;
	private ArrayList<BufferedImage> imageTiles;

	public UI() {
		tsl = new TileSetLoader();
		selectedTileIndex = selectedTileSetIndex = 0;
		uiLocation = new Point(Main.width, 0);
		uiImage = new BufferedImage(Main.width / 5, Main.height, BufferedImage.TYPE_INT_ARGB);
		imageSets = new ArrayList<BufferedImage>();
		imageTiles = new ArrayList<BufferedImage>();
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

	private void updateUI(Point mouseLocation) {
		imageSets.clear();
		imageTiles.clear();
		LinkedHashMap<String, TileSet> set = tsl.getSets();
		TileSet tempSet;
		for (int i = 0; i < set.size(); i++) {
			tempSet = tsl.getSets().get(tsl.getKey(i));
			if (tempSet.equals(selectedTileSet)) {
				selectedTileSetIndex = i;
			}
			imageSets.add(tempSet.getTileSheet());
		}

		LinkedHashMap<TileID, Tile> tileSet = selectedTileSet.getTiles();
		Tile tempTile;
		for (int i = 0; i < tileSet.size(); i++) {
			tempTile = tileSet.get(selectedTileSet.getKey(i));
			if (tempTile.equals(selectedTile)) {
				selectedTileIndex = i;
			}
			imageTiles.add(tempTile.getImage());
		}

	}

	public void update(Point mouseLocation, boolean mouseDrag) {

		if (inFocus) {
			if (mouseLocation.x < (Main.width * 16.0) / 20.0) {
				inFocus = false;
			} else if (uiLocation.x > (Main.width * 16.0) / 20.0) {
				uiLocation.x -= Main.width / 100.0;
				if (uiLocation.x < (Main.width * 16.0) / 20.0) {
					uiLocation.x = (int) ((Main.width * 16.0) / 20.0) + 1;
				}
			}
		} else {
			if (mouseLocation.x > (Main.width * 19.0) / 20.0 && !mouseDrag) {
				inFocus = true;
			} else if (uiLocation.x < Main.width) {
				uiLocation.x += Main.width / 100.0;
			}
		}
		if (!mouseDrag && inFocus) {
			updateUI(mouseLocation);
		}
	}

	public void draw(Graphics g) {

		Graphics uiGraphics = uiImage.getGraphics();
		uiGraphics.setColor(new Color(0, 0, 0, 0));
		uiGraphics.fillRect(0, 0, uiImage.getWidth(), uiImage.getHeight());
		uiGraphics.setColor(new Color(.25f, .25f, .25f, .5f));
		uiGraphics.fillRect(0, 0, (int) (Main.width / 5.0), Main.height);
		
		for (int i = 0; i < imageSets.size(); i++) {
			g.drawImage(imageSets.get(i), i * 64, 0, 64, 64, null);
		}
		for (int i = 0; i < imageTiles.size(); i++) {
			g.drawImage(imageTiles.get(i), i * 32, 64, 32, 32, null);
		}

		g.drawImage(uiImage, uiLocation.x, uiLocation.y, null);

	}

}
