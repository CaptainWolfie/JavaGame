package GameState.Animations;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	
	private int delay, frame = 0;
	private int image = 0, replay;
	private List<BufferedImage> images = new ArrayList<>();
	private List<Rectangle> dimensions = new ArrayList<>();

	public Animation(List<BufferedImage> images, List<Rectangle> dimensions2, int delay, int replay) {
		this.images = images;
		this.delay = delay;
		this.dimensions = dimensions2;
		this.replay = replay;
	}
	
	public void update() {
		if (++frame >= delay) { // change images
			frame = 0;
			image+=(image + 1 == images.size()) ? -image + replay : 1;
			if (image >= images.size())
				image = 0;
		}
	}
	
	/*
	 * Draw with the image's dimensions
	 */
	public void render(Graphics g, int x, int y, boolean backwards) {
		if (backwards)
			g.drawImage(images.get(image), x + dimensions.get(image).width + dimensions.get(image).x, y + dimensions.get(image).y, -dimensions.get(image).width, dimensions.get(image).height, null); // draw normal
		else
			g.drawImage(images.get(image), x + dimensions.get(image).x, y + dimensions.get(image).y, dimensions.get(image).width, dimensions.get(image).height, null); // draw normal
	}
	
	/*
	 * Draw with certain dimensions
	 */
	public void render(Graphics g, int x, int y, int width, int height, boolean backwards) {
		if (backwards)
			g.drawImage(images.get(image), x + width - dimensions.get(image).x, y + dimensions.get(image).y, -width + dimensions.get(image).x, height + dimensions.get(image).y, null); // draw backwards
		else
			g.drawImage(images.get(image), x + dimensions.get(image).x, y + dimensions.get(image).y, width - dimensions.get(image).x, height - dimensions.get(image).y, null); // draw normal
	}
	
	public void setImages(List<BufferedImage> images, List<Rectangle> dimensions, boolean keepPosition) {
		this.dimensions = dimensions;
		this.images = images;
		if (!keepPosition)
			image = 0;
	}
	
	/*
	 * Returns the current image is drawing and +1 is used to know how many images are left from getMaxImages() for example: 3/10 images
	 */
	public int getCurrentImage() {
		return image + 1;
	}
	
	/*
	 * How many images our current array has
	 */
	public int getMaxImages() {
		return images.size();
	}
	
	/*
	 * The images speed
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
}
