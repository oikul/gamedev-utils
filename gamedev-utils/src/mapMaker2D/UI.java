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
	private boolean inFocus, scrollTileSets;
	private Point uiLocation;
	private float sizeOfTileOnUI, border, tileSetSize, size, tileScroll, tileSetScroll;

	private BufferedImage uiImage;
	private ArrayList<BufferedImage> imageSets;
	private ArrayList<BufferedImage> imageTiles;

	public UI() {
		tsl = new TileSetLoader();
		selectedTileIndex = selectedTileSetIndex = 0;
		tileScroll = tileSetScroll = 0f;
		scrollTileSets = false;
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
				+ (int) ((mouseLocation.y - tileSetSize) / sizeOfTileOnUI - tileScroll) * 3;
		int max = tsl.getSets().get(tsl.getKey(selectedTileSetIndex)).getTiles().size() - 1;

		if (test > max || mouseLocation.y <= tileSetSize) {
			hoveredTile = -1;
		} else if (mouseLocation.y > tileSetSize) {
			hoveredTile = test;
		}
	}

	public void checkMouseOverTileSet(Point mouseLocation) {
		if (mouseLocation.y <= tileSetSize && mouseLocation.x - uiLocation.x > uiImage.getWidth() / 10
				&& mouseLocation.x - uiLocation.x < uiImage.getWidth() * 9 / 10) {

				int test = Math.round((mouseLocation.x - (uiLocation.x + uiMidpoint)) / tileSetSize)
						+ selectedTileSetIndex - (int)tileSetScroll;
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

	private void scrollTiles() {
		double rotation = Main.input.getMouseWheelRotation();
		tileScroll -= (rotation * 0.5f);
		if (tileScroll > 0f) {
			tileScroll = 0f;
		} else if (tileScroll < -imageTiles.size() / 3) {
			tileScroll = -imageTiles.size() / 3;
		}
	}

	private void scrollTileSets(Point mouseLocation) {
		
		if (Main.input.isMouseDown(MouseEvent.BUTTON1)) {
			if (mouseLocation.x - uiLocation.x < uiImage.getWidth() / 10) {
				tileSetScroll = Math.max(-selectedTileSetIndex, --tileSetScroll);
				System.out.println("UI.scrollTileSets()1, " + tileSetScroll);
			} else if (mouseLocation.x - uiLocation.x > uiImage.getWidth() * 9 / 10) {
				tileSetScroll = Math.min(imageSets.size() - selectedTileSetIndex - 1, ++tileSetScroll);
				System.out.println("UI.scrollTileSets()2, " + tileSetScroll);
			}
		}

		mouseLocation.translate(-uiLocation.x, 0);

		if (scrollTileSets) {
			if (Main.input.isMouseDown(MouseEvent.BUTTON1)) {
				if (mouseLocation.x < uiImage.getWidth() / 10) {
					tileSetScroll--;
					if (tileSetScroll < 0 - selectedTileSetIndex) {
						tileSetScroll = 0 - selectedTileSetIndex;
					}
				} else if (mouseLocation.x > uiImage.getWidth() * 9 / 10) {
					tileSetScroll++;
					if (tileSetScroll >= imageSets.size() - selectedTileSetIndex) {
						tileSetScroll = imageSets.size() - 1 - selectedTileSetIndex;
					}
				}
			}
		} else if (!Main.input.isMouseDown(MouseEvent.BUTTON1)
				&& (mouseLocation.x < uiImage.getWidth() / 10 || mouseLocation.x > uiImage.getWidth() * 9 / 10)) {
			scrollTileSets = true;
		} else {
			scrollTileSets = false;
		}
		mouseLocation.translate(uiLocation.x, 0);
		
	}

	public void update(Point mouseLocation, boolean mouseDrag, boolean updateTiles) {
		if (!mouseDrag && inFocus) {
			updateUI();
			checkMouseOverTile(mouseLocation);
			checkMouseOverTileSet(mouseLocation);
			if (mouseLocation.y > tileSetSize) {
				scrollTiles();
			} else {
				scrollTileSets(mouseLocation);
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
					tileScroll = tileSetScroll = 0f;
				}
			}
		} else {
			hoveredTile = -1;
			hoveredTileSet = -1;
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
		uiGraphics.setColor(Color.gray);
		uiGraphics.fillRect(0, 0, uiImage.getWidth(), uiImage.getHeight());

		// marks the selected tile
		uiGraphics.setColor(Color.white);
		uiGraphics.fillRect((int) ((selectedTileIndex % 3) * sizeOfTileOnUI),
				(int) (tileSetSize + (selectedTileIndex / 3) * sizeOfTileOnUI) + (int) (tileScroll * sizeOfTileOnUI),
				(int) sizeOfTileOnUI, (int) sizeOfTileOnUI);

		// marks the tile being hovered over
		if (hoveredTile != -1) {
			uiGraphics.setColor(Color.red);
			uiGraphics.fillRect((int) ((hoveredTile % 3) * sizeOfTileOnUI),
					(int) (tileSetSize + (hoveredTile / 3) * sizeOfTileOnUI) + (int) (tileScroll * sizeOfTileOnUI),
					(int) sizeOfTileOnUI, (int) sizeOfTileOnUI);
		}

		// draws all the tiles
		for (int i = 0; i < imageTiles.size(); i++) {
			uiGraphics.drawImage(imageTiles.get(i), (int) (border / 2 + (i % 3) * sizeOfTileOnUI),
					(int) (tileSetSize + (border / 2) + (i / 3) * sizeOfTileOnUI) + (int) (tileScroll * sizeOfTileOnUI),
					(int) size, (int) size, null);
		}

		uiGraphics.setColor(Color.lightGray);
		uiGraphics.fillRect(0, 0, uiImage.getWidth(), (int) tileSetSize);

		// marks the selected tileSet
		uiGraphics.setColor(Color.white);
		uiGraphics.fillRect((int) (uiMidpoint - (tileSetSize / 2)) - (int) (tileSetScroll * tileSetSize), 0,
				(int) tileSetSize, (int) tileSetSize);

		// marks the tileSet being hovered over
		if (hoveredTileSet != -1) {
			uiGraphics.setColor(Color.red);
			uiGraphics.fillRect((int) ((hoveredTileSet - selectedTileSetIndex - tileSetScroll) * tileSetSize)
					+ (int) (uiMidpoint - (tileSetSize / 2)), 0, (int) tileSetSize, (int) tileSetSize);
		}

		// draws all the tileSets
		for (int i = 0; i < imageSets.size(); i++) {
			uiGraphics.drawImage(imageSets.get(i),
					uiMidpoint + (int) (((i - selectedTileSetIndex - tileSetScroll) - 0.5) * tileSetSize) + (int) border / 2,
					(int) border / 2, (int) (tileSetSize - border), (int) (tileSetSize - border), null);
		}

		// draws the left and right arrows for the tileSets
		uiGraphics.setColor(new Color(100, 100, 100, 100));
		uiGraphics.fillRect(0, 0, uiImage.getWidth() / 10, (int) tileSetSize);
		uiGraphics.fillRect((uiImage.getWidth() * 9) / 10, 0, uiImage.getWidth() / 10, (int) tileSetSize);
		uiGraphics.setColor(new Color(50, 50, 50, 100));

		// the six coordinates of the arrows points
		int top, mid, bottom, left, right, offset;
		top = (int) (tileSetSize / 10f);
		bottom = (int) (tileSetSize * 9f / 10f);
		left = (int) (uiImage.getWidth() / 100f);
		right = (int) (uiImage.getWidth() * 9f / 100f);
		mid = (int) (tileSetSize / 2f);
		offset = (int) (uiImage.getWidth() * 9f / 10f);
		uiGraphics.fillPolygon(new int[] { left, right, right }, new int[] { mid, top, bottom }, 3);
		uiGraphics.fillPolygon(new int[] { left + offset, left + offset, right + offset },
				new int[] { bottom, top, mid }, 3);

		g.drawImage(uiImage, uiLocation.x, uiLocation.y, null);

	}
	
	public void close(){
		tsl.close();
		tsl = null;
		uiImage = null;
		imageSets = null;
		imageTiles = null;
	}

}
