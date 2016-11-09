package mapMaker2D;

import org.lwjgl.util.vector.Vector2f;

public class Tester {

	public static void main(String[] args) {

		
		Vector2f vec1 = new Vector2f(7, 7);
		Vector2f vec2 = new Vector2f(2,5);
		System.out.println(vec1+", "+vec2);
		Vector2f.sub(vec2, vec1, vec1);
		System.out.println(vec1+", "+vec2);
		
	}

}
