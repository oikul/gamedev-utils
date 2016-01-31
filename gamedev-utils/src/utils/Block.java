package utils;

import java.awt.Graphics2D;
import java.awt.Image;

public class Block {
	
	private Image[] textures;
	private long time, animationWaitTime;
	private int index;

	public Block(String path, long animationWaitTime) {
		textures = ResourceLoader.getBlockSprites(path, 16, 16);
		this.animationWaitTime = animationWaitTime;
		time = System.currentTimeMillis();
	}
	
	public void update(){
		long newTime = System.currentTimeMillis();
		if(newTime > time + animationWaitTime){
			time = newTime;
			if(index < textures.length){
				index ++;
			}else{
				index = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2d, int x, int y){
		g2d.drawImage(textures[index], x, y, 16, 16, null);
	}

}
