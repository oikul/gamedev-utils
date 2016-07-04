package mapMaker2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class TileSetLoader {
	
	private static TileSetLoader getts = new TileSetLoader();
	
	public static BufferedImage getTileSet(String path){
		
		try {
			URL url = getts.getClass().getClassLoader().getResource("" + path + ".png");
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("failed to load");
			e.printStackTrace();
		}
		return null;
	}
	
}
