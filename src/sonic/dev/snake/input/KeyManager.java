package sonic.dev.snake.input;

import sonic.dev.snake.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private Game game;
	private boolean keys[];
	public boolean up, down, left, right, accelerate, exit, enter;

	public KeyManager(Game game) {
		this.game = game;
		keys = new boolean[256];
	}
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		accelerate = keys[KeyEvent.VK_SPACE];
		exit = keys[KeyEvent.VK_ESCAPE];
		enter = keys[KeyEvent.VK_ENTER];
	}
	
	@Override public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	@Override public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	@Override public void keyTyped(KeyEvent e) {
		if (game.GetState() == "Main Menu") {
			if (e.getKeyChar() == '\b') {
				if (game.GetUsername().length() > 0) {
					game.SetUsername(game.GetUsername().substring(0, game.GetUsername().length() - 1));
				}
			} else if (game.GetUsername().length() < 10) {
				if (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' ||
					e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z' ||
					e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
					game.SetUsername(game.GetUsername() + e.getKeyChar());
				}
			}
		}
	}
	
}
