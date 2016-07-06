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
			mapWidth = Math.max(0, --mapWidth);
			Main.input.artificialKeyReleased(KeyEvent.VK_LEFT);
		} else if (Main.input.isKeyDown(KeyEvent.VK_UP)) {
			// for (int x = 0; x < mapWidth; x++) {
			// map.get(x).remove(map.get(x).size() - 1);
			// }
			mapHeight = Math.max(0, --mapHeight);
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
		// place selected tiles and drag logic
		if (Main.input.isMouseDown(MouseEvent.BUTTON3)) {
			mouseDrag = false;
		}
		TileID id = ui.getSelectedTile().getId();
		if (Main.input.isMouseDown(MouseEvent.BUTTON1) && ui.getSelectedTile() != null) {

			if (!mouseDrag) {// start drag or single click
				dragStart.setLocation(Math.max(0, Math.min(mapWidth - 1, (mouseLocation.x / Main.tileSize))),
						Math.max(0, Math.min(mapHeight - 1, (mouseLocation.y / Main.tileSize))));
			}
			mouseDrag = true;
		} else {// no click
			if (mouseDrag) {// end drag

				for (int x = Math.min(mouseLocation.x / Main.tileSize, dragStart.x); x < Math.max(0,
						Math.min(mapWidth - 1, Math.max(mouseLocation.x / Main.tileSize, dragStart.x))) + 1; x++) {

					for (int y = Math.min(mouseLocation.y / Main.tileSize, dragStart.y); y < Math.max(0,
							Math.min(mapHeight - 1, Math.max(mouseLocation.y / Main.tileSize, dragStart.y))) + 1; y++) {

						map.get(x).set(y, id);
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

		mouseLocation = Main.input.getMousePositionRelativeToComponent();
		if (!ui.isInFocus()) {
			checkMapChange();
			checkPlayerTilePlacement();
			checkNewTileSheet();
		} else {
			mouseDrag = false;
		}
		ui.update(mouseLocation);

	}

	public void draw(Graphics g) {

		g.setColor(Color.white);
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				g.fillRect(x * Main.tileSize + 1, y * Main.tileSize + 1, Main.tileSize - 2, Main.tileSize - 2);
				if (map.get(x).get(y) != null) {
					g.drawImage(ui.getTile(map.get(x).get(y)).getImage(), x * Main.tileSize, y * Main.tileSize, null);
				}
			}
		}
		if (!ui.isInFocus()) {
			BufferedImage img = ui.getSelectedTile().getImage();
			g.drawImage(img, (mouseLocation.x / Main.tileSize) * Main.tileSize,
					(mouseLocation.y / Main.tileSize) * Main.tileSize, null);

			if (mouseDrag) {
				g.setColor(new Color(127, 0, 255, 127));
				g.fillRect(Math.min((mouseLocation.x / Main.tileSize) * Main.tileSize, dragStart.x * Main.tileSize),
						Math.min((mouseLocation.y / Main.tileSize) * Main.tileSize, dragStart.y * Main.tileSize),
						Math.abs(((mouseLocation.x / Main.tileSize) * Main.tileSize) - (dragStart.x * Main.tileSize))
								+ Main.tileSize,
						Math.abs(((mouseLocation.y / Main.tileSize) * Main.tileSize) - (dragStart.y * Main.tileSize))
								+ Main.tileSize);
			}
		}
		ui.draw(g);
		// }
	}

}
