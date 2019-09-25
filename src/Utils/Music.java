package Utils;

import java.io.FileNotFoundException;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
	
	static MediaPlayer mediaPlayer;
	public static void play(String name) throws FileNotFoundException {
		String file = "../../res/Sounds/Music/" + name;
		final URL resource = Music.class.getResource(file);
		try {
			final Media media = new Media(resource.toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			mediaPlayer.setVolume(0.1);
		} catch (NullPointerException e) {
			throw new FileNotFoundException("Wrong music path: " + resource.getPath().toString());
		}
	}
	
	public static MediaPlayer getMusicPlayer() {
		return mediaPlayer;
	}
}
