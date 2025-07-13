package org.mastermind.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {
    private static final String APP_NAME = "Mastermind";
    private static final String DB_FILE_NAME = "game_data.db";
    private static String dbPath;

    static {
        String appDataDir = AppDataPath.getAppDataDirectory(APP_NAME);
        dbPath = appDataDir + File.separator + DB_FILE_NAME;
    }

    public static Connection connect() {
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC"); //ADDED from tutorialspoint
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to database at: " + dbPath);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS GAME_INFO" +
                    "(TIME_ID INT PRIMARY KEY NOT NULL," +
                    "STATUS CHAR(50) NOT NULL," +
                    "WINNER_NAME CHAR(50)," +
                    "ROUND INT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0); ??
        }

        return conn;
    }
}
