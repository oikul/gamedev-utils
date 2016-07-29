package mapMaker2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UI {
	// overlay that will hide itself when the cursor leaves

	private int selectedTileIndex, selectedTileSetIndex, hoveredTile, hoveredTileSet, uiMidpoint;
	private Tile selectedTile;
	private TileSet selectedTileSet;
	private TileSetLoader tsl;
	private boolean inFocus;
	private Point uiLocation;
	private float sizeOfTileOnUI, border, tileSetSize, size;

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

		tileSetSize = Main.width / 18;
		sizeOfTileOnUI = (Main.width / 15);
		border = sizeOfTileOnUI / 20;
		size = sizeOfTileOnUI * 19 / 20;
		uiMidpoint = Main.width / 10;
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

	private void updateUI() {
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

	private void checkMouseOverTile(Point mouseLocation) {

		int test = (int) ((mouseLocation.x - uiLocation.x) / sizeOfTileOnUI)
				+ (int) ((mouseLocation.y - tileSetSize) / sizeOfTileOnUI) * 3;
		int max = tsl.getSets().get(tsl.getKey(selectedTileSetIndex)).getTiles().size() - 1;

		if (test > max || mouseLocation.y <= tileSetSize) {
			hoveredTile = -1;
		} else if (mouseLocation.y > tileSetSize) {
			hoveredTile = test;
		}
	}

	public void checkMouseOverTileSet(Point mouseLocation) {
		if (mouseLocation.y <= tileSetSize) {

			int test = Math.round((mouseLocation.x - (uiLocation.x + uiMidpoint)) / tileSetSize) + selectedTileSetIndex;
			int max = imageSets.size() - 1;

			if (test > max || test < 0) {
				hoveredTileSet = -1;
			} else {
				hoveredTileSet = test;
			}
		} else {
			hoveredTileSet = -1;
		}
	}

	public void update(Point mouseLocation, boolean mouseDrag, boolean updateTiles) {
		if (!mouseDrag && inFocus) {
			updateUI();
			checkMouseOverTile(mouseLocation);
			checkMouseOverTileSet(mouseLocation);
		} else {
			hoveredTile = -1;
			hoveredTileSet = -1;
		}
		if (Main.input.isMouseDown(MouseEvent.BUTTON1)) {
			if (hoveredTile != -1) {
				selectedTileIndex = hoveredTile;
				selectedTile = selectedTileSet.getTile(selectedTileSet.getKey(selectedTileIndex));
			} 
			if (hoveredTileSet != -1) {
				selectedTileSetIndex = hoveredTileSet;
				selectedTileSet = tsl.getSets().get(tsl.getKey(selectedTileSetIndex));
				selectedTileIndex = 0;
				selectedTile = selectedTileSet.getTile(selectedTileSet.getKey(selectedTileIndex));
			}
		}
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

	}

	public void draw(Graphics g) {

		Graphics uiGraphics = uiImage.getGraphics();
		uiGraphics.setColor(new Color(0, 0, 0, 100));
		uiGraphics.fillRect(0, 0, uiImage.getWidth(), uiImage.getHeight());
		uiGraphics.setColor(new Color(100, 100, 100, 100));
		uiGraphics.fillRect(0, 0, (int) (Main.width / 5.0), Main.height);

		// marks the selected tile
		uiGraphics.setColor(Color.white);
		uiGraphics.fillRect((int) ((selectedTileIndex % 3) * sizeOfTileOnUI),
				(int) (tileSetSize + (selectedTileIndex / 3) * sizeOfTileOnUI), (int) sizeOfTileOnUI,
				(int) sizeOfTileOnUI);

		// marks the tile being hovered over
		if (hoveredTile != -1) {
			uiGraphics.setColor(Color.red);
			uiGraphics.fillRect((int) ((hoveredTile % 3) * sizeOfTileOnUI),
					(int) (tileSetSize + (hoveredTile / 3) * sizeOfTileOnUI), (int) sizeOfTileOnUI,
					(int) sizeOfTileOnUI);
		}

		// marks the tile set being hovered over
		if (hoveredTileSet != -1) {
			uiGraphics.setColor(Color.red);
			uiGraphics.fillRect((int) ((hoveredTileSet - selectedTileSetIndex) * tileSetSize)
					+ (int) (uiMidpoint - (tileSetSize / 2)), 0, (int) tileSetSize, (int) tileSetSize);
		}

		// draws all the tile sets
		for (int i = 0; i < imageSets.size(); i++) {
			uiGraphics.drawImage(imageSets.get(i),
					uiMidpoint + (int) (((i - selectedTileSetIndex) - 0.5) * tileSetSize) + (int) border / 2,
					(int) border / 2, (int) (tileSetSize - border), (int) (tileSetSize - border), null);
		}

		// draws all the tiles
		for (int i = 0; i < imageTiles.size(); i++) {
			uiGraphics.drawImage(imageTiles.get(i), (int) (border / 2 + (i % 3) * sizeOfTileOnUI),
					(int) (tileSetSize + (border / 2) + (i / 3) * sizeOfTileOnUI), (int) size, (int) size, null);
		}

		g.drawImage(uiImage, uiLocation.x, uiLocation.y, null);

	}

}
