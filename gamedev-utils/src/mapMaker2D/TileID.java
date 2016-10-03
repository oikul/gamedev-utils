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

	public boolean equals(Object obj) {
		boolean test = false;
		TileID externalID;
		try {
			externalID = ((TileID) obj);
		} catch (ClassCastException e) {
			return false;
		}
		test = externalID.tileSet.equals(tileSet);
		test &= externalID.x == x;
		test &= externalID.y == y;
		return test;
	}
	
	public String toString(){
		return ""+x+","+y+","+tileSet;
		
	}

	public void setTile(Tile tile) {
		this.tile = tile;
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

	public Tile getTile() {
		return tile;
	}

}
