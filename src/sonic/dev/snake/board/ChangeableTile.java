package sonic.dev.snake.board;

import java.awt.image.BufferedImage;

public interface ChangeableTile {
    boolean GetCanChangeAgain();
    void changeTile(BufferedImage tileImage, Tile.Type type);
}
