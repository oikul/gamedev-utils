package mapMaker2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

public class BuildMap {

	private int mapWidth, mapHeight, maxWidth, maxHeight;
	private boolean mouseDrag, loadTileSheet, updateTiles, zoomed, grid;
	private String lastPath, tileSize;
	private Point dragStart, mouseLocation;
	private TileSetLoader tsl;
	private UI ui;
	private ArrayList<ArrayList<TileID>> map;
	private ArrayList<TileUpdate> mapUpdates;
	private BufferedImage gridImage;
	private BufferedImage tileImage;

	public BuildMap(int mapWidth, int mapHeight, UI ui) {

		this.mapWidth = maxWidth = mapWidth;
		this.mapHeight = maxHeight = mapHeight;
		this.ui = ui;
		lastPath = "";
		tileSize = "16";
		tsl = ui.getTileSetLoader();
		tsl.getKeys();
		mouseDrag = loadTileSheet = zoomed = false;
		grid = true;
		dragStart = mouseLocation = new Point(0, 0);
		map = new ArrayList<ArrayList<TileID>>();
		mapUpdates = new ArrayList<TileUpdate>();
		for (int x = 0; x < mapWidth; x++) {
			map.add(new ArrayList<TileID>());
			for (int y = 0; y < mapHeight; y++) {
				map.get(x).add(null);
			}
		}
		gridImage = new BufferedImage(mapWidth * Settings.resolution, mapHeight * Settings.resolution,
				BufferedImage.TYPE_INT_ARGB);
		tileImage = new BufferedImage(mapWidth * Settings.resolution, mapHeight * Settings.resolution,
				BufferedImage.TYPE_INT_ARGB);
		Graphics tg = tileImage.getGraphics();
		tg.setColor(Color.black);
		tg.fillRect(0, 0, tileImage.getWidth(), tileImage.getHeight());
		ui.addTileSet(tsl.getTileSet("testTileSheet"), "testTileSheet", 16);

	}

	public UI getUI() {
		return ui;
	}

	/*
	 * private void checkMapChange() { if
	 * (Main.input.isKeyDown(KeyEvent.VK_RIGHT)) { if (maxWidth <= mapWidth) {
	 * map.add(new ArrayList<TileID>()); for (int y = 0; y < maxHeight; y++) {
	 * map.get(map.size() - 1).add(null); } maxWidth++; } mapWidth++;
	 * Main.input.artificialKeyReleased(KeyEvent.VK_RIGHT); } else if
	 * (Main.input.isKeyDown(KeyEvent.VK_LEFT)) { map.remove(map.size() - 1);
	 * mapWidth = Math.max(0, --mapWidth);
	 * Main.input.artificialKeyReleased(KeyEvent.VK_LEFT); } else if
	 * (Main.input.isKeyDown(KeyEvent.VK_UP)) { for (int x = 0; x < mapWidth;
	 * x++) { map.get(x).remove(map.get(x).size() - 1); } mapHeight =
	 * Math.max(0, --mapHeight);
	 * Main.input.artificialKeyReleased(KeyEvent.VK_UP); } else if
	 * (Main.input.isKeyDown(KeyEvent.VK_DOWN)) { if (maxHeight <= mapHeight) {
	 * for (int x = 0; x < maxWidth; x++) { map.get(x).add(null); } maxHeight++;
	 * } mapHeight++; Main.input.artificialKeyReleased(KeyEvent.VK_DOWN); } }
	 */

	private void checkPlayerTilePlacement() {
		// place selected tiles and drag logic
		updateTiles = false;
		if (Main.input.isMouseDown(MouseEvent.BUTTON3)) {
			mouseDrag = false;
			Main.input.artificialMouseReleased(MouseEvent.BUTTON1);
		}
		TileID id = ui.getSelectedTile().getId();
		if (Main.input.isMouseDown(MouseEvent.BUTTON1) && (mouseDrag
				|| (ui.getSelectedTile() != null && mouseLocation.x <= (mapWidth + Main.XOffset) * Main.tileSize
						&& mouseLocation.y <= (mapHeight + Main.YOffset) * Main.tileSize
						&& mouseLocation.x >= (Main.XOffset) * Main.tileSize
						&& mouseLocation.y >= (Main.YOffset) * Main.tileSize))) {

			if (!mouseDrag) {// start drag or single click
				dragStart.setLocation(
						Math.max(0, Math.min(mapWidth - 1, (mouseLocation.x) / Main.tileSize - (int) Main.XOffset)),
						Math.max(0, Math.min(mapHeight - 1, (mouseLocation.y / Main.tileSize - (int) Main.YOffset))));
			}
			mouseDrag = true;
		} else {// no click
			if (mouseDrag) {// end drag
				updateTiles = true;
				// **Math.min(mouseLocation.x / Main.tileSize, dragStart.x)**
				// accounts for backwards drag
				int fromx = Math.max(0, Math.min(mouseLocation.x / Main.tileSize - (int) Main.XOffset, dragStart.x));
				int toox = Math.min(mapWidth - 1,
						Math.max(mouseLocation.x / Main.tileSize - (int) Main.XOffset, dragStart.x)) + 1;

				int fromy = Math.max(0, Math.min(mouseLocation.y / Main.tileSize - (int) Main.YOffset, dragStart.y));
				int tooy = Math.min(mapHeight - 1,
						Math.max(mouseLocation.y / Main.tileSize - (int) Main.YOffset, dragStart.y)) + 1;

				for (int x = fromx; x < toox; x++) {

					for (int y = fromy; y < tooy; y++) {

						// tile change happens here
						map.get(x).set(y, id);
						mapUpdates.add(new TileUpdate(ui.getSelectedTile(), new Point(x, y)));
					}
				}
			}
			mouseDrag = false;
		}

	}

