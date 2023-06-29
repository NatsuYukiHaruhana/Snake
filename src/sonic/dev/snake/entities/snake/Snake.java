package sonic.dev.snake.entities.snake;

import java.util.ArrayList;

import sonic.dev.snake.Game;
import sonic.dev.snake.entities.Entity;
import sonic.dev.snake.input.KeyManager;

public class Snake extends Entity {
	
	private Game game;
	private KeyManager keyManager;
	
	private float timeTillNextUpdate = 0.5f;
	private float timePassedSinceUpdate = 0.03f;
	private int nextX = 1, nextY = 0; // next position the head of the snake will move
	private int lastMoveX = 1, lastMoveY = 1; // for bugs regarding movement when nBodies > 1
	
	private ArrayList<SnakeBody> body;
	private int nBodies = 0;
	
	
	public Snake(Game game, int x, int y) {
		super(x, y);
		canMove = true;
		
		this.game = game;
		keyManager = game.getKeyManager();
		
		body = new ArrayList<SnakeBody>();
		body.add(new SnakeBody(x, y));
		nBodies++;
	}
	
	@Override public void update() {
		
		if (nBodies == 1) {
			if (keyManager.up) {
				nextX = 0;
				nextY = -1;
			} else if (keyManager.down) {
				nextX = 0;
				nextY = 1;
			} else if (keyManager.left) {
				nextX = -1;
				nextY = 0;
			} else if (keyManager.right) {
				nextX = 1;
				nextY = 0;
			}
		} else {
			if (keyManager.up && lastMoveY != 1) {
				nextX = 0;
				nextY = -1;
			} else if (keyManager.down && lastMoveY != -1) {
				nextX = 0;
				nextY = 1;
			} else if (keyManager.left && lastMoveX != 1) {
				nextX = -1;
				nextY = 0;
			} else if (keyManager.right && lastMoveX != -1) {
				nextX = 1;
				nextY = 0;
			}
		}
		
		timePassedSinceUpdate += keyManager.accelerate ? 0.04f : 0.01f;
		
		if (timePassedSinceUpdate >= timeTillNextUpdate) {
			timePassedSinceUpdate = 0.0f;

			System.out.println(getHead().getX() + " " + getHead().getY());
			this.Move(nextX, nextY);
			
			lastMoveX = nextX;
			lastMoveY = nextY;
		}
	}
	
	public void speedUp() {
		timeTillNextUpdate -= 0.02f;
	}
	
	public void addBody() {
		body.add(new SnakeBody(body.get(body.size() - 1).getLastX(), body.get(body.size() - 1).getLastY()));
		nBodies++;
	}
	
	public void resetSnake() {
		body.clear();
		x = 1;
		y = 1;
		
		nextX = lastMoveX = 1;
		nextY = lastMoveY = 0;
	
		body.add(new SnakeBody(x, y));
		nBodies = 1;
		
		timeTillNextUpdate = 0.5f;
		timePassedSinceUpdate = 0.0f;
	}

	@Override
	public void Move(int nextX, int nextY) {
		if (canMove) {
			getHead().Move(nextX, nextY);

			for (int i = 1; i < nBodies; i++) {
				final int nextPosX = body.get(i - 1).getLastX() - body.get(i).getX();
				final int nextPosY = body.get(i - 1).getLastY() - body.get(i).getY();

				body.get(i).Move(nextPosX, nextPosY);
			}
		}
	}

	//Getters
	public SnakeBody getBodyAt(int index) {
		return body.get(index);
	}
	
	public SnakeBody getHead() {
		return getBodyAt(0);
	}
	
	public int getNumberOfBodies() {
		return nBodies;
	}
}
