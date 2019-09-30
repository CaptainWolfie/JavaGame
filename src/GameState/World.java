package GameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import GameState.Tiles.Tile;
import GameState.WorldGenerator.Generator;
import Utils.Assets;
import Utils.FileManager;
import Utils.ImageLoader;
import Utils.Security;

public class World {
	
	private int[][] blocks, health;
	private int width, height, xStart, xEnd, yStart, yEnd;
	
	private String path = "";
	
	private JFrame screen;
	private Camera camera;
	
	private static World world;
	
	private BufferedImage background;
	
	private World(JFrame frame){
		this.screen = frame;
	}
	
	public static World getInstance(JFrame frame) {
		if (world == null)
			world = new World(frame);
		return world;
	}
	
	public void init(String path, Camera camera) {
		this.camera = camera;
		this.path = path;
		
		int seed = 3489431; // world's seed

		background = ImageLoader.loadImage("/textures/BG1.jpg"); // load background image
		
		String[] elements = null;
		FileManager manager = new FileManager();

		if (!manager.fileExists(path))
			elements = Generator.generateWorld(1000, 50, seed).split("\\s+"); // separate all file's elements
		else {
			elements = Security.getInstance().decrypt(manager.readFile(path)).split("\\s+"); // separate all file's elements
		}
		
		width = Integer.valueOf(elements[0]); // get how many tiles will be horizontal
		height = Integer.valueOf(elements[1]); // get how many tils will be vertical

		blocks = new int[width][height]; // initializing the array
		health = new int[width][height];
		for (int y = 0; y < height; y++) { // for-y-loop
			for (int x = 0; x < width; x++) { // for-x-loop
				blocks[x][y] = Integer.valueOf(elements[(width * y) + x + 2]); // + 2 because with and height are in the same array
				health[x][y] = getTile(x,y).getHealth();
			}
		}
	}
	
	
	public void render(Graphics g) {
		g.drawImage(background , 0, 0, screen.getWidth(), screen.getHeight(), null);
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x,y).render(g, x * Tile.getWidth() - camera.getX(), y * Tile.getHeight() - camera.getY());
				if (getTile(x,y).isUnbreakable())
					continue;
				if (health[x][y] < (getTile(x,y).getHealth() / 4) * 4)
					g.drawImage(Assets.blockBreaking1, x * Tile.getWidth() - camera.getX(), y * Tile.getHeight() - camera.getY(), null);
				if (health[x][y] <= (getTile(x,y).getHealth() / 4) * 3)
					g.drawImage(Assets.blockBreaking2, x * Tile.getWidth() - camera.getX(), y * Tile.getHeight() - camera.getY(), null);
				if (health[x][y] <= (getTile(x,y).getHealth() / 4) * 2)
					g.drawImage(Assets.blockBreaking3, x * Tile.getWidth() - camera.getX(), y * Tile.getHeight() - camera.getY(), null);
				if (health[x][y] <= getTile(x,y).getHealth() / 4)
					g.drawImage(Assets.blockBreaking4, x * Tile.getWidth() - camera.getX(), y * Tile.getHeight() - camera.getY(), null);

			}
		}
		
	}
	
	public void update() {
		xStart = Math.max(0, camera.getX() / Tile.getWidth() - 1); // if camera is < 0 then it will start from 0
		xEnd = Math.min(width, (camera.getX() + screen.getWidth()) / Tile.getWidth() + 2); // if camera is over width it will end at world's width
		yStart = Math.max(0, camera.getY() / Tile.getHeight() - 1); // same as xStart but for height
		yEnd = Math.min(height, (camera.getY() + screen.getHeight()) / Tile.getHeight() + 2); // same as xEnd but for height
		
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				if (!getTile(x,y).isUnbreakable()) {
					if (health[x][y] <= 0) {
						health[x][y] = -1;
						blocks[x][y] = 0;
					}
				}
			}
		}
	}
	
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) // if index is out of bounds
			return Tile.grass;
		
		Tile tile = Tile.tiles[blocks[x][y]];
		if (tile == null)
			return Tile.grass;
		
		return tile;
	}
	
	public void setTile(int x, int y, Tile tile) throws ArrayIndexOutOfBoundsException {
		if (x < 0 || x >= width || y < 0 || y >= height) // if index is out of bounds
			throw new ArrayIndexOutOfBoundsException();
		else
			blocks[x][y] = tile.getId();
	}
	
	public String getWorld() {
		String world = width + " " + height + "\n";
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				world += blocks[x][y] + " " + (x + 1 == width ? "\n" : "");
			}
		}
		return world;
	}
	
	public int getHealth(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) // if index is out of bounds
			return -1;
		
		return health[x][y];
	}
	
	public void setHealth(int x, int y, int health) {
		if (x < 0 || x >= width || y < 0 || y >= height) // if index is out of bounds
			return;
		
		this.health[x][y] = health;
	}
	
	public void saveWorld() {
		FileManager manager = new FileManager();
		System.out.println("Saving world..");
		manager.writeFile(path, Security.getInstance().encrypt(getWorld()));
		System.out.println("World saved!");
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
