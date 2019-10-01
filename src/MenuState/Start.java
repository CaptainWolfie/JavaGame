package MenuState;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import Utils.Music;
import Utils.Screen;

public class Start implements Runnable {
	
	private Thread thread;
	
	private boolean running;
	
	private BufferStrategy bs;
	
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
		Music.play("MenuMusic.mp3");
		screen = new Screen(TITLE, WIDTH, HEIGHT);
	}
	
	// update the variables
	private void update() {
		
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
				update();
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
