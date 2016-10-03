package mapMaker2D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class TileSet {

	private LinkedHashMap<TileID, Tile> tiles;
	private ArrayList<TileID> tilesKeys;
	private BufferedImage tileSheet;
	private String path;
	private int tileSize;

	public TileSet(BufferedImage tileSheet, String path, int tileSize, TileSetLoader tsl) {

		this.tileSheet = tileSheet;
		this.path = path;
		this.tileSize = tileSize;
		tilesKeys = new ArrayList<TileID>();
		this.tiles = tsl.getTiles(tileSheet, tileSize, path);
		update();
	}

	public boolean equals(Object obj) {
		try {
			return ((TileSet) obj).getPath().equals(this.path);
		} catch (ClassCastException e) {
			return false;
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public String getPath() {
		return path;
	}

	public Tile getTile(TileID id) {
		return tiles.get(id);
	}

	public BufferedImage getTileSheet() {
		return tileSheet;
	}

	public LinkedHashMap<TileID, Tile> getTiles() {
		return tiles;
	}

	private void update() {
		tilesKeys = new ArrayList<TileID>(Arrays.asList(tiles.keySet().toArray(new TileID[1])));
	}

	public ArrayList<TileID> getKeys() {
		return tilesKeys;
	}

	public TileID getKey(int index) {
		return tilesKeys.get(index);
	}
	public void close(){
		for (Tile t : tiles.values()) {
			t.close();
			t = null;
		}
		tiles = null;
		tilesKeys = null;
		tileSheet = null;
	}
}
