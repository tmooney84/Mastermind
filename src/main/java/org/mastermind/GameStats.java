package org.mastermind;

import static org.mastermind.GameUtils.randomCode;

public class GameStats {
    private String secretCode;
    private int attempts = 10;
    private int currentRound;
    private boolean codeFound;
    private RoundData[] roundsData;

    public GameStats() {
        this.currentRound = 0;
        this.secretCode = randomCode();
        this.codeFound = false;
        RoundData[] roundsData = new RoundData[attempts];
    }

    public GameStats(String secretCode) {
        this.currentRound = 0;
        this.secretCode = secretCode;
        this.codeFound = false;
        RoundData[] roundsData = new RoundData[attempts];
    }

    public void setRoundsData(RoundData currentData) {
        this.roundsData[this.currentRound] = currentData;
    }

    public void setRoundsData(RoundData currentData, int round) {
        this.roundsData[round] = currentData;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setCodeFound(boolean codeFound) {
        this.codeFound = codeFound;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public boolean getCodeFound() {
        return codeFound;
    }

    public RoundData[] getRoundData() {
        return roundsData;
    }

    public RoundData getRoundData(int roundNumber) {
        return roundsData[roundNumber];
    }
}
