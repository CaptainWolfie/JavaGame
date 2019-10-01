package GameState.WorldGenerator;

import java.util.Random;

public class Generator {

	private static String world;
	
	public static String generateWorld(int width, int height, int seed) {
		Noise noise = new Noise(seed); // seed
		world = width + " " + height + "\n"; // width and height
		int i = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				i++;
				if (y < noise.getNoise(x, height)) {
					world += "0" + ((x == width - 1) ? "\n" : " ");
				} else {
					Random r = new Random();
					world += (r.nextInt(2) + 8) + ((x == width - 1) ? "\n" : " ");
				}
				System.out.println((i * 100) / (width * height) + "% - " + i + "/" + (width * height));
			}

		}
		return world;
	}
}
