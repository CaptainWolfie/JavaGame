package Utils;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
	
	static MediaPlayer mediaPlayer;
	public static void play(String name) {
		String file = "../../res/Sounds/Music/" + name;
	    final URL resource = Music.class.getResource(file);
	    final Media media = new Media(resource.toString());
	    mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.play();
	    mediaPlayer.setVolume(0.1);
	}
	
	public static void loop(String path) {

	}
}
