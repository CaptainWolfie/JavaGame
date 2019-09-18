package GameState.WorldGenerator;

import java.util.Random;

public class Generator {

	private static String world;
	
	public static String generateWorld(int width, int height, int seed) {
		Noise noise = new Noise(seed); // seed
		world = width + " " + height + "\n"; // width and height
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (y < noise.getNoise(x, height)) {
					world += "0" + ((x == width - 1) ? "\n" : " ");
				} else {
					Random r = new Random();
					world += (r.nextInt(2) + 8) + ((x == width - 1) ? "\n" : " ");
				}
			}
		}
		return world;
	}
}
