package GameState.WorldGenerator;

import java.util.Random;

public class Generator {

	public static String generateWorld(int width, int height, int seed) {
		Noise noise = new Noise(seed); // seed
		String world1 = width + " " + height + "\n";
		int[][] world = new int[width][height];
		int i = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				i++;
				if (y == noise.getNoise(x, height)){
					world[x][y] = 2;
				}  else if (y < noise.getNoise(x, height)) {
					world[x][y] = 0;
				} else {
					Random r = new Random();
					world[x][y] = r.nextInt(2) + 8;
				}

				world1 += world[x][y] + ((x == width - 1) ? "\n" : " ");
				System.out.println((i * 100) / (width * height) + "% - " + i + "/" + (width * height));
			}
		}
		return world1;
	}
}
