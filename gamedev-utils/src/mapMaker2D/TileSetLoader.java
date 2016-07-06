package mapMaker2D;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

import utils.MathHelper;

// class can be optimised to do everything at once and return only needed info
public class TileSetLoader {

	private LinkedHashMap<String,TileSet> sets = new LinkedHashMap<String,TileSet>();
	
	public BufferedImage getTileSet(String path) {
		sets.
		try {
			URL url = this.getClass().getClassLoader().getResource("resources/tileSheets/" + path + ".png");
			return ImageIO.read(url);
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
//			e.printStackTrace();
//			System.out.println("failed to load");
		}
		return null;
	}

	public LinkedHashMap<TileID, Tile> getTiles(BufferedImage tileSetImage, int tileSize, String path) {

		LinkedHashMap<TileID, Tile> tiles = new LinkedHashMap<TileID, Tile>();

		int tilesWide = MathHelper.ceiling(tileSetImage.getWidth(), tileSize);
		int tilesHigh = MathHelper.ceiling(tileSetImage.getHeight(), tileSize);

		for (int x = 0; x < tilesWide; x++) {
			for (int y = 0; y < tilesHigh; y++) {
				BufferedImage tileImage = new BufferedImage(Main.tileSize, Main.tileSize, BufferedImage.TYPE_INT_ARGB);
				Graphics g = tileImage.getGraphics();
				g.drawImage(tileSetImage, 0, 0, Main.tileSize, Main.tileSize, x * tileSize, y * tileSize,
						(x + 1) * tileSize - 1, (y + 1) * tileSize - 1, null);
				TileID id = new TileID(path, x, y);
				Tile tile = new Tile(id, tileImage);
				tiles.put(id, tile);
			}
		}
		return tiles;

	}

	public LinkedHashMap<String,TileSet> getSets() {
		return sets;
	}

}






















