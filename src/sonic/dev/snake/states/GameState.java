package sonic.dev.snake.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import sonic.dev.snake.Game;
import sonic.dev.snake.board.Board;
import sonic.dev.snake.entities.snake.Snake;

public class GameState extends State {

	private Snake player;
	private Board board;
	private boolean gameOver = false;
	
	public GameState(Game game) {
		super(game);
		
		player = new Snake(game, 1, 1);
		board = new Board(game, 45, 28, player);
	}
	
	@Override public void update() {
		if (!gameOver) {
			player.update();
			board.update();
			
			if (board.getGameOver()) {
				gameOver = true;
			}
		}
	}
	
	@Override public void render(Graphics g) {
		board.render(g);
		
		if (gameOver) {
			g.setColor(Color.red);
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 100));
			g.drawString("GAME OVER", 390, 385);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 75));
			g.drawString("Press Esc to exit game", 285, 490);
			g.drawString("or ENTER for highscores.", 250, 490 + g.getFontMetrics().getHeight());
		}
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
}
