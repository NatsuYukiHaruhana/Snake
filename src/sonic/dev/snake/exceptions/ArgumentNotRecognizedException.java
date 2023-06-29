package sonic.dev.snake.exceptions;

public class ArgumentNotRecognizedException extends Exception {
    public ArgumentNotRecognizedException() {}

    public ArgumentNotRecognizedException(String message)
    {
        super(message);
    }
}
