package GameState.Animations;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	
	private int delay, frame = 0;
	private int image = 0;
	private List<BufferedImage> images = new ArrayList<>();
	private List<Dimension> dimensions = new ArrayList<>();

	public Animation(List<BufferedImage> images, List<Dimension> dimensions, int delay) {
		this.images = images;
		this.delay = delay;
		this.dimensions = dimensions;
	}
	
	public void update() {
		if (++frame == delay) {
			frame = 0;
			image+=(image + 1 == images.size()) ? -image : 1;
		}
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(images.get(image), x, y, dimensions.get(image).width, dimensions.get(image).height, null);
	}
	
	public void render(Graphics g, int x, int y, int width, int height) {
		g.drawImage(images.get(image), x, y, width, height, null);
	}
}
