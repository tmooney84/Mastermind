package org.mastermind.db;

import org.mastermind.GameResults;

import javax.xml.crypto.Data;
import java.util.List;

import static org.mastermind.db.AppDataPath.getAppDataDirectory;

public class DBTest {
    public static void main(String[] args) {
        System.out.println("The path is: " + getAppDataDirectory("Mastermind"));

        System.out.println("-------------------Entering Game Data-----------------------");
        GameResults testResults1 = new GameResults("Winner", "Jimbo", 12345, 3);
        GameResults testResults2 = new GameResults("Tie", "", 33333, 10);
        GameResults testResults3 = new GameResults("Loser", "TheMan123", 44355, 10);
        DatabaseServiceImpl testDBServ = new DatabaseServiceImpl();
        testDBServ.saveGameResults(testResults1);
        testDBServ.saveGameResults(testResults2);
        testDBServ.saveGameResults(testResults3);
        System.out.println("-------------------Printing Game Data-----------------------");
        List<GameResults> gr = testDBServ.getAllGameResultsByTime();
        for (GameResults g : gr) {
            System.out.println("Time ID: " + g.getTimeID() +
                    " Status: " + g.getStatus() +
                    " Winner Name: " + g.getWinnerName());
        }
        System.out.println("-------------------Deleting Game Data-----------------------");
        testDBServ.deleteAllGameResults();
        System.out.println("-------------------Printing Again-----------------------");
        List<GameResults> gnr = testDBServ.getAllGameResultsByTime();
        for (GameResults g : gnr) {
            System.out.println("Time ID: " + g.getTimeID() +
                    " Status: " + g.getStatus() +
                    " Winner Name: " + g.getWinnerName());
        }

        System.out.println("-------------------Deleting Game Data Again-----------------------");
        testDBServ.deleteAllGameResults();
    }
}
