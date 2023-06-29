package sonic.dev.snake.gfx;

import sonic.dev.snake.exceptions.NumberOutsideOfBoundariesException;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	BufferedImage sheet;
	
	public SpriteSheet(String path) {
		sheet = ImageLoader.loadFile(path);
	}
	
	public BufferedImage crop(int x, int y, int width, int height) {
		try {
			if (x < sheet.getMinX() || y < sheet.getMinY() ||
					x > sheet.getWidth() || y > sheet.getHeight() ||
					width > sheet.getWidth() || height > sheet.getHeight()) {
				throw new NumberOutsideOfBoundariesException();
			}

		} catch (NumberOutsideOfBoundariesException e) {
			e.printStackTrace();

			return null;
		}

		return sheet.getSubimage(x, y, width, height);
	}
	
}
