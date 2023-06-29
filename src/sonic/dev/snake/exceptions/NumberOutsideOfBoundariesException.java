package sonic.dev.snake.exceptions;

public class NumberOutsideOfBoundariesException extends Exception {
    public NumberOutsideOfBoundariesException() {}

    public NumberOutsideOfBoundariesException(String message)
    {
        super(message);
    }
}
