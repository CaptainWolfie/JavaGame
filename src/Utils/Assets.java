package Utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assets {
	
	private static int width = 16;
	private static int height = 16;
	
	public static BufferedImage grass, dirt1, dirt2, dirt3, topRightGrass, topLeftGrass, rightGrass, leftGrass, bottomRightGrass,
				bottomLeftGrass, sandTop, sandBottom;
	public static Map<List<BufferedImage>, List<Rectangle>> walk = new HashMap<>();
	public static Map<List<BufferedImage>, List<Rectangle>> still = new HashMap<>();
	public static Map<List<BufferedImage>, List<Rectangle>> use = new HashMap<>();

	public static void init() {		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Blocks.png"));
		
		dirt1 = sheet.crop(0, 0, width, height);
		dirt2 = sheet.crop(width, 0, width, height);
		dirt3 = sheet.crop(width * 2, 0, width, height);
		
		grass = sheet.crop(0, height, width, height);
		topRightGrass = sheet.crop(width, height, width, height);
		topLeftGrass = sheet.crop(width * 2, height, width, height);
		
		rightGrass = sheet.crop(0, height * 2, width, height);
		leftGrass = sheet.crop(width, height * 2, width, height);
		
		bottomRightGrass = sheet.crop(0, height * 3, width, height);
		bottomLeftGrass = sheet.crop(width, height * 3, width, height);
		
		sandTop = sheet.crop(width * 2, height * 2, width, height);
		sandBottom = sheet.crop(width * 2, height * 3, width, height);
		
		// player walking
		List<BufferedImage> images = new ArrayList<>();
		List<Rectangle> dimensions = new ArrayList<>();

		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/png/walking.png"));
		width = 24;
		height = 46;
		
		images.add(sheet.crop(0, 0, width, height)); // first
		dimensions.add(new Rectangle(width, height));
		
		images.add(sheet.crop(26, 0, width, height)); // second
		dimensions.add(new Rectangle(width, height));
		
		images.add(sheet.crop(52, 0, width, height)); // third
		dimensions.add(new Rectangle(width, height));
		
		images.add(sheet.crop(78, 0, width, height)); // fourth
		dimensions.add(new Rectangle(width, height));
	
		images.add(sheet.crop(104, 0, width, height)); // fifth
		dimensions.add(new Rectangle(width, height));
		
		images.add(sheet.crop(130, 0, width, height)); // sixth
		dimensions.add(new Rectangle(width, height));
		
		images.add(sheet.crop(156, 0, width, height)); // seventh
		dimensions.add(new Rectangle(width, height));
		
		images.add(sheet.crop(182, 0, width, height)); // eight
		dimensions.add(new Rectangle(width, height));
		
		walk.put(images, dimensions);
		
		// player still
		images = new ArrayList<>();
		dimensions = new ArrayList<>();
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/png/still.png"));
		
		images.add(sheet.crop(0, 0, width, height)); // first
		dimensions.add(new Rectangle(width, height));
		
		still.put(images, dimensions);
		
		// player tool use
		images = new ArrayList<>();
		dimensions = new ArrayList<>();
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/png/Tools Use.png"));
		
		images.add(sheet.crop(0, 0, 30, height)); // first
		dimensions.add(new Rectangle(width - 30, 0, 30, height));
		
		images.add(sheet.crop(32, 0, 26, height)); // second
		dimensions.add(new Rectangle(width - 26, 0, 26, height));

		images.add(sheet.crop(60, 0, 22, height)); // third
		dimensions.add(new Rectangle(width - 22, 0, 22, height));

		images.add(sheet.crop(84, 0, 24, height)); // fourth
		dimensions.add(new Rectangle(width - 24, 0, 24, height));

		images.add(sheet.crop(110, 0, 24, height)); // fifth
		dimensions.add(new Rectangle(width - 24, 0, 24, height));
		
		use.put(images, dimensions);
	}
}