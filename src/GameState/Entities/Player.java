package GameState.Entities;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import GameState.Camera;
import GameState.World;
import GameState.Animations.Animation;
import GameState.Tiles.Tile;
import Input.Keyboard;
import Input.Mouse;
import Utils.Assets;
import Utils.Screen;

public class Player {
	
	private enum Keys {
		LEFT,
		RIGHT
	}
	
	private enum State {
		WALKING,
		RUNNING,
		STILL,
		USING_TOOL
	}
	
	// useful enums variables
	private Keys lastKey = Keys.RIGHT;
	private State imagesState = State.STILL;

	// useful objects
	private List<BufferedImage> images = new ArrayList<>();
	private List<Rectangle> dimensions = new ArrayList<>();
	private Animation animation;
	private Keyboard keyboard;
	private Mouse mouse;
	private World world;
	private Camera camera;
	private JFrame frame;
	
	// gravity variables
	private boolean falling = false, jumping = false;
	private float kinetic = 20, dynamic = 11.4f;
	// player variables
	private int walkSpeed = 4, runSpeed = 6, imagesWalkSpeed = 5, imagesRunSpeed = 3;
	
	// player dimension variables
	private int x, y, width, height, speed = 4, range = 3;
	
	// block break
	private int blockX, blockY;
	
	public Player(Screen screen, int x, int y, int width, int height, World world, Camera camera) {
		init();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.frame = screen.getFrame();
		this.keyboard = screen.getKeyboard();
		this.mouse = screen.getMouse();
		this.world = world;
		this.camera = camera;
	}
	
	private void init() {
		for (List<BufferedImage> i : Assets.still.keySet()) {
			images = i;
		}
		for (List<Rectangle> i : Assets.still.values()) {
			dimensions = i;
		}
		animation = new Animation(images, dimensions, imagesWalkSpeed, 2);
	}
	
	public void render(Graphics g) {
		animation.render(g, x - camera.getX(), y - camera.getY(), width, height, lastKey == Keys.LEFT);
	}
	
	public void update(double latency) {
		int speed = (int) (this.speed * latency); // fixed bug left was faster than right when you add the number that time
		
		if (imagesState == State.USING_TOOL) {
			
			if (animation.getCurrentImage() == animation.getMaxImages()) {
				if (canInteract(blockX, blockY))
					world.setHealth(blockX, blockY, world.getHealth(blockX, blockY) - 10);
				changeState(State.STILL);
			}
		}
		
		/*
		 * If both buttons are pressed dont do anything
		 */
		if (!(keyboard.pressed[KeyEvent.VK_D] && keyboard.pressed[KeyEvent.VK_A]) && imagesState != State.USING_TOOL && (keyboard.pressed[KeyEvent.VK_D]
				|| keyboard.pressed[KeyEvent.VK_A]) || keyboard.pressed[KeyEvent.VK_SHIFT]) {
			/*
			 * LEFT or RIGHT or SHIFT are pressed but not both left and right
			 */
			if (keyboard.pressed[KeyEvent.VK_SHIFT])
				changeState(State.RUNNING);
			else
				changeState(State.WALKING);
			if (keyboard.pressed[KeyEvent.VK_D]) {
				lastKey = Keys.RIGHT;
				moveRight(speed);
			}
			if (keyboard.pressed[KeyEvent.VK_A]) {
				lastKey = Keys.LEFT;
				moveLeft(speed);
			}
		} else
			if (imagesState != State.USING_TOOL)
				changeState(State.STILL);
		
		if (mouse.LeftClick) {
			if (canInteract() && !imagesState.equals(State.USING_TOOL) && world.getTile(getXBlockAtMouse(), getYBlockAtMouse()) != Tile.air) {
				blockX = getXBlockAtMouse();
				blockY = getYBlockAtMouse();
				changeState(State.USING_TOOL);
			}
		}

		if (mouse.RightClick) {
			world.setBlock(getXBlockAtMouse(), getYBlockAtMouse(), Tile.sandTop);
		}

		if (keyboard.pressed[KeyEvent.VK_W] && !jumping && !falling) {
			jumping = true;
			kinetic = 35;
			dynamic = 2;
		}
		
		
		if (jumping)
			jump();
		
		updateGravity();
		
		if (y - camera.getY() >= frame.getHeight() - 100)
			camera.setY((int)(camera.getY() + kinetic/dynamic));
			
		if (y - camera.getY() <= 100)
			camera.setY((int)(camera.getY() - kinetic/dynamic));
		
		animation.update();
	}
	
