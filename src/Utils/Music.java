package Utils;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
	
	static MediaPlayer mediaPlayer;
	public static void play(String name) {
		String file = "Sounds/Music/" + name;
		final URL resource = Music.class.getClassLoader().getResource(file);
		try {
			final Media media = new Media(resource.toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			mediaPlayer.setVolume(0.1);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public static MediaPlayer getMusicPlayer() {
		return mediaPlayer;
	}
}
