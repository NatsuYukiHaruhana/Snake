package sonic.dev.snake.entities.snake;

import sonic.dev.snake.entities.Entity;

public class SnakeBody extends Entity {

	private int lastX, lastY;
	
	public SnakeBody(int x, int y) {
		super(x, y);
		canMove = true;
		
		lastX = x;
		lastY = y;
	}
	
	@Override public void update() {
		
	}

	@Override
	public void Move(int nextX, int nextY) {
		if (canMove) {
			lastX = x;
			lastY = y;

			x += nextX;
			y += nextY;
		}
	}

	//Getters
	public int getLastX() {
		return lastX;
	}
	
	public int getLastY() {
		return lastY;
	}

	//Setters
	public void SetLastX(int value) { lastX = value; }
	public void SetLastY(int value) { lastY = value; }
}
