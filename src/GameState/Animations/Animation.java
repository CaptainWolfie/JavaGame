package GameState.Animations;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	
	private int delay, frame = 0;
	private int image = 0, replay;
	private List<BufferedImage> images = new ArrayList<>();
	private List<Dimension> dimensions = new ArrayList<>();

	public Animation(List<BufferedImage> images, List<Dimension> dimensions, int delay, int replay) {
		this.images = images;
		this.delay = delay;
		this.dimensions = dimensions;
		this.replay = replay;
	}
	
	public void update() {
		if (++frame == delay) { // change images
			frame = 0;
			image+=(image + 1 == images.size()) ? -image + replay : 1;
		}
	}
	
	/*
	 * Draw with the image's dimensions
	 */
	public void render(Graphics g, int x, int y, boolean backwards) {
		if (backwards)
			g.drawImage(images.get(image), x + dimensions.get(image).width, y, -dimensions.get(image).width, dimensions.get(image).height, null); // draw normal
		else
			g.drawImage(images.get(image), x, y, dimensions.get(image).width, dimensions.get(image).height, null); // draw normal
	}
	
	/*
	 * Draw with certain dimensions
	 */
	public void render(Graphics g, int x, int y, int width, int height, boolean backwards) {
		if (backwards)
			g.drawImage(images.get(image), x + width, y, -width, height, null); // draw backwards
		else
			g.drawImage(images.get(image), x, y, width, height, null); // draw normal
	}
}
