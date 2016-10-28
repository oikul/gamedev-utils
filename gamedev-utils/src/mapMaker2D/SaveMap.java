package mapMaker2D;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SaveMap implements Runnable{
	// will save the map in a format that all the rest of my games will use

	private String pastFileLocation = null;
	private BufferedImage saveImage;

	public boolean saveAs(String fileLocation, BuildMap map) {

		if (fileLocation == null) {
			fileLocation = "defaultSaveLocation";
		}

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
						mapTilesPre.add(new StringBuilder("-," + tref + "," + x + "," + y));
					}

				}
			}
		}

		saveFileContent.append("!,"+map.getWidth()+","+map.getHeight()+"\n");
		
		for (String str : tileSheetsPre) {
			saveFileContent.append(":," + str + "," + map.getTSL().getSets().get(str).getTileSize() + "\n");
		}

		for (String str : usedTilesPre) {
			saveFileContent.append(".," + str + "," + usedTilesPre.indexOf(str) + "\n");
		}

		for (StringBuilder str : mapTilesPre) {
			saveFileContent.append(str + "\n");
		}

		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("savedMaps/" + fileLocation + ".mpsv"), "utf-8"));
			writer.write(saveFileContent.toString());
			writer.close();
		} catch (IOException e) {
			return false;
		}
		pastFileLocation = fileLocation;
		return true;
	}

	public boolean save(BuildMap map) {

		if (pastFileLocation == null) {
			return saveAs("defaultSaveLocation", map);
		}
		return saveAs(pastFileLocation, map);

	}

	public boolean saveImage(BuildMap map) {
		BufferedImage image = map.getMapImage();
		saveImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		saveImage.createGraphics().drawImage(image, 0, 0, null);
		Thread saveImage = new Thread(this);
		saveImage.start();
		return true;
	}

	@Override
	public void run() {
		try {
			File outputfile = new File("mapPictures/saved.png");
			ImageIO.write(saveImage, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
