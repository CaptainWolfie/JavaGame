package Utils;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage(){
		return image;
	}
	
	public BufferedImage crop(int x, int y, int width, int height) {
		return image.getSubimage(x, y, width, height);
	}
}