	private void changeState(State state) {
		if (imagesState == state)
			return;
		
		else {
			imagesState = state;
			if (state == State.STILL) {
				for (List<BufferedImage> i : Assets.still.keySet()) {
					images = i;
				}
				for (List<Rectangle> i : Assets.still.values()) {
					dimensions = i;
				}
				animation.setImages(images, dimensions, false);
				speed = walkSpeed;
				animation.setDelay(imagesWalkSpeed);
			} else if (state == State.RUNNING) {
				for (List<BufferedImage> i : Assets.walk.keySet()) {
					images = i;
				}
				for (List<Rectangle> i : Assets.walk.values()) {
					dimensions = i;
				}
				animation.setImages(images, dimensions, true);
				speed = runSpeed;
				animation.setDelay(imagesRunSpeed);
			} else {
				for (List<BufferedImage> i : Assets.walk.keySet()) {
					images = i;
				}
				for (List<Rectangle> i : Assets.walk.values()) {
					dimensions = i;
				}
				animation.setImages(images, dimensions, false);
				speed = walkSpeed;
				animation.setDelay(speed);
			}
			
			if (state == State.USING_TOOL) {
				for (List<BufferedImage> i : Assets.use.keySet()) {
					images = i;
				}
				for (List<Rectangle> i : Assets.use.values()) {
					dimensions = i;
				}
				animation.setImages(images, dimensions, false);
				animation.setDelay(5);
			}
		}
	}

	private void updateGravity() {
		// check for falling
		try {
			boolean tileFound = false;
			for (int i = 8; i < width - 7; i++) {
				if (world.getTile((x + i) / Tile.getWidth(), getWorldY() + 2).isSolid()) {
					tileFound = true;
				}
			}
			/*
		  	 * Check if player is not jumping and has no tile under their legs
		 	 */
			if (!falling && !jumping && !tileFound) // getWorldY() + 2 because the y is on the top of player's head
				falling = true;
			
			/*
		 	 * check if player has something under their legs
		 	 */
			if (tileFound)
				falling = false;
			
			if (getWorldY() < 0 && !jumping)
				falling = true;
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			/*
			 * Tile is null
			 */
			falling = true;
		}
		
		/*
		 * if player is falling change their position
		 */
		if (falling && !jumping) {
			kinetic+=3;
			if (kinetic >= 80)
				kinetic = 79;
			else
				dynamic-=.2;
			
			// player's Y if we change their position from here
			int playerFinalY = (int)(getWorldY() * Tile.getHeight() + kinetic/dynamic)/Tile.getHeight();
			// if any block found solid between player Y and playerFinalY
			boolean blockFound = false;
			// check for each x of player asset after the 8th x and 7 before
			for (int x1 = 8; x1 < width - 7; x1++) {
				for (int y1 = 1; y1 <= playerFinalY - getWorldY() + 1; y1++) {
					// check if a tile between player and final player y is solid
					if (world.getTile((x + x1) / Tile.getWidth(), (y + speed) / Tile.getHeight() + 1 + y1).isSolid()) {
						y = (getWorldY() + y1) * Tile.getHeight(); // here we dont have +2 because the Y is on the top of player's head and this time we dont check for any blocks under his legs
						falling = false;
						blockFound = true;
					}
				}
			}
			if (!blockFound)
				y+=kinetic/dynamic;
		}
		
		
		/*
		 * Change player's energies
		 */
		if (!falling && !jumping && (dynamic != 11.4 || kinetic != 20)) {
			kinetic = 20;
			dynamic = 11.4f;
		}
	}
	
