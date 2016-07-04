package mapMaker2D;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class TileSet {

	private LinkedHashMap<TileID, Tile> tiles;
	private String path;
	private int tileSize;
	
	public TileSet(BufferedImage tiles, String path){
		
		this.path = path;
		this.tiles = TileSetLoader.getTiles(tiles,tileSize,path);
	}
	
	public Tile getTile(TileID id){
		return tiles.get(id);
	}
}
