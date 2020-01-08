package GameState.Tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import GameState.Animations.Animation;
import Utils.Assets;

public class Tile {

	/*
	 * Tiles list
	 */
	public static Tile[] tiles = new Tile[256];
	private Animation animation;
	private List<BufferedImage> images = new ArrayList<>();
	private List<Rectangle> dimensions = new ArrayList<>();
	
	/*
	 * List of tile objects
	 */
	public static Tile air = new Tile(null, 0, -1, false);
	public static Tile topLeftGrass = new Tile(Assets.topLeftGrass, 1, 10, true);
	public static Tile grass = new Tile(Assets.grass, 2, 10, true);
	public static Tile topRightGrass = new Tile(Assets.topRightGrass, 3, 10, true);
	public static Tile leftGrass = new Tile(Assets.leftGrass, 4, 10, true);
	public static Tile rightGrass = new Tile(Assets.rightGrass, 5, 10, true);
	public static Tile bottomLeftGrass = new Tile(Assets.bottomLeftGrass, 6, 10, true);
	public static Tile bottomRightGrass = new Tile(Assets.bottomRightGrass, 7, 10, true);
	public static Tile dirt1 = new Tile(Assets.dirt1, 8, 10, true);
	public static Tile dirt2 = new Tile(Assets.dirt2, 9, 10, true);
	public static Tile dirt3 = new Tile(Assets.dirt3, 10, 10, true);
	public static Tile sandTop = new Tile(Assets.sandTop, 11, 10, true);
	public static Tile sandBottom = new Tile(Assets.sandBottom, 12, 10, true);
	public static Tile water = new Tile(Assets.water1, 13, -1, false);

	private BufferedImage image;
	private boolean isSolid;
	private int health, id;
	private static int WIDTH = 12, HEIGHT = 12;
	
	/*
	 * @Args image: Tile's rendered image
	 * @Args id: Tile's ID in map file (ex: 1)
	 * @Args health: Tile's health (leave -1 for unbreakable)
	 * @Args isSolid: If player can touch it
	 */
	private Tile(BufferedImage image, int id, int health, boolean isSolid) {
		this.image = image;
		this.isSolid = isSolid;
		this.health = health;
		this.id = id;
		
		tiles[id] = this;
		
		init();
		animation = new Animation(images, dimensions, 10, 0);
	}
	
	/*
	 * Initialize the images
	 */
	private void init() {
		if (id == 13) {
			images.add(Assets.water1);
			images.add(Assets.water2);
			dimensions.add(new Rectangle(WIDTH, HEIGHT));
			dimensions.add(new Rectangle(WIDTH, HEIGHT));
		}
	}
	
	/*
	 * Render this tile only
	 */
	public void render(Graphics g, int x, int y) {
		if (images.size() != 0) {
			animation.render(g, x, y, WIDTH, HEIGHT, false);
		} else
			g.drawImage(image, x, y, WIDTH, HEIGHT, null);
	}
	
	public static void updateAll() {
		for (int i = 0; i < tiles.length; i++) {
			try {
				tiles[i].update();
			} catch (NullPointerException e) {
				break;
			}
		}
	}
	
	public void update() {
		if (images.size() != 0)
			animation.update();
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
