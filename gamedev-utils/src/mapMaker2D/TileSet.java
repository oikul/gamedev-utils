package mapMaker2D;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class TileSet {

	private LinkedHashMap<TileID, Tile> tiles;
	private String path;
	
	public TileSet(BufferedImage tiles, String path){
		
		this.path = path;
		tiles = TileSetLoader.getTiles(tiles);
	}
	
	public Tile getTile(TileID id){
		return tiles.get(id);
	}
}
