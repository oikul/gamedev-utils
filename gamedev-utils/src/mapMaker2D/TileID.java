package mapMaker2D;

public class TileID {

	private String tileSet;
	private int x, y;
	private Tile tile;
	
	public TileID(String tileSet, int x, int y) {
		this.tileSet = tileSet;
		this.x = x;
		this.y = y;
	}
	
	public String getTileSet() {
		return tileSet;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
	
}
