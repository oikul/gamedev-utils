package mapMaker2D;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class SaveMap {
	// will save the map in a format that all the rest of my games will use

	private String pastFileLocation = null;

	public boolean saveAs(String fileLocation, BuildMap map) {

		StringBuilder saveFileContent = new StringBuilder("");
		
		ArrayList<String> tileSheetsPre = new ArrayList<String>();
		ArrayList<String> usedTilesPre = new ArrayList<String>();
		ArrayList<StringBuilder> mapTilesPre = new ArrayList<StringBuilder>();

		ArrayList<ArrayList<TileID>> allTiles = map.getMap();
		
		for(int x = 0; x < allTiles.size(); x++){
			for(int y = 0; y < allTiles.get(x).size(); y++){
				
				TileID id = allTiles.get(x).get(y);
				if(id!=null){
				int tref;
				String lol = id.getTileSet();
				if(!tileSheetsPre.contains(lol)){
					tileSheetsPre.add(id.getTileSet());
				}
				
				if(usedTilesPre.contains(id.toString())){
					tref = usedTilesPre.indexOf(id.toString());
					mapTilesPre.get(tref).append(","+x+","+y);
					
				}else{
					usedTilesPre.add(id.toString());
					tref = usedTilesPre.size()-1;
					mapTilesPre.add(new StringBuilder("-"+tref+","+x+","+y));
				}
				
				}
			}
		}
				
				System.out.println(tileSheetsPre);
				System.out.println(usedTilesPre);
				System.out.println(mapTilesPre);
		
		

//		// saves tile sheets
//		ArrayList<String> keys = map.getTSL().getKeys();
//		int ref = 0;
//		for (String key : keys) {
//			tileSheets.append(": " + key + "," + map.getTSL().getSets().get(key).getTileSize() + "," + ref + "\n");
//			ref++;
//		}
//
//		// saves tiles pictures info
//		ArrayList<String> usedTilesTSave = new ArrayList<String>();
//		ArrayList<StringBuilder> mapTilesToSave = new ArrayList<StringBuilder>();
//
//		// saves map tiles
//		ArrayList<ArrayList<TileID>> tiles = map.getMap();
//
//		for (int x = 0; x < tiles.size(); x++) {
//			for (int y = 0; y < tiles.get(x).size(); y++) {
//				
//				int tsref = 0;
//				TileID id = tiles.get(x).get(y);
//				
//				if (id == null) {
//					continue;
//				}
//				if (keys.contains(id.getTileSet())) {
//					tsref = keys.indexOf(id.getTileSet());
//				} else {
//					keys.add(id.getTileSet());
//					tsref = keys.size() - 1;
//				}
//				
//				if (usedTilesTSave.contains(id.toString())) {
//					ref = usedTilesTSave.indexOf(id.toString());
//					mapTilesToSave.get(ref).append("," + id.getX() + "," + id.getY());
//				} else {
//					usedTilesTSave.add(id.toString());
//					ref = usedTilesTSave.size() - 1;
//					mapTilesToSave.get(ref).append(ref + "," + id.getX() + "," + id.getY());
//
//				}
//
//			}
//		}
//
//		for (String tile : usedTilesTSave) {
//
//			String path = tile.split(",")[2];
//			usedTiles.append("." + keys.indexOf(path) + "," + tile + "\n");
//
//		}

		// encode(tiles,sb,keys);

		return false;
	}

	private void encode(ArrayList<ArrayList<TileID>> tiles, StringBuilder sb, ArrayList<String> keys) {

	}

	public boolean save(BuildMap map) {

		if (pastFileLocation == null) {
			if (saveAs("defaultSaveLocation", map)) {
				return true;
			}
		} else {
			saveAs(pastFileLocation, map);
		}
		return false;

	}

	public static boolean saveImage(Image image, String filePath, TileID[][] map) {
		StringBuilder sb = new StringBuilder("");
		sb.append("hello");
		try {
			BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			bi.createGraphics().drawImage(image, 0, 0, null);
			File outputfile = new File("saved.png");
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
