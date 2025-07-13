package org.mastermind;

public class GameResults {
    private String status;
    private String winnerName;
    private long timeID;
    private int round;

    public GameResults(String status, String winnerName, long timeID, int round) {
        this.status = status;
        this.winnerName = winnerName;
        this.timeID = timeID;
        this.round = round;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public void setTimeID(long timeID) {
        this.timeID = timeID;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getStatus() {
        return status;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public long getTimeID() {
        return timeID;
    }

    public int getRound() {
        return round;
    }
}
