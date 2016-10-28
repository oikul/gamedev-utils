package mapMaker2D;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LoadMap {

	public LoadMap() {

	}

	public BuildMap load(String path, BuildMap map) {

		BufferedReader reader;
		BuildMap newMap = null;

		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.err.println("Load file not found");
			return null;
		}
		int width = 0, height = 0;
		String[] splits;
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		ArrayList<TileUpdate> updates = new ArrayList<TileUpdate>();

		try {

			String line = reader.readLine();
			while (line != null) {
				splits = line.split(",");
				switch (line.charAt(0)) {
				case '!':
					width = Integer.parseInt(splits[1]);
					height = Integer.parseInt(splits[2]);
					newMap = new BuildMap(width, height, map.getUI());
					break;

				case ':':
					if (!newMap.getTSL().getSets().containsKey(splits[1])) {
						BufferedImage img = newMap.getTSL().getTileSet(splits[1]);
						newMap.getUI().addTileSet(img, splits[1], Integer.parseInt(splits[2]));
					}
					
					break;

				case '.':
					String tileSetPath = splits[3];
					TileSetLoader tsl = newMap.getTSL();
					LinkedHashMap<String, TileSet> sets = tsl.getSets();
					TileSet set = sets.get(tileSetPath);
					int num = set.getTileSize();
					tiles.add(set.getTile(
							new TileID(tileSetPath, Integer.parseInt(splits[1]), Integer.parseInt(splits[2]))));

					break;

				case '-':
					int ref = Integer.parseInt(splits[1]);
					Tile tile = tiles.get(ref);
					for (int i = 1; i <= (splits.length - 2) / 2; i++) {
						int x = Integer.parseInt(splits[2 * i]);
						int y = Integer.parseInt(splits[2 * i + 1]);
						updates.add(new TileUpdate(tile, new Point(x, y)));
					}
					break;
				default:
					System.err.println("Load file in incorrect format");
					reader.close();
					return null;
				}

				line = reader.readLine();
			}

			newMap.addMapChanges(updates);

			reader.close();

		} catch (IOException e) {
			System.err.println("Something went wrong");
		}
		newMap.updateTiles();

		return newMap;
	}

}
