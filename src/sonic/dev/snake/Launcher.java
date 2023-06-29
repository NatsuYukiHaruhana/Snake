package sonic.dev.snake;

import sonic.dev.snake.exceptions.ArgumentNotRecognizedException;

public class Launcher {
	public static void main(String[] args) {
		String arg = null;
		try {
			if (args.length == 0 || (args[0].compareTo("Game") != 0 && args[0].compareTo("Main Menu") == 0 && args[0].compareTo("Highscores") == 0)) {
				throw new ArgumentNotRecognizedException();
			}
			arg = args[0];
		} catch (ArgumentNotRecognizedException e){
			e.printStackTrace();
		} finally {
			Game game = new Game("Snake", 1440, 900, arg);

			game.start();
		}
	}
}
