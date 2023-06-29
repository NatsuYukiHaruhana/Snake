package sonic.dev.snake.board;

import java.awt.*;
import java.awt.image.BufferedImage;

import sonic.dev.snake.exceptions.ImageNotFoundException;
import sonic.dev.snake.gfx.Assets;

public class Tile implements ChangeableTile {

	private boolean canChangeAgain = true;
	private int x, y, width, height;
	private BufferedImage image;

	public static enum Type
	{
		player,
		food,
		edge,
		board,
		NA // Not Available
	}
	
	private Type type = Type.NA;
	
	public Tile(int x, int y, int width, int height, BufferedImage image, Type type)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.type = type;
	}
	
	public void render(Graphics g) {
		try {
			if (image == null) {
				throw new ImageNotFoundException();
			}
			g.drawImage(image, x, y, width, height, null);
		} catch (ImageNotFoundException e) {
			e.printStackTrace();

			Color initColor = g.getColor();
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, width, height);
			g.setColor(initColor);
		}
	}

	@Override
	public void changeTile(BufferedImage tileImage, Type type) {
		if (type == Type.edge) {
			canChangeAgain = false;
		} else {
			canChangeAgain = true;
		}

		image = tileImage;
		this.type = type;
	}

	@Override
	public boolean GetCanChangeAgain() {
		return canChangeAgain;
	}

	//Getters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//Setters
	public Type getType() {
		return type;
	}
}
