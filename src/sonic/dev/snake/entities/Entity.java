package sonic.dev.snake.entities;

public abstract class Entity implements MovableEntity {

	protected boolean canMove;

	protected final int DEFAULT_WIDTH = 32,
						DEFAULT_HEIGHT = 32;
	
	protected int x, y, width, height;
	
	public Entity (int x, int y) {
		this.x = x;
		this.y = y;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
	}
	
	public abstract void update();
	
	//Getters
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public boolean GetCanMove() { return canMove; }


}
