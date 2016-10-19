package mapMaker2D;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadMap {

	public LoadMap() {

	}

	public BuildMap load(String path, BuildMap map) {

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.err.println("Load file not found");
			return null;
		}
		int width = 0, height = 0;
		try {

			String line = reader.readLine();

			while (line != null) {

				switch (line.charAt(0)) {
				case '!':
					break;
				case ':':
					break;
				case '.':
					break;
				case '-':
					break;
				default:
					System.err.println("Load file in incorrect format");
					return null;
				}

			}

		} catch (IOException e) {

		}

		BuildMap newMap = null;

		return newMap;
	}

}
