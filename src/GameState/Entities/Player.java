package GameState.Entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.KeyEvent;

import GameState.Animations.Animation;
import Input.Keyboard;
import Utils.Assets;
import Utils.Screen;

public class Player {
	
	private enum Keys {
		LEFT,
		RIGHT
	}
	
	private Keys lastKey = Keys.RIGHT;

	private List<BufferedImage> images = new ArrayList<>();
	private List<Dimension> dimensions = new ArrayList<>();
	private Animation animation;
	private Keyboard keyboard;
	
	@SuppressWarnings("unused")
	private int x, y, width, height, speed = 4;
	
	public Player(Screen screen, int x, int y, int width, int height) {
		init();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.keyboard = screen.getKeyboard();
	}
	
	private void init() {
		for (List<BufferedImage> i : Assets.walk.keySet()) {
			images = i;
		}
		for (List<Dimension> i : Assets.walk.values()) {
			dimensions = i;
		}
		animation = new Animation(images, dimensions, 5, 2);
	}
	
	public void render(Graphics g) {
		animation.render(g, x, y, lastKey == Keys.LEFT);
	}
	
	public void update(double latency) {
		int speed = (int) (this.speed * latency); // fixed bug left was faster than right when you add the number that time
		
		/*
		 * If both buttons are pressed dont do anything
		 */
		if (!(keyboard.pressed[KeyEvent.VK_D] && keyboard.pressed[KeyEvent.VK_A])) {
			/*
			 * Only one of the buttons is pressed
			 */
			if (keyboard.pressed[KeyEvent.VK_D]) {
				lastKey = Keys.RIGHT;
				x+=speed;
			}
			if (keyboard.pressed[KeyEvent.VK_A]) {
				lastKey = Keys.LEFT;
				x-=speed;
			}
		}
		if (keyboard.pressed[KeyEvent.VK_W])
			y-=speed * latency;
		if (keyboard.pressed[KeyEvent.VK_S])
			y+=speed * latency;
	
		animation.update();
	}
}