package mapMaker2D;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class UI{
	//overlay that will hide itself after seconds of inactivity
	
	private Tile selectedTile;
	private TileSet selectedTileSet;
	private TileSetLoader tsl;
	private boolean inFocus;
	
	public UI(){
		tsl = new TileSetLoader();
		selectedTile = null;
	}
	
	public Tile getSelectedTile(){
		return selectedTile;
	}
	public TileSet getSelectedTileSet(){
		return selectedTileSet;
	}
	public TileSetLoader getTileSetLoader() {
		return tsl;
	}
	public Tile getTile(TileID id){
		
		return tsl.getSets().get(id.getTileSet()).getTile(id);
		
	}

	public void addTileSet(BufferedImage tileSet, String path, int tileSize) {

		TileSet tiles = new TileSet(tileSet, path, tileSize, tsl);
		tsl.getSets().put(path, tiles);
		if(selectedTile == null || selectedTileSet == null){
			selectedTileSet = tiles;
			selectedTile = tiles.getTile((TileID) tiles.getTiles().keySet().toArray()[0]);
		}
		
	}
	
	public void update(Point mouseLocation){
		
	}
	
	public void draw(Graphics g){
		g.drawImage(selectedTileSet.getTileSheet(), 0, 0, 64, 64, null);
	}

	
}




















