package mapMaker2D;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class UI {
	//overlay that will hide itself after seconds of inactivity
	
	private Tile selectedTile;
	private LinkedHashMap<String,TileSet> sets;
	
	public UI(){
		sets = new LinkedHashMap<>();
	}
	
	public Tile getSelectedTile(){
		return selectedTile;
	}
	
	public Tile getTile(TileID id){
		
		return sets.get(id.getTileSet()).getTile(id);
		
	}

	public void addTileSet(BufferedImage tileSet, String path) {
		
		sets.put(path, value)
		
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		
	}
	
}
