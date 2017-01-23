package handlers;

public class SoundTest {

	public static void main(String[] args) {
		SoundHandler.ghost.play();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SoundHandler.ghost.setVolume(-20f);
		SoundHandler.ghost.play();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
