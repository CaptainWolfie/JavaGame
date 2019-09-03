package Utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assets {
	
	private static int width = 16;
	private static int height = 16;
	
	public static BufferedImage grass;
	public static Map<List<BufferedImage>, List<Dimension>> walk = new HashMap<>();
		
	public static void init() {		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles1.png"));
		
		grass = sheet.crop(0, height, width, height);
		
		List<BufferedImage> images = new ArrayList<>();
		List<Dimension> dimensions = new ArrayList<>();		

		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/png/walking.png"));
		width = 24;
		height = 46;
		images.add(sheet.crop(0, 0, width, height));
		dimensions.add(new Dimension(width, height));
		width = 24;
		images.add(sheet.crop(26, 0, width, height));
		dimensions.add(new Dimension(width, height));
		images.add(sheet.crop(52, 0, width, height));
		dimensions.add(new Dimension(width, height));
		
		walk.put(images, dimensions);
	}
}