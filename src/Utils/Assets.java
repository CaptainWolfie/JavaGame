package Utils;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static int width = 16;
	private static int height = 16;
	
	public static BufferedImage grass;
		
	public static void init() {		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles1.png"));
		
		grass = sheet.crop(0, height, width, height);
	}
}