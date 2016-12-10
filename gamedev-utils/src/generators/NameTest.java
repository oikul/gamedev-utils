package generators;

public class NameTest {

	public static void main(String[] args) {
		for(int i = 0; i < 100; i++){
			System.out.println(NameGenerator.generateName(2, i));
		}
	}

}
