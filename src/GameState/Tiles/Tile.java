package GameState.Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Utils.Assets;

public class Tile {
	
	public static Tile[] tiles = new Tile[256];
	public static Tile grass = new Tile(Assets.grass, 0, 10, true);
	
	
	private BufferedImage image;
	private boolean isSolid;
	private int health, id;
	private static int WIDTH = 16, HEIGHT = 16;
	
	public Tile(BufferedImage image, int id, int health, boolean isSolid) {
		this.image = image;
		this.isSolid = isSolid;
		this.health = health;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(image, x, y, WIDTH, HEIGHT, null);
	}
	
	public boolean isSolid() {
		return isSolid;
	}
	
	public int getId() {
		return id;
	}
	
	public int getHealth() {
		return health;
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
}
