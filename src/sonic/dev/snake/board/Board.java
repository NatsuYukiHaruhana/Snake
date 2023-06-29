package sonic.dev.snake.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import sonic.dev.snake.Game;
import sonic.dev.snake.board.Tile.Type;
import sonic.dev.snake.entities.food.Food;
import sonic.dev.snake.entities.snake.Snake;
import sonic.dev.snake.gfx.Assets;

public class Board {

	private Game game;

	private int boardWidth, boardHeight;
	private int tileWidth, tileHeight;
	
	private Tile board[][];
	private Snake player;
	
	private Random rng;
	private Food food;
	
	private int score = 0;
	private int nLives = 3;
	
	private boolean gameOver = false;
	
	public Board(Game game, int boardWidth, int boardHeight, Snake player) {
		this.game = game;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		tileWidth = 32;
		tileHeight = 32;
		
		this.player = player;
		
		rng = new Random();
		
		int foodX, foodY;
		do {
			foodX = rng.nextInt(1, (boardWidth - 1));
			foodY = rng.nextInt(1, (boardHeight - 1));
		} while(foodX == 1 && foodY == 1);
		
		food = new Food(foodX, foodY);
		createBoard();
	}
	
	private void createBoard() {
		board = new Tile[boardWidth][boardHeight];
		
		for (int i = 0; i < boardWidth; i++) {
			board[i][0] = new Tile(i * tileWidth, 0, tileWidth, tileHeight, Assets.edge, Type.edge);
			board[i][boardHeight - 1] = new Tile(i * tileWidth, (boardHeight - 1) * tileHeight, tileWidth, tileHeight, Assets.edge, Type.edge);
		}
		
		for (int i = 0; i < boardHeight; i++) {
			board[0][i] = new Tile(0, i * tileHeight, tileWidth, tileHeight, Assets.edge, Type.edge);
			board[boardWidth - 1][i] = new Tile((boardWidth - 1) * tileWidth, i * tileHeight, tileWidth, tileHeight, Assets.edge, Type.edge);
		}
		
		for (int i = 1; i < boardWidth - 1; i++) {
			for (int j = 1; j < boardHeight - 1; j++) {
				board[i][j] = new Tile(i * tileWidth, j * tileHeight, tileWidth, tileHeight, Assets.innerBoard, Type.board);
			}
		}
		
		
		setTile(food.getX(), food.getY(), Assets.food, Type.food);
	}
	
	private void resetBoard() {
		//resetting snake
		final int nBodies = player.getNumberOfBodies();
		for (int i = 0; i < nBodies; i++) {
			setTile(player.getBodyAt(i).getLastX(), player.getBodyAt(i).getLastY(), Assets.innerBoard, Type.board);
		}
		
		player.resetSnake();
		
		//resetting food
		setTile(food.getX(), food.getY(), Assets.innerBoard, Type.board);
		System.gc();
		
		int foodX, foodY;
		do {
			foodX = rng.nextInt(1, (boardWidth - 1));
			foodY = rng.nextInt(1, (boardHeight - 1));
		} while(foodX == 1 && foodY == 1);
		
		food.setX(foodX);
		food.setY(foodY);
		
		setTile(food.getX(), food.getY(), Assets.food, Type.food);
	}
	
	public void update() {
		//Player logic
		if (board[player.getHead().getX()][player.getHead().getY()].getType() == Type.edge) {
			nLives--;
			
			if (nLives == 0) {
				gameOver = true;
				game.SetScore(score);
			} else {
				resetBoard();
			}
			return;
		} 
		
		final int nBodies = player.getNumberOfBodies();
		for (int i = 1; i < nBodies; i++) {
			if (player.getHead().getX() == player.getBodyAt(i).getX() && player.getHead().getY() == player.getBodyAt(i).getY()) {
				if (nLives == 0) {
					gameOver = true;
				} else {
					resetBoard();
				}
				return;
			}
		}
		
		if (board[player.getHead().getX()][player.getHead().getY()].getType() == Type.food) {
			player.addBody();
			score++;
			
			if (score % 2 == 0) {
				player.speedUp();
			}
			
			int foodX, foodY;
			boolean validPosition;
			do {
				validPosition = true;
				
				foodX = rng.nextInt(1, boardWidth - 1);
				foodY = rng.nextInt(1, boardHeight - 1);
				
				for (int i = 0; i < nBodies && validPosition; i++) {
					if (foodX == player.getBodyAt(i).getX() && foodY == player.getBodyAt(i).getY()) {
						validPosition = false;
					}
				}
			} while(!validPosition);
			
			food = null;
			System.gc();
			
			food = new Food(foodX, foodY);
			
			setTile(food.getX(), food.getY(), Assets.food, Type.food);
		}
		
		//Player render
		for (int i = 1; i < nBodies; i++) {
			setTile(player.getBodyAt(i).getX(), player.getBodyAt(i).getY(), Assets.snakeBody, Type.player);
		}
		
		resetTile(player.getBodyAt(nBodies - 1).getLastX(), player.getBodyAt(nBodies - 1).getLastY());
		setTile(player.getHead().getX(), player.getHead().getY(), Assets.snakeHead, Type.player);
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < boardHeight; j++) {
				board[i][j].render(g);
			}
		}
		
		g.setColor(Color.GREEN);
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 25));
		g.drawString("Score: " + score, 32, 25);
		
		g.drawString("Lives: " + nLives, 1320, 25);
	}
	
	public void setTile(int x, int y, BufferedImage tileImage, Type type) {
		if (board[x][y].GetCanChangeAgain()) {
			board[x][y].changeTile(tileImage, type);
		}
	}
	
	public void resetTile(int x, int y) {
		setTile(x, y, Assets.innerBoard, Type.board);
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
}
