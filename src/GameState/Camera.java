package GameState;

import javax.swing.JFrame;

import GameState.Tiles.Tile;

public class Camera {

	private int x = 0, y = 0;
	
	private JFrame frame;
	private World world;
	
	public Camera(JFrame frame, World world) {
		this.frame = frame;
		this.world = world;
	}
	
	public void setX(int x) {
		if (x < 0) {
			this.x = 0;
			return;
		} else if ((x + frame.getWidth()) / Tile.getWidth() >= world.getWidth()) {
			this.x = world.getWidth() * Tile.getWidth() - frame.getWidth();
			return;
		}
		this.x = x;
	}
	
	public void setY(int y) {
		if (y < 0) {
			this.y = 0;
			return;
		} else if ((y + frame.getHeight()) / Tile.getHeight() >= world.getHeight()) {
			this.y = world.getHeight() * Tile.getHeight() - frame.getHeight();
			return;
		}
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
