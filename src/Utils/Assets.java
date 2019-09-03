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
	public static Map<List<BufferedImage>, List<Dimension>> still = new HashMap<>();
	
	public static void init() {		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles1.png"));
		
		grass = sheet.crop(0, height, width, height);
		
		List<BufferedImage> images = new ArrayList<>();
		List<Dimension> dimensions = new ArrayList<>();

		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/png/walking.png"));
		width = 24;
		height = 46;
		
		images.add(sheet.crop(0, 0, width, height)); // first
		dimensions.add(new Dimension(width, height));
		
		images.add(sheet.crop(26, 0, width, height)); // second
		dimensions.add(new Dimension(width, height));
		
		images.add(sheet.crop(52, 0, width, height)); // third
		dimensions.add(new Dimension(width, height));
		
		images.add(sheet.crop(78, 0, width, height)); // fourth
		dimensions.add(new Dimension(width, height));
	
		images.add(sheet.crop(104, 0, width, height)); // fifth
		dimensions.add(new Dimension(width, height));
		
		images.add(sheet.crop(130, 0, width, height)); // sixth
		dimensions.add(new Dimension(width, height));
		
		images.add(sheet.crop(156, 0, width, height)); // seventh
		dimensions.add(new Dimension(width, height));
		
		images.add(sheet.crop(182, 0, width, height)); // eight
		dimensions.add(new Dimension(width, height));
		
		walk.put(images, dimensions);
		
		images = new ArrayList<>();
		dimensions = new ArrayList<>();
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/png/still.png"));
		
		images.add(sheet.crop(0, 0, width, height)); // first
		dimensions.add(new Dimension(width, height));
		
		still.put(images, dimensions);
	}
}