package sonic.dev.snake.exceptions;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException() {}

    public ImageNotFoundException(String message)
    {
        super(message);
    }
}
