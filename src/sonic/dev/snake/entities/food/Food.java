package sonic.dev.snake.entities.food;

import sonic.dev.snake.entities.Entity;

public class Food extends Entity {

	public Food(int x, int y) {
		super(x, y);
		canMove = false;
	}
	
	public void update() {}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) { this.y = y; }

	@Override
	public void Move(int nextX, int nextY) {}
}
