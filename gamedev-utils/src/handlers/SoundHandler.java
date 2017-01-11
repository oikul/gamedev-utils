package handlers;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class SoundHandler {
	
	public static final SoundHandler ghostHurt = new SoundHandler("sound/ghost/ghost_hurt.wav");
	public static final SoundHandler ghost = new SoundHandler("sound/ghost/ghost.wav");
	public static final SoundHandler ghostLaughter = new SoundHandler("sound/ghost/ghost_laughter.wav");
	
	private Clip clip, clip1, clip2;
	
	public SoundHandler(String path){
		try {
			AudioInputStream sound = ResourceHandler.getSound(path);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
			clip1 = (Clip) AudioSystem.getLine(info);
			clip1.open(sound);
			clip2 = (Clip) AudioSystem.getLine(info);
			clip2.open(sound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * plays the clip
	 */
	public void play(){
		if(clip.isActive()){
			if(clip1.isActive()){
				if(clip2.isActive()){
					clip.setFramePosition(0);
					clip.start();
				}else{
					clip2.setFramePosition(0);
					clip2.start();
				}
			}else{
				clip1.setFramePosition(0);
				clip1.start();
			}
		}else{
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	/**
	 * stops the clip
	 */
	public void stop(){
		clip.stop();
	}
	
	/**
	 * loops the clip
	 */
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
