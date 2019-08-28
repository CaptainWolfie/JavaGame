package Utils;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Screen {
	
	private int WIDTH, HEIGHT;
	
	private Canvas canvas;
	
	private JFrame frame;
	
	public Screen(String TITLE, int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		createFrame(TITLE); // create the frame and canvas
		
	}
	
	private void createFrame(String TITLE) {
		frame = new JFrame(TITLE);
		frame.setSize(new Dimension(WIDTH,HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		createCanvas();
		frame.pack();
	}
	
	private void createCanvas() {
		canvas = new Canvas();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		canvas.setPreferredSize(size);
		canvas.setMinimumSize(size);
		canvas.setMaximumSize(size);
		
		frame.add(canvas);
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

}
