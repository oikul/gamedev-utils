package mapMaker2D;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class TileSet {

	private LinkedHashMap<TileID, Tile> tiles;
	private BufferedImage tileSheet;
	private String path;
	private int tileSize;
	
	public TileSet(BufferedImage tileSheet, String path, int tileSize, TileSetLoader tsl){
		
		this.tileSheet = tileSheet;
		this.path = path;
		this.tileSize = tileSize;
		this.tiles = tsl.getTiles(tileSheet,tileSize,path);
	}

	public int getTileSize(){
		return tileSize;
	}
	public String getPath(){
		return path;
	}
	public Tile getTile(TileID id){
		return tiles.get(id);
	}
	public BufferedImage getTileSheet(){
		return tileSheet;
	}
}
