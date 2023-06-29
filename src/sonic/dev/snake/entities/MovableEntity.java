package sonic.dev.snake.entities;

public interface MovableEntity {
    boolean GetCanMove();
    void Move(int nextX, int nextY);
}
