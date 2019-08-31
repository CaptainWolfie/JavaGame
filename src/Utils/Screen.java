package Utils;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Input.Keyboard;

public class Screen {
	
	private int WIDTH, HEIGHT;
	
	private Canvas canvas;
	
	private JFrame frame;
	
	private Keyboard keyboard = new Keyboard();
	
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
		frame.addKeyListener(keyboard);
		setCursor();
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
		canvas.addKeyListener(keyboard);
		frame.add(canvas);
	}
	
	private void setCursor() {
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("res/textures/cursor.png").getImage(),
				new Point(0,0),"custom cursor"));
	}
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public Keyboard getKeyboard() {
		return keyboard;
	}

}
