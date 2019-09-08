package GameState;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;

import GameState.Entities.Player;
import GameState.Tiles.Tile;
import Utils.Assets;
import Utils.ImageLoader;
import Utils.Music;
import Utils.Screen;

public class Start implements Runnable {
	
	private Thread thread;
	
	private boolean running;
	
	private BufferStrategy bs;
	
	private Player player;
	
	private World world;
	
	private Graphics g;
	
	private Screen screen;
	
	private int WIDTH, HEIGHT;
	
	private String TITLE;

	/*
	 * Constructor
	 */
	public Start(String title, int width, int height) {
		this.TITLE = title;
		this.WIDTH = width;
		this.HEIGHT = height;
		start(); // creates the thread
	}
	
	// initialize the variables
	private void init() {
		try {
			Music.play("FirstDay.mp3");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Assets.init();
		screen = new Screen(TITLE, WIDTH, HEIGHT);
		world = new World(screen.getFrame());
		world.init("src/Maps/Map.txt");
		player = new Player(screen, 0, 0, 22, 32, world);
	}
	
	// update the variables
	private void update(double latency) {
		player.update(latency);
	}
	
	// render the graphics on the screen
	private void render() {
		bs = screen.getCanvas().getBufferStrategy(); // creates buffer strategy
		if (bs == null) {
			screen.getCanvas().createBufferStrategy(2); // create buffers
			render();
			return;
		}
		g = bs.getDrawGraphics(); // create graphics
		g.clearRect(0, 0, WIDTH, HEIGHT); // clear screen
		// start drawing
		world.render(g);
		player.render(g);
		/*
		 * Draw the current block
		 */
		
		g.drawImage(ImageLoader.loadImage("/textures/CurrentTile.png"), (MouseInfo.getPointerInfo().getLocation().x - screen.getFrame().getLocation().x) / Tile.getWidth() * Tile.getWidth(), (MouseInfo.getPointerInfo().getLocation().y - screen.getFrame().getLocation().y) / Tile.getHeight() * Tile.getHeight(), null);
		// stop drawing
		bs.show(); // show
		g.dispose(); // destroy
	}
	
	
	
	
	
	// here's the loop
	@Override
	public void run() {
		// start the program
		init();
		render();
		
		int fps = 60;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		double ticks = 0;
		// start the loop
		while(running) {
			double timePerTick = 1000000000 / fps;
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1) {
				update(1/delta);
				render();
				ticks+=delta;
				delta = 0;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
	}
	
	
	
	
	/*
	 * Thread stuff
	*/
	public synchronized void start() {
		thread = new Thread(this);
		if (running)
			return;
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		if (!running)
			return;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}
}
