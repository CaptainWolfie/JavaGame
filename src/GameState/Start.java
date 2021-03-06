package GameState;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import CaptainWolfie.UIBlocks.UIMain;
import GameState.Entities.Player;
import GameState.GUIs.Inventory;
import GameState.Tiles.Tile;
import Utils.Assets;
import Utils.ImageLoader;
import Utils.Music;
import Utils.Screen;

public class Start implements Runnable {
	
	private Thread thread;
	
	private Camera camera;
	
	private boolean running;

	private BufferStrategy bs;

	private Inventory inventory;

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
		Music.play("FirstDay.mp3");
		Assets.init();
		screen = new Screen(TITLE, WIDTH, HEIGHT);
		world = World.getInstance(screen.getFrame());
		camera = new Camera(screen.getFrame(), world);
		String path = System.getProperty("user.home") + "\\Weliopy\\Map.txt";
		world.init(path, camera);
		player = new Player(screen, 0, 0, 16, 26, world, camera);
		UIMain.init(screen.getFrame());
		inventory = new Inventory();
		inventory.init();
	}
	
	// update the variables
	private void update(double latency) {
		inventory.update();

		if (screen.getKeyboard().pressed[KeyEvent.VK_F11]) {
			screen.getFrame().dispose();
			screen.getFrame().setUndecorated(!screen.getFrame().isUndecorated());
			screen.getFrame().setVisible(true);
			screen.getKeyboard().pressed[KeyEvent.VK_F11] = false;
		}
		
		world.update();
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

		/*
		 * Draw inventory
		 */
		inventory.render(g);
		/*
		 * Draw the current block
		 */
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - (screen.getFrame().getWidth() - screen.getFrame().getContentPane().getSize().width) / 2;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - (screen.getFrame().getHeight() - screen.getFrame().getContentPane().getSize().height) + (screen.getFrame().getWidth() - screen.getFrame().getContentPane().getSize().width) / 2;

		if (player.canInteract()) {
			g.drawImage(ImageLoader.loadImage("/textures/CurrentTile.png"),
					(mouseX - screen.getFrame().getLocation().x + (camera.getX() % Tile.getWidth())) / Tile.getWidth() * Tile.getWidth() - (camera.getX() % Tile.getWidth()),
					(mouseY - screen.getFrame().getLocation().y + (camera.getY() % Tile.getHeight())) / Tile.getHeight() * Tile.getHeight() - (camera.getY() % Tile.getHeight()),  null);

			screen.setCursor("Mine.png");
		} else
			screen.setCursor("Default.png");

		player.render(g);

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
	
	public Screen getScreen() {
		return screen;
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
