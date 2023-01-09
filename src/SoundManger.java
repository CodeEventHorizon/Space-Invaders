import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
* Reference to sound effects used:
 * https://www.classicgaming.cc/classics/space-invaders/sounds
* */
public class SoundManger {
	final static String path = "src/Audio/";

	public final static Clip LOWPITCH = getClip("ufo_lowpitch");
	public final static Clip EXPLOSION = getClip("explosion");
	public final static Clip INVADERKILLED = getClip("invaderkilled");
	public final static Clip FASTINVADER_3 = getClip("fastinvader3");
	public final static Clip UFO_HIGHPITCH = getClip("ufo_highpitch");
	public final static Clip[] clips = {LOWPITCH, EXPLOSION, INVADERKILLED,
			FASTINVADER_3, UFO_HIGHPITCH};
	// DEBUG to check all sounds work from the array clips
	public static void main(String[] args) throws Exception{
		for (Clip clip : clips) {
			play(clip);
			Thread.sleep(1000);
		}
	}
	//method that allows to play a clip
	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}
	// method for getting .wav files (sound effects)
	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}
	// methods to play a sound
	public void playLowpitch() {
		play(LOWPITCH);
	}
	public void playExplosion() {
		play(EXPLOSION);
	}
	public void playInvaderKilled() {
		play(INVADERKILLED);
	}
	public void playFastInvader() {
		play(FASTINVADER_3);
	}
	public void playHighpitch() {
		play(UFO_HIGHPITCH);
	}
}