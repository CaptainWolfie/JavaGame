package GameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import GameState.Tiles.Tile;
import Utils.FileReader;
import Utils.ImageLoader;

public class World {
	
	private int[][] blocks;
	private int width, height;
	private JFrame screen;
	
	private BufferedImage background;
	
	public World(JFrame frame){
		this.screen = frame;
	}
	
	public void init(String path) {
		background = ImageLoader.loadImage("/textures/BG1.jpg");
		FileReader reader = new FileReader();
		String[] elements = reader.readFile(path).split("\\s+"); // separate all file's elements
		width = Integer.valueOf(elements[0]); // get how many tiles will be horizontal
		height = Integer.valueOf(elements[1]); // get how many tils will be vertical
		
		blocks = new int[width][height]; // initializing the array
		for (int y = 0; y < height; y++) { // for-y-loop
			for (int x = 0; x < width; x++) { // for-x-loop
				blocks[x][y] = Integer.valueOf(elements[(width * y) + x + 2]); // + 2 because with and height are in the same array
			}
		}
	}
	
	
	public void render(Graphics g) {
		g.drawImage(background , 0, 0, screen.getWidth(), screen.getHeight(), null);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x,y).render(g, x * Tile.getWidth(), y * Tile.getHeight());
			}
		}
	}
	
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) // if index is out of bounds
			return Tile.grass;
		
		Tile tile = Tile.tiles[blocks[x][y]];
		if (tile == null)
			return Tile.grass;
		
		return tile;
	}

}
