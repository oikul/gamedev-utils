package mapMaker2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class BuildMap {

	private int mapWidth, mapHeight;
	private boolean mouseDrag, type_path;
	private String lastPath;
	private Point dragStart;
	private TileSetLoader tsl;
	private UI ui;
	private TileID[][] map;

	public BuildMap(int mapWidth, int mapHeight, UI ui) {

		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.ui = ui;
		lastPath = "";
		tsl = ui.getTileSetLoader();
		mouseDrag = type_path = false;
		dragStart = new Point(0, 0);
		map = new TileID[mapWidth][mapHeight];

	}

	public void update() {

		if(type_path){
			return;
		}
		
		if (Main.input.isMouseDown(MouseEvent.BUTTON1) && ui.getSelectedTile() != null) {
			Point p = Main.input.getMousePositionRelativeToComponent();
			TileID id = ui.getSelectedTile().getId();
			if (mouseDrag) {
				for (int x = dragStart.x; x <= (p.x / Main.tileSize); x++) {
					for (int y = dragStart.y; y <= (p.y / Main.tileSize); y++) {
						map[x][y] = id;
					}
				}
			} else {
				map[p.x / Main.tileSize][p.y / Main.tileSize] = id;
				dragStart.setLocation(p.x / Main.tileSize, p.y / Main.tileSize);
				mouseDrag = true;
			}
		} else {
			mouseDrag = false;
		}

		if (Main.input.isKeyDown(KeyEvent.VK_T)) {
			type_path = true;
			Main.input.artificialKeyReleased(KeyEvent.VK_T);
			Main.input.clearTypedAcum();
		}

	}

	public void draw(Graphics g) {

		if (type_path) {
			System.out.println("inloop");
			g.setColor(Color.black);
			g.fillRect(0, 0, Main.width, Main.height);
			g.setColor(Color.white);
			Font textFont = new Font("Verdana", Font.BOLD, 40);
			g.setFont(textFont);
			FontMetrics met = g.getFontMetrics();
			String type = "Please type the Tile Sheets path.";
			g.drawString(type, (Main.width - met.stringWidth(type)) / 2, (Main.height / 4));
			String path = Main.input.getTypedAcum();
			g.drawString(path, (Main.width - met.stringWidth(path)) / 2, (Main.height * 3 / 4));
			
			if(Main.input.isKeyDown(KeyEvent.VK_ENTER)){
				type_path = false;
			}
		} else {

			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {
					if (map[x][y] != null) {
						g.drawImage(ui.getTile(map[x][y]).getImage(), x * Main.tileSize, y * Main.tileSize, null);
					}
				}
			}
		}
	}

}
