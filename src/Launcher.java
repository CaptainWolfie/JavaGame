import GameState.Start;
import GameState.World;
import Utils.Music;
import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Created by: Matt Zafiriou
 * Github: CaptainWolfie
 * Start Date: 28/8/2019
 */
public class Launcher extends Application {
	
	
	public static void main(String[] args) {
		Start start = new Start("Game Launcher", 900, 700);
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	        	Music.getMusicPlayer().stop();
	    		World.getInstance(start.getScreen().getFrame()).saveWorld();;
	        }
	    }, "Shutdown-thread"));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}
}