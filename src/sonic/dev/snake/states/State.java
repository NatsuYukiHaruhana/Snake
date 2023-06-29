package sonic.dev.snake.states;

import java.awt.Graphics;

import sonic.dev.snake.Game;
import sonic.dev.snake.gfx.Assets;

public abstract class State {

	public static State currentState = null;
	
	public static void setState(State state, String stateName) {
		if (stateName.compareTo("Game") != 0) {
			Assets.dispose();
		}

		currentState = state;

		game.ChangeCanvas(stateName);
	}
	
	public static State getState() {
		return currentState;
	}
	
	protected static Game game;
	
	public State(Game game) {
		this.game = game;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
}
