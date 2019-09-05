package GameState.Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Utils.Assets;

public class Tile {

	/*
	 * Tiles list
	 */
	public static Tile[] tiles = new Tile[256];
	
	/*
	 * List of tile objects
	 */
	public static Tile air = new Tile(null, 0, -1, false);
	public static Tile grass = new Tile(Assets.grass, 1, 10, true);
	
	
	private BufferedImage image;
	private boolean isSolid;
	private int health, id;
	private static int WIDTH = 16, HEIGHT = 16;
	
	/*
	 * @Args image: Tile's rendered image
	 * @Args id: Tile's ID in map file (ex: 1)
	 * @Args health: Tile's health (leave -1 for unbreakable)
	 * @Args isSolid: If player can touch it
	 */
	public Tile(BufferedImage image, int id, int health, boolean isSolid) {
		this.image = image;
		this.isSolid = isSolid;
		this.health = health;
		this.id = id;
		
		tiles[id] = this;
	}
	
	/*
	 * Render this tile only
	 */
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
	
	public boolean isUnbreakable() {
		return (getHealth() == -1) ? true : false;
	}
}
