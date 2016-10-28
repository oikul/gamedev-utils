package mapMaker2D;

import java.util.LinkedHashMap;

public class Tester {

	public static void main(String[] args) {

		
		TileID id1 = new TileID("test1",1,1);
		TileID id2 = new TileID("test1",1,1);
		LinkedHashMap<TileID, String> list = new LinkedHashMap<>();
		list.put(id1, "working");
		System.out.println(list.containsKey(id2));
		
	}

}
