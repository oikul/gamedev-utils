package mapMaker2D;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class BuildMap {

	private int mapWidth, mapHeight;
	private boolean mouseDrag;
	private Point dragStart;
	private UI ui;
	private TileID[][] map;
	private TileSetLoader gts;

	public BuildMap(int mapWidth, int mapHeight) {

		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		mouseDrag = false;
		dragStart = new Point(0, 0);
		ui = new UI();
		map = new TileID[mapWidth][mapHeight];
		gts = new TileSetLoader();

	}

	public void update() {

		if (Main.input.isMouseDown(MouseEvent.BUTTON1)) {
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
		
		if(Main.input.isKeyDown(KeyEvent.VK_T)){
			String path = JOptionPane.showInputDialog(Main.getComponent(), "Please enter the path of the wanted tile sheet", 
					"Choose tile sheet", JOptionPane.PLAIN_MESSAGE);
			ui.addTileSet(gts.getTileSet(path),path);
		}

	}

	public void draw(Graphics g) {

		for(int x = 0; x < mapWidth; x++){
			for(int y = 0; y < mapHeight; y++){
				if(map[x][y] != null){
				g.drawImage(ui.getTile(map[x][y]).getImage(), x*Main.tileSize, y*Main.tileSize, null);
			}
			}
		}
		
	}

}
