package utils;

public class Player extends Entity{

	public Player(String name, String spritePath, int xPos, int yPos, int width, int height) {
		super(name, spritePath, xPos, yPos, width, height);
	}
	
	@Override
	public void update(){
		
	}
	
	@Override
	public void getFrame(){
		switch(direction){
		case UP:
			break;
		case LEFT:
			break;
		case DOWN:
			break;
		case RIGHT:
			break;
		default:
			break;
		}
	}

}