	private void checkNewTileSheet() {

		if (Main.input.isKeyDown(KeyEvent.VK_T)) {
			// type_path = true;
			Main.forceFront = true;
			lastPath = (String) JOptionPane.showInputDialog(Main.getFrame(),
					"Please type the Tile Sheets path Then press enter. "
							+ "\nAssume the path starts with '/src/resources/tileSheets/' \nand ends with '.png'.",
					"Enter Path", JOptionPane.PLAIN_MESSAGE);

			tileSize = (String) JOptionPane.showInputDialog(Main.getFrame(),
					"Please choose the size of the tiles on the tile sheet", "Enter Tile Size",
					JOptionPane.PLAIN_MESSAGE, null, new String[] { "8", "16", "32", "64" }, tileSize);
			Main.forceFront = false;
			loadTileSheet = true;
			Main.input.artificialKeyReleased(KeyEvent.VK_T);
			// Main.input.clearTypedAcum();
		}
		if (loadTileSheet) {
			// change the tile size to a user inputed var-----------------------
			BufferedImage tileSetImage = tsl.getTileSet(lastPath);
			if (tileSetImage != null) {
				int tileSize1 = 0;
				try {
					tileSize1 = Integer.parseInt(tileSize);
					ui.addTileSet(tileSetImage, lastPath, tileSize1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Main.forceFront = true;
				JOptionPane.showConfirmDialog(Main.getFrame(), "Invalid path entered", "ERROR",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				Main.forceFront = false;
			}
			loadTileSheet = false;
		}

	}

	public void update(boolean zoomed, Point mouseLocation) {

		this.zoomed = zoomed;
		this.mouseLocation = mouseLocation;
		if (Main.input.isKeyDown(KeyEvent.VK_G)) {
			grid = !grid;
			Main.input.artificialKeyReleased(KeyEvent.VK_G);
		}
		if (!ui.isInFocus()) {
			// checkMapChange();
			checkPlayerTilePlacement();
			checkNewTileSheet();
		} else {
			mouseDrag = false;
		}
		ui.update(mouseLocation, mouseDrag, updateTiles);

	}

	public void draw(Graphics g) {
		// remove all nested for loops
		int size = Main.tileSize;
		Graphics tg = tileImage.getGraphics();

		if (updateTiles) {
			updateTiles = false;
			for (TileUpdate tileup : mapUpdates) {
				tg.drawImage(tileup.getTile().getImage(), tileup.getLocation().x * Settings.resolution,
						tileup.getLocation().y * Settings.resolution, Settings.resolution, Settings.resolution, null);
			}

		}

		int dx = (int)(Main.XOffset*size)/size*size;
		int dy = (int)(Main.YOffset*size)/size*size;
		
		g.drawImage(tileImage,
				dx ,dy ,
				dx+(tileImage.getWidth() * size) / Settings.resolution,
				dy+(tileImage.getHeight() * size) / Settings.resolution,
				
				0, 0, tileImage.getWidth(), tileImage.getHeight(), 
				null);
		
		if (!ui.isInFocus()) {
			BufferedImage img = ui.getSelectedTile().getImage();
			g.drawImage(img, (mouseLocation.x / size) * size, (mouseLocation.y / size) * size, size, size, null);

			if (mouseDrag) {
				g.setColor(new Color(100, 100, 255, 100));
				int x = Math.min((mouseLocation.x / size) * size, (dragStart.x + (int) Main.XOffset) * size);
				int y = Math.min((mouseLocation.y / size) * size, (dragStart.y + (int) Main.YOffset) * size);
				int w = Math.abs(((mouseLocation.x / size) * size) - (dragStart.x + (int) Main.XOffset) * size) + size;
				int h = Math.abs(((mouseLocation.y / size) * size) - (dragStart.y + (int) Main.YOffset) * size) + size;
				g.fillRect(x, y, w, h);
			}
		}
		if (grid) {
			// g.drawImage(gridImage, 0, 0, tileImage.getWidth(),
			// tileImage.getHeight(), null);
		}
		ui.draw(g);
		// }
	}

}
