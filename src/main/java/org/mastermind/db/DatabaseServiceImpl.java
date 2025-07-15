package org.mastermind.db;

import org.mastermind.GameResults;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {
    @Override
    public List<GameResults> getAllGameResultsByTime() {
        String sqlQuery = "SELECT * FROM GAME_INFO ORDER BY TIME_ID DESC";
        List<GameResults> gameList = new ArrayList();

        try (Connection dbConnect = DatabaseManager.connect();
             Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            while (rs.next()) {
                GameResults g = new GameResults();
                g.setTimeID(rs.getLong("TIME_ID"));
                g.setStatus(rs.getString("STATUS"));
                g.setWinnerName(rs.getString("WINNER_NAME"));
                g.setRound(rs.getInt("ROUND"));
                gameList.add(g);
            }
            return gameList;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameList;
    }

    @Override
    public void saveGameResults(GameResults results) {
        String sqlQuery = "INSERT INTO GAME_INFO (TIME_ID, STATUS, WINNER_NAME, ROUND) VALUES (?,?,?,?)";
        try (Connection dbConnect = DatabaseManager.connect();
             PreparedStatement prepStmt = dbConnect.prepareStatement(sqlQuery)) {
            prepStmt.setLong(1, results.getTimeID());
            prepStmt.setString(2, results.getStatus());
            prepStmt.setString(3, results.getWinnerName());
            prepStmt.setInt(4, results.getRound());
            int affectedRows = prepStmt.executeUpdate();

            //printout test
            System.out.println(affectedRows + " row affected!!!");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteAllGameResults() {
        String sqlQuery = "DELETE FROM GAME_INFO";
        try (Connection dbConnect = DatabaseManager.connect();
             Statement stmt = dbConnect.createStatement()) {
            int i = stmt.executeUpdate(sqlQuery);
            if (i == 1) {
                return true;
            }
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
