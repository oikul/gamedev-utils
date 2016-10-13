package mapMaker2D;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import handlers.ResourceHandler;

public class SaveMap {
	// will save the map in a format that all the rest of my games will use

	private String pastFileLocation = null;

	public boolean saveAs(String fileLocation, BuildMap map) {

		StringBuilder saveFileContent = new StringBuilder("");

		ArrayList<String> tileSheetsPre = new ArrayList<String>();
		ArrayList<String> usedTilesPre = new ArrayList<String>();
		ArrayList<StringBuilder> mapTilesPre = new ArrayList<StringBuilder>();

		ArrayList<ArrayList<TileID>> allTiles = map.getMap();

		for (int x = 0; x < allTiles.size(); x++) {
			for (int y = 0; y < allTiles.get(x).size(); y++) {

				TileID id = allTiles.get(x).get(y);
				if (id != null) {

					int tref;
					String lol = id.getTileSet();
					if (!tileSheetsPre.contains(lol)) {
						tileSheetsPre.add(id.getTileSet());
					}

					if (usedTilesPre.contains(id.toString())) {
						tref = usedTilesPre.indexOf(id.toString());
						mapTilesPre.get(tref).append("," + x + "," + y);

					} else {
						usedTilesPre.add(id.toString());
						tref = usedTilesPre.size() - 1;
						mapTilesPre.add(new StringBuilder("-" + tref + "," + x + "," + y));
					}

				}
			}
		}

		for (String str : tileSheetsPre) {
			saveFileContent.append(":" + str +"\n");
		}

		for (String str : usedTilesPre) {
			saveFileContent.append("." + str + "," + usedTilesPre.indexOf(str) + "\n");
		}

		for (StringBuilder str : mapTilesPre) {
			saveFileContent.append(str + "\n");
		}

		int loc = 0;
		try {
			while(loc < 100){
			ResourceHandler.openInputStream("savedMaps/"+fileLocation+".mpsv");
			loc++;
			}
		} catch (IOException e1) {
		}
		

		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("savedMaps/"+fileLocation+""+loc+".mpsv"), "utf-8"));
			writer.write(saveFileContent.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

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
