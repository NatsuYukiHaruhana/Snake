package sonic.dev.snake.states;

import sonic.dev.snake.Game;

import java.awt.*;

public class MainMenuState extends State {

    private final int usernameOffset = 23;

    public MainMenuState(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        //Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.GetDisplayWidth(), game.GetDisplayHeight());

        //Title Screen
        g.setColor(Color.magenta);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 100));
        g.drawString("Welcome to Snake!", 240, 185);

        //Others
        g.setColor(Color.blue);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 75));
        g.drawString("Press ENTER to play!", 310, 490);
        g.drawString("Current username:" + game.GetUsername(), 355 - game.GetUsername().length() * usernameOffset, 490 + g.getFontMetrics().getHeight());
    }
}
