package generators;

import java.awt.Rectangle;
import java.util.ArrayList;

import handlers.MathHandler;

public class RoomGenerator {
	
	public Rectangle[] generateRooms(int numOfRooms, int xLimit, int yLimit, int xMod, int yMod, int widthLimit,
			int heightLimit, int widthMod, int heightMod, int corridorSize) {
		ArrayList<Rectangle> rooms = new ArrayList<Rectangle>();
		for (int i = 0; i < numOfRooms; i++) {
			rooms.add(new Rectangle(MathHandler.random.nextInt(xLimit) + xMod, MathHandler.random.nextInt(yLimit) + yMod,
					MathHandler.random.nextInt(widthLimit) + widthMod,
					MathHandler.random.nextInt(heightLimit) + heightMod));
		}
		int pls = rooms.size();
		for (int i = 0; i < pls; i++) {
			for (int j = 0; j < pls; j++) {
				Rectangle r1 = rooms.get(i), r2 = rooms.get(j);
				int x, y, width, height;
				if (r1.x < r2.x) {
					x = r1.x + r1.width / 2;
					width = (r2.x + r2.width / 2) - x;
				} else {
					x = r2.x + r2.width / 2;
					width = (r1.x + r1.width / 2) - x;
				}
				if (r1.y < r2.y) {
					y = r1.y + r1.height / 2;
					height = (r2.y + r2.height / 2) - y;
				} else {
					y = r2.y + r2.height / 2;
					height = (r1.y + r1.height / 2) - y;
				}
				rooms.add(new Rectangle(x, y, width, corridorSize));
				rooms.add(new Rectangle(x, y, corridorSize, height));
			}
		}
		return rooms.toArray(new Rectangle[rooms.size()]);
	}

}
