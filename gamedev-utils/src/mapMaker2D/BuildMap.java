package mapMaker2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BuildMap {

	// The number of tiles wide and high the map is
	private int mapWidth, mapHeight;
	// flags
	private boolean mouseDrag, loadTileSheet, updateTiles, grid;
	private String tileSheetPath, tileSize, brushType;
	private Point dragStart, mouseLocation;
	private TileSetLoader tsl;
	private UI ui;
	private ArrayList<ArrayList<TileID>> map;
	private ArrayList<TileUpdate> mapUpdates;
	// private BufferedImage gridImage;
	private BufferedImage tileImage;

	public BuildMap(int mapWidth, int mapHeight, UI ui) {

		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.ui = ui;
		tileSheetPath = "";
		tileSize = "16";
		brushType = "line";
		tsl = ui.getTileSetLoader();
		mouseDrag = loadTileSheet = false;
		grid = true;

		dragStart = mouseLocation = new Point(50, 50);
		map = new ArrayList<ArrayList<TileID>>();
		mapUpdates = new ArrayList<TileUpdate>();
		for (int x = 0; x < mapWidth; x++) {
			map.add(new ArrayList<TileID>());
			for (int y = 0; y < mapHeight; y++) {
				map.get(x).add(null);
			}
		}

		// gridImage = new BufferedImage(mapWidth * Settings.resolution,
		// mapHeight * Settings.resolution,
		// BufferedImage.TYPE_INT_ARGB);
		tileImage = new BufferedImage(mapWidth * Settings.resolution, mapHeight * Settings.resolution,
				BufferedImage.TYPE_INT_ARGB);
		Graphics tg = tileImage.getGraphics();
		tg.setColor(Color.white);
		tg.fillRect(0, 0, tileImage.getWidth(), tileImage.getHeight());
		ui.addTileSet(tsl.getTileSet("testTileSheet"), "testTileSheet", 16);

	}

	public UI getUI() {
		return ui;
	}

	public TileSetLoader getTSL(){
		return tsl;
	}

	public ArrayList<ArrayList<TileID>> getMap(){
		return map;
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

		switch (brushType) {
		case "square":
			clickAndDragSquare();
			break;
		case "line":
			ClickAndDragLine();
			break;
		}

	}

	private void clickAndDragSquare() {
		// place selected tiles and drag logic
		updateTiles = false;

		if (Main.input.isMouseDown(MouseEvent.BUTTON3)) {
			mouseDrag = false;
			return;
		}
		TileID id = ui.getSelectedTile().getId();

		if (Main.input.isMouseDown(MouseEvent.BUTTON1)
				&& (mouseDrag || (ui.getSelectedTile() != null && pointInMap(mouseLocation)))) {

			if (!mouseDrag) {// start drag or single click
				// dragStart.setLocation((mouseLocation.x) / Main.tileSize -
				// (int) Main.XOffset,
				// mouseLocation.y / Main.tileSize - (int) Main.YOffset);
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

	private void ClickAndDragLine() {

		if (Main.input.isMouseDown(MouseEvent.BUTTON3)) {
			mouseDrag = false;
			return;
		}
		updateTiles = false;
		TileID id = ui.getSelectedTile().getId();
		int x = Math.max(0, Math.min(mouseLocation.x / Main.tileSize - (int) Main.XOffset, mapWidth - 1));
		int y = Math.max(0, Math.min(mouseLocation.y / Main.tileSize - (int) Main.YOffset, mapHeight - 1));

		if (Main.input.isMouseDown(MouseEvent.BUTTON1) && (ui.getSelectedTile() != null)) { // click

			if (pointInMap(mouseLocation)) {

				if (!mouseDrag) {

					map.get(x).set(y, id);
					mapUpdates.add(new TileUpdate(ui.getSelectedTile(), new Point(x, y)));

				} else {

					mapUpdates.addAll(drawLine(new Point(dragStart), new Point(x, y), id));

				}

				updateTiles = true;
				mouseDrag = true;

			} else {

				if (mouseDrag) {
					mapUpdates.addAll(drawLine(new Point(dragStart), new Point(x, y), id));
				}

			}

		} else { // no click

			mouseDrag = false;

		}
		dragStart.setLocation(x, y);

	}

	private ArrayList<TileUpdate> drawLine(Point start, Point end, TileID id) {
		ArrayList<TileUpdate> updates = new ArrayList<TileUpdate>();

		Point line = new Point(end.x - start.x, end.y - start.y);

		int xMultiplier = 1;
		int yMultiplier = 1;
		boolean swapCoords = false;

		float grad = (float) line.y / (float) line.x;

		if (line.y < 0) {
			yMultiplier = -1;
			line.setLocation(line.x, -line.y);
		}
		if (line.x < 0) {
			xMultiplier = -1;
			line.setLocation(-line.x, line.y);
		}
		if (Math.abs(grad) > 1) {
			swapCoords = true;
			line.setLocation(line.y, line.x);
		}

		grad = (float) line.y / (float) line.x;

		for (int x = 0; x <= line.x; x++) {

			int y = Math.round(x * grad);
			if (swapCoords) {
				updates.add(
						new TileUpdate(id.getTile(), new Point(start.x + y * xMultiplier, start.y + x * yMultiplier)));
				map.get(start.x + y * xMultiplier).set(start.y + x * yMultiplier, id);
			} else {
				updates.add(
						new TileUpdate(id.getTile(), new Point(start.x + x * xMultiplier, start.y + y * yMultiplier)));
				map.get(start.x + y * xMultiplier).set(start.y + x * yMultiplier, id);
			}

		}

		return updates;

	}

	private boolean pointInMap(Point point) {
		return point.x < (mapWidth + (int) Main.XOffset) * Main.tileSize
				&& point.y < (mapHeight + (int) Main.YOffset) * Main.tileSize
				&& point.x > ((int) Main.XOffset) * Main.tileSize && point.y > ((int) Main.YOffset) * Main.tileSize;
	}

	private void checkNewTileSheet() {

		if (Main.input.isKeyDown(KeyEvent.VK_T)) {
			// type_path = true;
			Main.forceFront = true;
			tileSheetPath = (String) Main.textEnterDialog("Please type the Tile Sheets path Then press enter. ",
					"Enter Path");
			// lastPath = (String) JOptionPane.showInputDialog(
			// "Please type the Tile Sheets path Then press enter. "
			// + "\nAssume the path starts with '/src/resources/tileSheets/'
			// \nand ends with '.png'.",
			// "Enter Path");
			if (tileSheetPath != null) {
				tileSize = (String) Main.multipleChoiceDialog("Please choose the size of the tiles on the tile sheet",
						"16", "TileSize", new String[] { "8", "16", "32", "64" }, JOptionPane.QUESTION_MESSAGE);
				// tileSize = (String) JOptionPane.showInputDialog("Please
				// choose the size of the tiles on the tile sheet",
				// new String[] { "8", "16", "32", "64" });
				loadTileSheet = true;
			}
			Main.forceFront = false;
			Main.input.artificialKeyReleased(KeyEvent.VK_T);
		}
		Main.forceFront = false;
		Main.input.artificialKeyReleased(KeyEvent.VK_T);

		if (loadTileSheet) {
			// change the tile size to a user inputed var-----------------------
			BufferedImage tileSetImage = tsl.getTileSet(tileSheetPath);
			if (tileSetImage != null) {
				int tileSize1 = 0;
				try {
					tileSize1 = Integer.parseInt(tileSize);
					ui.addTileSet(tileSetImage, tileSheetPath, tileSize1);
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

	public void update(Point mouseLocation) {

		this.mouseLocation = mouseLocation;
		if (Main.input.isKeyDown(KeyEvent.VK_G)) {
			grid = !grid;
			Main.input.artificialKeyReleased(KeyEvent.VK_G);
		}
		if (!ui.isInFocus()) {
			// checkMapChange();
			checkPlayerTilePlacement();

			if (Main.input.isKeyDown(KeyEvent.VK_T)) {
				checkNewTileSheet();
			}
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
		// Draw all map updates before here
		mapUpdates.removeAll(mapUpdates);

		int dx = (int) (Main.XOffset * size) / size * size;
		int dy = (int) (Main.YOffset * size) / size * size;

		g.drawImage(tileImage, dx, dy, dx + (tileImage.getWidth() * size) / Settings.resolution,
				dy + (tileImage.getHeight() * size) / Settings.resolution, 0, 0, tileImage.getWidth(),
				tileImage.getHeight(), null);

		if (!ui.isInFocus()) {
			BufferedImage img = ui.getSelectedTile().getImage();
			g.drawImage(img, (mouseLocation.x / size) * size, (mouseLocation.y / size) * size, size, size, null);

			if (mouseDrag && brushType.equals("square")) {
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

	public void close() {
		ui.close();
		ui = null;
		map = null;
		mapUpdates = null;
		tileImage = null;
	}

}
