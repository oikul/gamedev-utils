package handlers;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class SoundHandler {
	
	public static final SoundHandler ghostHurt = new SoundHandler("sound/ghost/ghost_hurt.wav");
	public static final SoundHandler ghost = new SoundHandler("sound/ghost/ghost.wav");
	public static final SoundHandler ghostLaughter = new SoundHandler("sound/ghost/ghost_laughter.wav");
	
	private Clip clip;
	private FloatControl gainControl;
	
	public SoundHandler(String path){
		try {
			AudioInputStream sound = ResourceHandler.getSound(path);
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	/**
	 * plays the clip
	 */
	public void play(){
		clip.setFramePosition(0);
		clip.start();
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
	
	public void setVolume(float volume){
		gainControl.setValue(volume);
	}

}
