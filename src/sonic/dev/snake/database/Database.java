package sonic.dev.snake.database;

import java.sql.*;
import java.util.*;

public class Database {
    final String url = "jdbc:mysql://127.0.0.1:3306/project_piii?verifyServerCertificate=false&useSSL=true&autoReconnect=true";
    final String user = "java";
    final String pass = "test123";

    public void AddToDatabase(String user, int score) throws SQLException {
        Connection conn = DriverManager.getConnection(url, this.user, pass);

        Statement checkPlayerExistsStmt = conn.createStatement();
        ResultSet playerExists = checkPlayerExistsStmt.executeQuery("SELECT * FROM players WHERE username LIKE '" + user + "';");
        checkPlayerExistsStmt.closeOnCompletion();

        if (playerExists.next() == false) {
            Statement insertPlayerStmt = conn.createStatement();
            insertPlayerStmt.executeUpdate("INSERT INTO players(username) VALUES ('" + user + "');");
            insertPlayerStmt.closeOnCompletion();
        }
        playerExists.close();

        Statement checkPlayerAmountStmt = conn.createStatement();
        ResultSet insertedPlayer = checkPlayerAmountStmt.executeQuery("SELECT uid FROM players WHERE username LIKE'" + user + "';");
        checkPlayerAmountStmt.closeOnCompletion();

        if (insertedPlayer.next() == false) {
            System.out.println("Could not insert player into database!");
            System.exit(1);
        }

        final int playerUID = insertedPlayer.getInt(1);
        insertedPlayer.close();

        Statement insertScoreStmt = conn.createStatement();
        insertScoreStmt.executeUpdate("INSERT INTO scores(uid, score) VALUES ('" + ((Integer)playerUID).toString() + "', '" + ((Integer)score).toString() + "');");
        insertScoreStmt.closeOnCompletion();
        conn.close();
    }

    public void ReturnTop10Scores(List<String> players, List<Integer> scores) throws SQLException {
        Connection conn = DriverManager.getConnection(url, this.user, pass);

        Statement getTop10ScoresStmt = conn.createStatement();
        ResultSet top10ScoresSet = getTop10ScoresStmt.executeQuery("SELECT p.username, s.score FROM players p, scores s WHERE p.uid = s.uid ORDER BY score DESC LIMIT 10;");
        getTop10ScoresStmt.closeOnCompletion();

        while(top10ScoresSet.next() == true) {
            players.add(top10ScoresSet.getString(1));
            scores.add(top10ScoresSet.getInt(2));
        }

        top10ScoresSet.close();
        conn.close();
    }
}
