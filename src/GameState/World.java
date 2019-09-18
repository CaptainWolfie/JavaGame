package GameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import GameState.Tiles.Tile;
import GameState.WorldGenerator.Generator;
import Utils.ImageLoader;

public class World {
	
	private int[][] blocks;
	private int width, height, xStart, xEnd, yStart, yEnd;
	
	private JFrame screen;
	private Camera camera;
	
	private BufferedImage background;
	
	public World(JFrame frame){
		this.screen = frame;
	}
	
	public void init(String path, Camera camera) {
		this.camera = camera;

		int seed = 3489431; // world's seed

		background = ImageLoader.loadImage("/textures/BG1.jpg"); // load background image
		
		String[] elements = Generator.generateWorld(1000, 50, seed).split("\\s+"); // separate all file's elements
		
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
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x,y).render(g, x * Tile.getWidth() - camera.getX(), y * Tile.getHeight() - camera.getY());
			}
		}
	}
	
	public void update() {
		xStart = Math.max(0, camera.getX() / Tile.getWidth() - 1); // if camera is < 0 then it will start from 0
		xEnd = Math.min(width, (camera.getX() + screen.getWidth()) / Tile.getWidth() + 2); // if camera is over width it will end at world's width
		yStart = Math.max(0, camera.getY() / Tile.getHeight() - 1); // same as xStart but for height
		yEnd = Math.min(height, (camera.getY() + screen.getHeight()) / Tile.getHeight() + 2); // same as xEnd but for height
	}
	
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) // if index is out of bounds
			return Tile.grass;
		
		Tile tile = Tile.tiles[blocks[x][y]];
		if (tile == null)
			return Tile.grass;
		
		return tile;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
