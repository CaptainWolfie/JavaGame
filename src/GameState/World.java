package GameState;

import java.awt.Color;
import java.awt.Graphics;

import Utils.FileReader;

public class World {
	
	private int[][] blocks;
	
	public void init(String path) {
		FileReader reader = new FileReader();
		String[] elements = reader.readFile("src/Maps/Map.txt").split("\\s+"); // separate all file's elements
		int width = Integer.valueOf(elements[0]); // get how many tiles will be horizontal
		int height = Integer.valueOf(elements[1]); // get how many tils will be vertical

		
		blocks = new int[width][height]; // initializing the array
		for (int y = 0; y < height; y++) { // for-y-loop
			for (int x = 0; x < width; x++) { // for-x-loop
				blocks[x][y] = Integer.valueOf(elements[(width * y) + x + 2]); // + 2 because with and height are in the same array
			}
		}
	}
	
	
	public void render(Graphics g) {
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (blocks[x][y] == 0)
					g.setColor(Color.blue);
				else
					g.setColor(Color.white);
				g.fillRect(x*50, y*50, 50, 50);
			}
		}
	}

}
