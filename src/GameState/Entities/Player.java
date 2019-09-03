package GameState.Entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import GameState.Animations.Animation;
import Utils.Assets;

public class Player {

	private List<BufferedImage> images = new ArrayList<>();
	private List<Dimension> dimensions = new ArrayList<>();
	private Animation animation;
	
	@SuppressWarnings("unused")
	private int x, y, width, height;
	
	public Player(int x, int y, int width, int height) {
		init();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	private void init() {
		for (List<BufferedImage> i : Assets.walk.keySet()) {
			images = i;
		}
		for (List<Dimension> i : Assets.walk.values()) {
			dimensions = i;
		}
		animation = new Animation(images, dimensions, 10);
	}
	
	public void render(Graphics g) {
		animation.render(g, x, y);
	}
	
	public void update() {
		animation.update();
	}
}