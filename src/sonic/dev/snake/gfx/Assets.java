package sonic.dev.snake.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int width = 64, height = 64; // each tile is 64 x 64
	
	public static BufferedImage snakeHead, snakeBody, edge, food, innerBoard;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet("G:\\Java\\Snake\\res\\Textures\\tiles.png");
		
		snakeHead = sheet.crop(0, 0, width, height);
		snakeBody = sheet.crop(width, 0, width, height);
		food = sheet.crop(0, height, width, height);
		edge = sheet.crop(width, height, width, height);
		innerBoard = sheet.crop(0, height * 2, width, height);
	}

	public static void dispose() {
		snakeHead = snakeBody = food = edge = innerBoard = null;
	}
}
