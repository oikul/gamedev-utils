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

	private int mapWidth, mapHeight, maxWidth, maxHeight;
	private boolean mouseDrag, loadTileSheet;
	private String lastPath, tileSize;
	private Point dragStart, mouseLocation;
	private TileSetLoader tsl;
	private UI ui;
	private ArrayList<ArrayList<TileID>> map;

	public BuildMap(int mapWidth, int mapHeight, UI ui) {

		this.mapWidth = maxWidth = mapWidth;
		this.mapHeight = maxHeight = mapHeight;
		this.ui = ui;
		lastPath = "";
		tileSize = "16";
		tsl = ui.getTileSetLoader();
		mouseDrag = loadTileSheet = false;
		dragStart = mouseLocation = new Point(0, 0);
		map = new ArrayList<ArrayList<TileID>>();
		for (int x = 0; x < mapWidth; x++) {
			map.add(new ArrayList<TileID>());
			for (int y = 0; y < mapHeight; y++) {
				map.get(x).add(null);
			}
		}
		ui.addTileSet(tsl.getTileSet("testTileSheet"), "testTileSheet", 16);

	}

	private void checkMapChange() {

		if (Main.input.isKeyDown(KeyEvent.VK_RIGHT)) {
			if (maxWidth <= mapWidth) {
				map.add(new ArrayList<TileID>());
				for (int y = 0; y < maxHeight; y++) {
					map.get(map.size() - 1).add(null);
				}
				maxWidth++;
			}
			mapWidth++;
			Main.input.artificialKeyReleased(KeyEvent.VK_RIGHT);
		} else if (Main.input.isKeyDown(KeyEvent.VK_LEFT)) {
			// map.remove(map.size() - 1);
			mapWidth--;
			Main.input.artificialKeyReleased(KeyEvent.VK_LEFT);
		} else if (Main.input.isKeyDown(KeyEvent.VK_UP)) {
			// for (int x = 0; x < mapWidth; x++) {
			// map.get(x).remove(map.get(x).size() - 1);
			// }
			mapHeight--;
			Main.input.artificialKeyReleased(KeyEvent.VK_UP);
		} else if (Main.input.isKeyDown(KeyEvent.VK_DOWN)) {
			if (maxHeight <= mapHeight) {
				for (int x = 0; x < maxWidth; x++) {
					map.get(x).add(null);
				}
				maxHeight++;
			}
			mapHeight++;
			Main.input.artificialKeyReleased(KeyEvent.VK_DOWN);
		}

	}

	private void checkPlayerTilePlacement() {

		if (Main.input.isMouseDown(MouseEvent.BUTTON1) && ui.getSelectedTile() != null) {
			Point p = Main.input.getMousePositionRelativeToComponent();
			TileID id = ui.getSelectedTile().getId();
			if (mouseDrag) {
				for (int x = dragStart.x; x <= Math.min(mapWidth-1,(p.x / Main.tileSize)); x++) {
					for (int y = dragStart.y; y <= Math.min(mapHeight-1, (p.y / Main.tileSize)); y++) {
						map.get(x).set(y, id);
					}
				}
			} else {
				map.get(Math.min(mapWidth-1,(p.x / Main.tileSize))).set(Math.min(mapHeight-1, (p.y / Main.tileSize)), id);
				dragStart.setLocation(Math.min(mapWidth-1,(p.x / Main.tileSize)), Math.min(mapHeight-1, (p.y / Main.tileSize)));
				mouseDrag = true;
			}
		} else {
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
					JOptionPane.PLAIN_MESSAGE, null, new String[] { "8", "16", "32", "64", "128" }, tileSize);
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

	public void update() {

		checkMapChange();
		checkPlayerTilePlacement();
		checkNewTileSheet();

		mouseLocation = Main.input.getMousePositionRelativeToComponent();

		ui.update(mouseLocation);

	}

	public void draw(Graphics g) {

		// if (type_path) {
		// g.setColor(Color.black);
		// g.fillRect(0, 0, Main.width, Main.height);
		// g.setColor(Color.white);
		// Font textFont = new Font("Verdana", Font.BOLD, 35);
		// g.setFont(textFont);
		// FontMetrics met = g.getFontMetrics();
		//
		// String line1 = "Please type the Tile Sheets path Then press enter.";
		// g.drawString(line1, (Main.width - met.stringWidth(line1)) / 2,
		// (Main.height / 4));
		// String line2 = "Assume the path starts with
		// '/src/resources/tileSheets/'";
		// g.drawString(line2, (Main.width - met.stringWidth(line2)) / 2,
		// (Main.height / 4) + met.getHeight());
		// String line3 = "and ends with '.png'.";
		// g.drawString(line3, (Main.width - met.stringWidth(line3)) / 2,
		// (Main.height / 4) + 2 * met.getHeight());
		//
		// String path = Main.input.getTypedAcum();
		// g.drawString(path, (Main.width - met.stringWidth(path)) / 2,
		// (Main.height * 3 / 4));
		//
		// if (Main.input.isKeyDown(KeyEvent.VK_ENTER)) {
		// type_path = false;
		// loadTileSheet = true;
		// lastPath = path;
		// Main.input.artificialKeyReleased(KeyEvent.VK_ENTER);
		// }
		// } else {

		g.setColor(Color.white);
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				g.fillRect(x * 16 + 1, y * 16 + 1, 14, 14);
				if (map.get(x).get(y) != null) {
					g.drawImage(ui.getTile(map.get(x).get(y)).getImage(), x * Main.tileSize, y * Main.tileSize, null);
				}
			}
		}
		
		BufferedImage img = ui.getSelectedTile().getImage();
		g.drawImage(img, mouseLocation.x - ui.getSelectedTileSet().getTileSize() / 2,
				mouseLocation.y - ui.getSelectedTileSet().getTileSize() / 2, null);

		ui.draw(g);
		// }
	}

}
