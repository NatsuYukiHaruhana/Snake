package sonic.dev.snake.states;

import sonic.dev.snake.Game;
import sonic.dev.snake.database.Database;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HighscoresState extends State {

    private Database db;
    private List<String> players;
    private List<Integer> scores;

    public HighscoresState(Game game) throws SQLException {
        super(game);

        db = new Database();
        if (game.GetUsername() == "") {
            db.AddToDatabase("Guest", game.GetScore());
        } else {
            db.AddToDatabase(game.GetUsername(), game.GetScore());
        }

        players = new ArrayList<String>();
        scores = new ArrayList<Integer>();
        db.ReturnTop10Scores(players, scores);
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics g) {
        //Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.GetDisplayWidth(), game.GetDisplayHeight());

        //Title Screen
        g.setColor(Color.magenta);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 100));
        g.drawString("Highscores", 440, 85);


        //Scores
        g.setColor(Color.blue);
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 75));
        final int xOffset = 750;
        for(int rank = 1; rank <= 10; rank++) {
            String message = ((Integer) rank).toString() + ". " + players.get(rank - 1) + " " + scores.get(rank - 1).toString();
            g.drawString(message, 25 + (rank > 5 ? xOffset : 0), 180 + g.getFontMetrics().getHeight() * ((rank - 1) % 5));
        }

        //Others
        g.setColor(Color.PINK);
        g.drawString("Press SPACE to go back to main menu", 25, 700);
        g.drawString("or ESC to exit!", 430, 700 + g.getFontMetrics().getHeight());
    }
}
