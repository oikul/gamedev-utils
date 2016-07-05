package mapMaker2D;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UI{
	//overlay that will hide itself after seconds of inactivity
	
	private Tile selectedTile;
	private TileSetLoader tsl;
	
	public UI(){
		tsl = new TileSetLoader();
		selectedTile = null;
	}
	
	public Tile getSelectedTile(){
		return selectedTile;
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
		
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		
	}

	
}




