	private void moveRight(int speed) {
		boolean tileFound = false;
		for (int i = 2; i < height - 4; i++) {
			if (world.getTile((x - 7 + width + speed) / Tile.getWidth(), (y + i) / Tile.getWidth()) != Tile.air)
				tileFound = true;
		}
		if (tileFound)
			return;
		
		if (frame.getWidth() - (x - camera.getX()) <= 170)
			camera.setX(camera.getX() + speed);
		x+=speed;
	}
	
	private void moveLeft(int speed) {
		boolean tileFound = false;
		for (int i = 2; i < height - 4; i++) {
			if (x - camera.getX() - speed + 8 <= 0) // if player - speed is < 0
				tileFound = true;
			
			if (world.getTile((x + 8 - speed) / Tile.getWidth(), (y + i) / Tile.getWidth()) != Tile.air)
				tileFound = true;
		}
		if (tileFound)
			return;
		
		if (x - camera.getX() <= 170)
			camera.setX(camera.getX() - speed);
		x-=speed;
	}
	
	private void jump() {
		if (kinetic <= 0) {
			jumping = false;
			kinetic = 20;
			dynamic = 11.4f;
			falling = true;
			return;
		}
		kinetic-=2;
		dynamic+=.3;
		
		boolean blockFound = false;
		// check for each x of player asset after the 8th x and 7 before
		for (int x1 = 9; x1 <= width - 7; x1++) {
			if (world.getTile((x + x1) / Tile.getWidth(), (int)((y - kinetic/dynamic) / Tile.getHeight())).isSolid()) {
				blockFound = true;
				kinetic = 0;
			}
		}
		if (!blockFound)
			y-=kinetic/dynamic;
	}
	
	
	public int getWorldX() {
		return (x + 8) / Tile.getWidth();
	}
	
	public int getWorldY() {
		return y / Tile.getHeight();
	}
	
	public int getRange() {
		return range;
	}
	
	public int getXBlockAtMouse() {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - (frame.getWidth() - frame.getContentPane().getSize().width) / 2;

		int mouseBlockX = ((mouseX - frame.getLocation().x) + camera.getX()) / Tile.getWidth();
		
		return mouseBlockX;
	}
	
	public int getYBlockAtMouse() {
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - (frame.getHeight() - frame.getContentPane().getSize().height) + (frame.getWidth() - frame.getContentPane().getSize().width) / 2;

		int mouseBlockY = ((mouseY - frame.getLocation().y) + camera.getY()) / Tile.getHeight();
		
		return mouseBlockY;
	}
	
	public boolean canInteract() {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - (frame.getWidth() - frame.getContentPane().getSize().width) / 2;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - (frame.getHeight() - frame.getContentPane().getSize().height) + (frame.getWidth() - frame.getContentPane().getSize().width) / 2;

		int mouseBlockX = ((mouseX - frame.getLocation().x) + camera.getX()) / Tile.getWidth();
		int mouseBlockY = ((mouseY - frame.getLocation().y) + camera.getY()) / Tile.getHeight();
		
		if (mouseBlockX > getWorldX() + getRange() || mouseBlockX < getWorldX() - getRange() ||
				mouseBlockY - 1 > getWorldY() + getRange() || mouseBlockY < getWorldY() - getRange())
			return false;
		else
			return true;
	}
	
	public boolean canInteract(int x, int y) {
		if (x > getWorldX() + getRange() || x < getWorldX() - getRange() ||
				y - 1 > getWorldY() + getRange() || y < getWorldY() - getRange())
			return false;
		else
			return true;
	}
	
}