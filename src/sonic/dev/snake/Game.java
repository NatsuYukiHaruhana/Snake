package sonic.dev.snake;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.sql.SQLException;

import sonic.dev.snake.display.Display;
import sonic.dev.snake.gfx.Assets;
import sonic.dev.snake.input.KeyManager;
import sonic.dev.snake.states.GameState;
import sonic.dev.snake.states.HighscoresState;
import sonic.dev.snake.states.MainMenuState;
import sonic.dev.snake.states.State;

public class Game implements Runnable {

	//Thread
	private Thread thread;
	private boolean isRunning = false;
	
	//Display
	private Display display;
	private String title;
	private int width, height;
	
	//Graphics
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	private MainMenuState mainMenuState;
	private GameState gameState;
	private HighscoresState highscoresState;
	private String startState;
	
	//Input
	private KeyManager keyManager;

	//Others
	private String username;
	private int score;
	
	public Game(String title, int width, int height, String startState) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.startState = startState;
	}
	
	//Init game variables
	private void init() throws SQLException {
		keyManager = new KeyManager(this);
		
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);

		try {
			if (startState == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			startState = "Main Menu";
		} finally {
			if (startState.compareTo("Game") == 0) {
				Assets.init();
				gameState = new GameState(this);
				State.setState(gameState, "Game");
			} else if (startState.compareTo("Highscores") == 0) {
				highscoresState = new HighscoresState(this);
				State.setState(highscoresState, "Highscores");
			} else {
				mainMenuState = new MainMenuState(this);
				State.setState(mainMenuState, "Main Menu");
			}
		}

		username = "";
	}
	
	//Game update/render
	private void update() throws SQLException {
		keyManager.update();
		
		if (State.getState() != null) {
			State.getState().update();
			
			if (State.getState() == gameState) {
				if (gameState.getGameOver()) {
					if (keyManager.exit) {
						stop();
					} else if (keyManager.enter) {
						highscoresState = new HighscoresState(this);
						State.setState(highscoresState, "Highscores");
						gameState = null;
					}
				}
			} else if (State.getState() == highscoresState) {
				if (keyManager.exit) {
					stop();
				} else if (keyManager.accelerate) {
					mainMenuState = new MainMenuState(this);
					State.setState(mainMenuState, "Main Menu");
					highscoresState = null;
				}
			} else if (State.getState() == mainMenuState) {
				if (keyManager.enter) {
					Assets.init();
					gameState = new GameState(this);
					State.setState(gameState, "Game");
					mainMenuState = null;
				}
			}
		}
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		//Begin drawing
		
		if (State.getState() != null) {
			State.getState().render(g);
		}
		
		//End drawing
		
		bs.show();
		g.dispose();
	}
	
	//Thread run
	public void run() {
		try {
			init();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//FPS
		final int FPS = 60;
		double timePerFrame = 1000000000 / FPS;
		double delta = 0;
		long timeNow = 0;
		long timeLast = System.nanoTime();
		//FPS Counter
		int frames = 0;
		long timer = 0;
		
		while (isRunning) {
			timeNow = System.nanoTime();
			delta += (timeNow - timeLast) / timePerFrame;
			timer += timeNow - timeLast;
			timeLast = timeNow;
			
			if (delta >= 1) {
				try {
					update();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				render();
				
				delta--;
				frames++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer = 0;
			}
		}
	}
	
	//Thread start/stop
	public synchronized void start() {
		if (isRunning) {
			System.out.println("Warning: start() function called again!");
			return;
		}
		
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!isRunning) {
			System.out.println("Warning: stop() function called again!");
			return;
		}
		
		isRunning = false;

		display.getFrame().dispose();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void ChangeCanvas(String title) {
		display.ChangeCanvas(title);
		display.getCanvas().createBufferStrategy(3);
	}

	//Getters
	public final int GetDisplayWidth() {
		return width;
	}

	public final int GetDisplayHeight() {
		return height;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public final String GetUsername() {
		return username;
	}

	public final int GetScore() {
		return score;
	}

	public final String GetState() {
		if (State.getState() == gameState) {
			return "Game";
		} else if (State.getState() == mainMenuState) {
			return "Main Menu";
		} else if (State.getState() == highscoresState) {
			return "Highscores";
		} else {
			return null;
		}
	}

	//Setters
	public void SetUsername(String value) {
		username = value;
	}

	public void SetScore(int value) {
		score = value;
	}
}
