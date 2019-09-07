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
	public static Tile topLeftGrass = new Tile(Assets.topLeftGrass, 1, 10, true);
	public static Tile grass = new Tile(Assets.grass, 2, 10, true);
	public static Tile toRightGrass = new Tile(Assets.topRightGrass, 3, 10, true);
	public static Tile leftGrass = new Tile(Assets.leftGrass, 4, 10, true);
	public static Tile rightGrass = new Tile(Assets.rightGrass, 5, 10, true);
	public static Tile bottomLeftGrass = new Tile(Assets.bottomLeftGrass, 6, 10, true);
	public static Tile bottomRightGrass = new Tile(Assets.bottomRightGrass, 7, 10, true);
	public static Tile dirt1 = new Tile(Assets.dirt1, 8, 10, true);
	public static Tile dirt2 = new Tile(Assets.dirt2, 9, 10, true);
	public static Tile dirt3 = new Tile(Assets.dirt3, 10, 10, true);
	public static Tile sandTop = new Tile(Assets.sandTop, 11, 10, true);
	public static Tile sandBottom = new Tile(Assets.sandBottom, 12, 10, true);

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
